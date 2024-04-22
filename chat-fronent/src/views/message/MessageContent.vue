<script setup >
import router from "@/router/router";
import {useRoute} from "vue-router";
import {ListenMessage, SendToOne} from "@/net/Socket";
import {websocketstore} from "@/store/websocketstore";
import {get} from "@/net/net";
import {reactive, ref, watch} from "vue";
import axios from "axios";
import {CirclePlus, More, Phone, Platform, Scissor, Search, VideoCamera} from "@element-plus/icons-vue";
import V3Emoji from 'vue3-emoji'
import 'vue3-emoji/dist/style.css'
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {userdetailstore} from "@/store/userdetailstore";

const customIcon = {
  'Smileys & Emotion': '😚',
  'Food & Drink': '🍔',
  'Animals & Nature': '🐶',
  'People & Body': '🤚',
  'Travel & Places': '🚗',
  Activities: '🎉',
  Objects: '💰',
  Symbols: '♿',
  Flags: '🏴',
};

const customSize= {
  'V3Emoji-width': '700px',
  'V3Emoji-height': '20rem',
  'V3Emoji-fontSize': '1.5rem',
  'V3Emoji-itemSize': '30px'
};



const socket = websocketstore().socket
const detailstore = userdetailstore()
let disable = ref(true)
let textarea = ref("")
watch(textarea, (newValue) => {
  disable.value = newValue.length === 0;
});

const route = useRoute();
let toId = route.query.id

const clickEmoji = (val) => {
  textarea.value += val;
};

const userstore = userdetailstore()
let loading =ref(false)
let ToUserDetail
let FromUserAvatar

get(`/account/getOtherDetail?id=${toId}`,(data)=> {
  ToUserDetail = reactive({
    NickName:data.data.accountNickName,
    Avatar: "" + `${axios.defaults.baseURL}/images/${data.data.accountAvatar}`,
  });



  ListenMessage(socket,(data)=>{
    displayMessage(data.data.msgbody,"received")
  })
  loading.value = true
})


function SendMessage(){
  SendToOne(detailstore.userdetail.accountId,toId,textarea.value,socket)
  displayMessage(textarea.value,"sent")
  textarea.value = ""
}



function displayMessage(message, sender) {
  const chatContainer = document.getElementById("chat-container");
  if (!chatContainer) {
    console.error("Chat container element not found.");
    return;
  }

  // 创建包含头像和消息的总容器
  const totalMessageContainer = document.createElement("div");
  totalMessageContainer.classList.add("message-container");

  // 创建头像元素
  const avatar = document.createElement("img");
  avatar.setAttribute("src", sender === "sent" ? userstore.avatarUrl : ToUserDetail.Avatar);
  avatar.classList.add("my-avatar");

  // 添加不同的类以控制头像位置
  if (sender === "sent") {
    avatar.classList.add("avatar-sent");
  } else {
    avatar.classList.add("avatar-received");
  }

  // 创建消息元素
  const messageElement = document.createElement("div");
  messageElement.textContent = message;
  messageElement.classList.add("chat-message");

  // 添加不同的类以控制消息框的位置和样式
  if (sender === "sent") {
    messageElement.classList.add("chat-message-sent");
  } else {
    messageElement.classList.add("chat-message-received");
  }

  // 根据发送状态将头像和消息添加到总容器中
  if (sender === "sent") {
    totalMessageContainer.appendChild(messageElement); // 先添加消息
    totalMessageContainer.appendChild(avatar); // 再添加头像
  } else {
    totalMessageContainer.appendChild(avatar); // 先添加头像
    totalMessageContainer.appendChild(messageElement); // 再添加消息
  }

  // 将总容器添加到聊天容器中
  chatContainer.appendChild(totalMessageContainer);
}





</script>


