package com.zzw.tcp.handel;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.zzw.common.Const;
import com.zzw.common.model.UserSession;
import com.zzw.common.model.enums.Command.FriendshipEventCommand;
import com.zzw.common.model.enums.Command.MessageCommand;
import com.zzw.common.model.enums.Command.SystemCommand;
import com.zzw.common.model.enums.FriendShipError;
import com.zzw.common.model.req.friend.CheckStatusReq;
import com.zzw.common.pack.LoginPack;
import com.zzw.common.proto.Message;
import com.zzw.common.proto.MessagePack;
import com.zzw.tcp.Mq.Send.MqMessageProducer;
import com.zzw.tcp.fegin.MessageClient;
import com.zzw.tcp.utils.SocketHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import jakarta.annotation.Resource;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;



@Component
public class NettyHandlerService extends SimpleChannelInboundHandler<Message> {

    @Setter
    static MqMessageProducer messageProducer;


    @Setter
    static SocketHolder socketHolder;

    @Setter
    static StringRedisTemplate stringRedisTemplate;

    @Setter
    static MessageClient messageClient;



    //查看三种消息
    //登录
    //退出
    //心跳检测
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                Message msg) throws Exception {


        Integer command = msg.getMessageHeader().getCommand();
        // 收到的是登录消息 做的处理
        if(command.equals(SystemCommand.LOGIN.getCommand())) {
            LoginPack loginPack = JSON.parseObject(JSONObject.toJSONString(msg.getMessagePack()), new TypeReference<LoginPack>() {
            }.getType());
            Integer userId = loginPack.getUserId();
            ctx.channel().attr(AttributeKey.valueOf(Const.CHANNEL.UserId)).set(userId);
            UserSession userSession = new UserSession();
            userSession.setUserId(userId);
            userSession.setConnectState(1);
            MessagePack message = new MessagePack();
            if (stringRedisTemplate.opsForHash().hasKey(Const.REDIS.USER_SESSION, userId.toString())) {
                //todo 通知channel 另外一端上线 要下线
                NioSocketChannel nioSocketChannel = socketHolder.get(userId);
                //todo 通知下线
                message.setCommand(SystemCommand.FORCEOFFLINE.getCommand());
                message.setData("另外一段已经登录，你已经强制下线");
                nioSocketChannel.writeAndFlush(message);
                //todo stringRedisTemplate移除 socketHolder中移除
                socketHolder.remove(userId);
            }
            stringRedisTemplate.opsForHash().put(Const.REDIS.USER_SESSION, userId.toString(), userSession.toString());
            //向sessionSocketHolder中存储channel
            socketHolder
                    .put(userId, (NioSocketChannel) ctx.channel());

        }else if(command.equals(MessageCommand.MSG_P2P.getCommand())){

            MessagePack messagePack = JSON.parseObject(JSONObject.toJSONString(msg.getMessagePack()), new TypeReference<MessagePack>() {
            }.getType());

            if(CheckStatus(messagePack)){
                messageProducer.sendMessage(msg,command);
            }else{
                messagePack.setCommand(FriendshipEventCommand.FRIEND_REFUSE_COMMUNICATION.getCommand());
                messagePack.setData(FriendShipError.U_IN_HIS_BLACK.getError());
                ctx.channel().writeAndFlush(messagePack);
            }

        }else{
            messageProducer.sendMessage(msg,command);
        }
    }


    private Boolean CheckStatus(MessagePack messagePack){


        Integer FromId = messagePack.getUserId();
        Integer ToId = messagePack.getToId();
        CheckStatusReq req = new CheckStatusReq(FromId,ToId);
        String s = messageClient.CheckStatus(req);
        JSONObject result = JSON.parseObject(s);
        Integer code = result.getInteger("code");
        if(code.equals(200)){
            return  true;
        }
        return false;
    }
}
