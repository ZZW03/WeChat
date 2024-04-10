package com.zzw.tcp.handel;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.zzw.common.Const;
import com.zzw.common.model.UserSession;
import com.zzw.common.model.enums.SystemCommand;
import com.zzw.common.pack.LoginPack;
import com.zzw.common.proto.Message;
import com.zzw.tcp.utils.SocketHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
public class NettyHandlerService extends SimpleChannelInboundHandler<Message> {

    @Resource
    SocketHolder socketHolder;


    @Resource
    StringRedisTemplate stringRedisTemplate;



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
            if (stringRedisTemplate.opsForHash().hasKey(Const.REDIS.USER_SESSION,userId)){
                //todo 通知channel 另外一端上线 要下线
                NioSocketChannel nioSocketChannel = socketHolder.get(userId);
                //todo 通知下线
                nioSocketChannel.writeAndFlush("asd");
                //todo stringRedisTemplate移除 socketHolder中移除
                socketHolder.remove(userId);
            }

            stringRedisTemplate.opsForHash().put(Const.REDIS.USER_SESSION,userId,userSession);

            //向sessionSocketHolder中存储channel
            socketHolder
                    .put(userId, (NioSocketChannel) ctx.channel());

            
            
        }
    }


}