<template>
  <div class="main" v-if="loading">
    <el-container class="contain-main">
      <el-header class="header">
        <div style="margin-top: 3% ;display: inline-block;font-size: 20px;font-family: 'Microsoft Yahei',serif">{{ToUserDetail.NickName}}</div>
        <div style="display:inline-block;position: relative;left: 77%">
          <el-button class="button-icon"  size="large" circle ><el-icon size="30"><Phone /></el-icon></el-button>
          <el-button class="button-icon"  size="large" circle ><el-icon size="30"><VideoCamera /></el-icon></el-button>
          <el-button class="button-icon"  size="large" circle ><el-icon size="30"><font-awesome-icon :icon="['fas', 'tv']" /></el-icon></el-button>
          <el-button class="button-icon"  size="large" circle ><el-icon size="30"><CirclePlus /></el-icon></el-button>
          <el-button class="button-icon"  size="large" circle ><el-icon size="30"><More /></el-icon></el-button>
        </div>
      </el-header>
      <el-divider />
      <el-scrollbar style="height: 50%">
        <el-main id="chat-container">



        </el-main>
      </el-scrollbar>
      <el-divider style="margin: 10px"/>
      <el-footer class="foot">
        <div>
          <div  >
            <div style="display: inline-block;margin-left: 20px">
              <V3Emoji
                  @click-emoji="clickEmoji"
                  :options-name="customIcon"
                  :recent="true"
                  :custom-size="customSize"
              >
                <font-awesome-icon :icon="['far', 'face-smile']" />
              </V3Emoji>
            </div>
            <div class="icon">
              <el-icon size="20" style="position: relative;top: 2px"><Scissor /></el-icon>
            </div>
            <div class="icon" >
              <font-awesome-icon class="font-awesome-icon fa-lg"  :icon="['far', 'folder']" />
            </div>
            <div class="icon">
              <font-awesome-icon class="fa-lg font-awesome-icon" :icon="['far', 'image']" />
            </div>
            <div class="icon">
              <font-awesome-icon class="fa-lg font-awesome-icon" :icon="['fas', 'microphone']" />
            </div>
            
          </div>
          <div style="height: 250px;margin-top: 10px">
            <textarea class="text-area" v-model="textarea" ></textarea>
            <el-button class="send-button" @click="SendMessage" :disabled="disable">发送</el-button>
          </div>
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<style>
.main{
  background-color: whitesmoke;
  height: 100%;
  width: 98%;
  display: flex;
  border-radius: 10px;
  padding: 0;
  margin: 0;
}
.header{
  height: 5%;
}

.foot{
  height: 30%;
}
.contain-main{
  padding: 0;
}

#chat-container {
  display: flex;
  flex-direction: column; /* 垂直排列 */
}



.button-icon{
  position: relative !important;
  border: none !important;
  background-color: whitesmoke !important;
}
.button-icon:hover{
  color: black !important;
  background-color: whitesmoke !important;
}

.button-icon:focus{
  background-color: whitesmoke!important;
}

.text-area{
  border: none;
  background-color: whitesmoke;
  /* 去除选中后的边框 */
  outline:none;
  font-size: 17px;
  font-family: 微软雅黑,serif;
  width: 98%;
  height: 90%;
  resize: none ;
  display: inline-block;
}

.send-button{
  position: relative;
  left: 95%;
  width: 80px;
  background-color: #a7d4ec;
  border: none;
  border-radius: 10px;
}
.send-button:hover{
  color: black;
  background-color: #a7d4ec;
}
.send-button[disabled] {
  opacity: 0.6;
  cursor: not-allowed;
  background-color: #a7d4ec;

}
.send-button[disabled]:hover{
  opacity: 0.6;
  cursor: not-allowed;
  background-color: #a7d4ec;
}

.icon{
  display: inline-block;
  margin-left: 20px;
}
.icon:hover{
  cursor: pointer;
}

.font-awesome-icon{
  position: relative;
  bottom: 2px

}


/* 基础样式 */
.my-avatar {
  width: 40px; /* 设置头像大小 */
  height: 45px; /* 设置头像大小 */
  border-radius: 50%; /* 圆形头像 */
  object-fit: cover;
}

.message-container {
  display: flex;
  align-items: flex-end; /* 消息框与头像底部对齐 */
  padding: 10px;
  margin-bottom: 5px;
}

.chat-message {
  display: inline-block;
  padding: 5px 10px;
  border-radius: 10px;
  margin: 0 10px; /* 与头像或边缘保持一定间距 */
  max-width: 60%; /* 消息最大宽度 */
  word-wrap: break-word; /* 确保内容换行 */
}

/* 发送的消息 */
.chat-message-sent {
  background-color: #dcf8c6;
  margin-left: auto; /* 将消息向右移动 */
}

/* 接收的消息 */
.chat-message-received {
  background-color: #f0f0f0;
  margin-right: 0; /* 将消息向右移动 */
}

/* 发送方头像 */
.avatar-sent {
  //margin-left: auto; /* 头像向右移动 */
}

/* 接收方头像 */
.avatar-received {
  /* 默认样式即为左侧，无需额外设置 */
}


</style>