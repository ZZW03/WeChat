<script setup>
import {ArrowRight, Plus, Search, User} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import router from "../../router/router.js";
import {get} from "@/net/net";
import FriendDetails from "@/views/Components/FriendDetails.vue";
import {userdetailstore} from "@/store/userdetailstore";
import {websocketstore} from "@/store/websocketstore";
import {SendToAddFriend, SendToOne} from "@/net/Socket";
import {findByName} from "@/net/account";
import {userstore} from "@/store/userstore";
import {ElMessage} from "element-plus";
import axios from "axios";

const ToUser=ref()
const ListData = ref()

get('/friend/SelShip',(data)=>{
  ListData.value = data.data
})
let show = ref(false)
let textarea = ref("")
const socket = websocketstore().socket

let AddFriendDetails ;
function isShow(){
  findByName(ToUser.value,(data)=>{
    if(userstore.user.id === data){
      ElMessage.error("不能添加自己")
    }else{
      get(`/account/getOtherDetail?id=${data}`,(evt)=>{

        AddFriendDetails = reactive({
          id: evt.data.accountId,
          NickName: evt.data.accountNickName,
          Avatar: "" + `${axios.defaults.baseURL}/images/${evt.data.accountAvatar}`,
          Desc: evt.data.accountDesc
        })
        show.value = !show.value
      })
    }
  })
}


function addFriend(){
    SendToAddFriend(userstore.user.id,AddFriendDetails.id,socket,3003,textarea.value,()=>{
      ElMessage.success("发送成功")
    })
}



</script>

<template>
  <div>
    <el-container id="container">
      <el-aside class="elAside">
        <div>
        <el-input placeholder="请输入对方账号" :prefix-icon="Search" v-model="ToUser" style="width: 70%;margin-left: 5%;margin-top: 5%;"/>
        <el-button  class="add" :icon="Plus" size="default" style="margin-left: 2%;margin-top: 5%" @click="isShow"/>
        <el-drawer v-model="show"
                   :direction="'ltr'"
                   title="申请添加好友"
                   :close-on-click-modal="false"
                   >
          <div style="height: 60%">
            <el-divider class="divider"></el-divider>
              <div style="margin-left: 12%">
                <el-avatar size="default" class="User-icon" :src="AddFriendDetails.Avatar"/>
                <span style="display: inline-block; height:50%;width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; position: relative; bottom: 19px;font-size: 15px">{{AddFriendDetails.NickName}}</span>
                <span style="display: inline-block; width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; position: relative; right: 100px; top: 1px; font-size: 11px;color: grey">{{AddFriendDetails.Desc}}</span>
              </div>
              <div style="height: 70%">
                <span style="font-size: 14px;color: grey;margin-left: 10%;">填写验证消息</span>
                <textarea class="text-area" v-model="textarea" ></textarea>
                <div style="margin-top: 2%;position: relative;margin-left: 65%">
                  <el-button class="button-send" @click="addFriend">发送</el-button>
                  <el-button class="button-cancel" @click="isShow">取消</el-button>
                </div>

              </div>

          </div>
        </el-drawer>
        </div>
        <div style="width: 100%;">
          <el-button @click="router.push({name:'friendInformation'})" class="information" style="width: 85%;border: none;margin-left:10%;margin-top:5% ; "  >好友通知 &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp; &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<el-icon><ArrowRight /></el-icon> </el-button>
          <el-button @click="router.push({name:'groupInformation'})"  class="information" style="width: 85%;border: none;margin-left:10%;margin-top:5% ; "  >群聊通知 &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp; &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<el-icon><ArrowRight /></el-icon> </el-button>
        </div>

        <el-divider/>
        <el-scrollbar style="height: 70%">
          <el-menu style="height: 70%"
          >
            <el-sub-menu >
              <template #title>
                <el-icon>
                  <User />
                </el-icon>
                <span style="font-family: Microsoft JhengHei,serif;color: grey">好友列表</span>
              </template>
              <el-menu-item  class="item" v-for="item in ListData">
                <FriendDetails :Id="item.toId"/>
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-scrollbar>
      </el-aside>

      <el-main id="main-container" >
        <el-scrollbar style="height: calc(100vh - 100px)">
          <router-view v-slot="{ Component }">
            <transition name="el-fade-in-linear" mode="out-in">
              <component :is="Component" style="height: 100%"/>
            </transition>
          </router-view>
        </el-scrollbar>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
#container{
  height: 100%;
}

.elAside{
  background-color: white;
}

.information:hover{
    background-color: whitesmoke;
}
.information:focus{
  background-color: whitesmoke;
}

.item:hover{
  background-color: whitesmoke;
}

.item:focus{
  background-color: whitesmoke;
}

.add{
  background-color: white;
}

.add:hover{
  background-color: whitesmoke;
}

.add:focus{
  background-color: whitesmoke;
}

:deep(.el-drawer__header) {
  margin-left: 37%;
  margin-bottom: 10px;
  position: relative;
  bottom: 10px;
}

:deep(.el-checkbox-group .el-checkbox) {
  margin-right: 10px;
}
:deep(.el-checkbox-group .el-checkbox) {
  margin-right: 10px;
}


:deep(.el-drawer.ltr){
  margin: auto;
  margin-left: 38%;
  height: 40%;
  width: 30%;
  background-color: whitesmoke;
}

:deep(.el-drawer) {
  border-radius: 20px;
}

:deep(.el-drawer__body) {

  padding: 0;
}

.divider{
  margin: 0;
  margin-bottom: 10px;
  padding: 0;
}

.User-icon{
  margin-bottom: 5px;
  margin-right: 8px;
  margin-left: -20px;
}
.text-area{
  margin: auto;
  margin-top: 2%;
  border: none;
  border-radius: 10px;
  background-color: white;
  /* 去除选中后的边框 */
  outline: #0f60d7;
  font-size: 15px;
  font-family: 微软雅黑,serif;
  width: 80%;
  height: 100%;
  resize: none ;
  display: block;
}

.button-send{
  color: white;
  background-color: #7b97e1;
}

.button-send:hover{
  color: white;
  background-color: #2773d3;
}

.button-send:focus{
  color: white;
  background-color: #2773d3;
}

.button-cancel{
  color: black;
  background-color: white;
}

.button-cancel:hover{
  color: black;
  background-color: whitesmoke;
}

.button-cancel:focus{
  color: black;
  background-color: whitesmoke;
}

</style>