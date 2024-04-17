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


let loading =ref(false)
let UserDetail
get(`/account/getOtherDetail?id=${toId}`,(data)=> {

  UserDetail = reactive({
    NickName:data.data.accountNickName,
    Avatar: "" + `${axios.defaults.baseURL}/images/${data.data.accountAvatar}`,
  });
  ListenMessage(socket,(data)=>{
    console.log(data.data.data)
    displayMessage(data.data.data,"received")
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

  const messageElement = document.createElement("div");
  messageElement.textContent = message;
  messageElement.classList.add("chat-message");

  if (sender === "received") {
    messageElement.classList.add("chat-message-received");
  } else if (sender === "sent") {
    messageElement.classList.add("chat-message-sent");
  }

  chatContainer.appendChild(messageElement);
}





</script>


<template>
  <div class="main" v-if="loading">
    <el-container class="contain-main">
      <el-header class="header">
        <div style="margin-top: 2% ;display: inline-block;font-family: 'Microsoft Yahei',serif">{{UserDetail.NickName}}</div>
        <div style="display:inline-block;position: relative;left: 77%">
          <el-button class="icon"  size="large" circle ><el-icon size="30"><Phone /></el-icon></el-button>
          <el-button class="icon"  size="large" circle ><el-icon size="30"><VideoCamera /></el-icon></el-button>
          <el-button class="icon"  size="large" circle ><el-icon size="30"><font-awesome-icon :icon="['fas', 'tv']" /></el-icon></el-button>
          <el-button class="icon"  size="large" circle ><el-icon size="30"><CirclePlus /></el-icon></el-button>
          <el-button class="icon"  size="large" circle ><el-icon size="30"><More /></el-icon></el-button>
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

.chat-message {
  padding: 10px;
  margin-bottom: 5px;
  border-radius: 10px;
  display: flex; /* 使用 Flex 布局 */
  align-items: center; /* 沿着交叉轴居中对齐 */
}

.chat-message-sent {
  background-color: #dcf8c6;
  justify-content: flex-end; /* 从右侧对齐 */
  margin-left: auto; /* 将 sent 消息向右移动 */
  max-width: 40%;
  word-wrap: break-word; /* 确保内容换行 */
}

.chat-message-received {
  margin-right: auto;
  justify-content: flex-start;
  background-color: #f0f0f0;
  max-width: 40%;
  word-wrap: break-word; /* 确保内容换行 */
}

.icon{
  position: relative;
  border: none;
  background-color: whitesmoke;
}
.icon:hover{
  background-color: whitesmoke;
}

.icon:focus{
  background-color: whitesmoke;
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


</style>