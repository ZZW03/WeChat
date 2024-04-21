<script setup>

import {get} from "@/net/net";
import {reactive, ref} from "vue";
import axios from "axios";
import {SendToAddFriend} from "@/net/Socket";
import {userstore} from "@/store/userstore";
import {ElMessage} from "element-plus";
import {websocketstore} from "@/store/websocketstore";

const prop = defineProps({
  Item : Object
})

const socket = websocketstore().socket
const loading = ref(false)
let UserDetail
get(`/account/getOtherDetail?id=${prop.Item.fromId}`,(data)=>{
  UserDetail = reactive({
    NickName: "" + data.data.accountNickName,
    Avatar: "" + `${axios.defaults.baseURL}/images/${data.data.accountAvatar}`,
  });
  loading.value = true;
})

const isdisabled = ref()
const approve = ref()
if(prop.Item.approveStatus === 0){
  approve.value ="同意"
  isdisabled.value = false
}else{
  approve.value ="已同意"
  isdisabled.value = true
}


function approveAddFriend(){
  SendToAddFriend(userstore.user.id,prop.Item.fromId,socket,3000,prop.Item.reqId,()=>{
    approve.value ="已同意"
    isdisabled.value = true
    ElMessage.success("您已经,同意请求")
  })
}

</script>

<template>
  <div class="main-container" v-if="loading">
    <el-avatar class="User-icon" :src="UserDetail.Avatar"/>
    <div class="floor">
      <div class="First-floor">{{UserDetail.NickName}}<span style="color: black;margin-left: 2%">请求添加好友</span><span style="font-size: 11px;margin-left: 2%;color: grey">{{prop.Item.createTime}}</span></div>
      <div class="Second-floor">验证消息：{{prop.Item.addWording}}</div>
    </div>
    <div class="approve-div" style="display: block">
      <el-button @click="approveAddFriend" class="approve-button" style="border: none" :disabled="isdisabled">{{approve}}</el-button>
    </div>
  </div>
</template>

<style scoped>

.main-container{
  display: flex;
  width: 70%;
  height: 80px;
  background-color: white;
  border-radius: 10px;
  margin: auto;
}

.User-icon{
  margin-top: 2%;
  margin-left: 5%;
}
.floor{
  width: 60%;
  height: 60%;
  display: block;
  margin-top:auto;
  margin-bottom:auto;
}

.First-floor{
  display: block;
  width: 100%;
  height: 40%;
  font-size: 14px;
  color: #0f60d7;
  margin-left: 2%;
}

.Second-floor{
  height: 40%;
  display: block;
  width: 100%;
  font-size: 14px;
  margin-left: 2%;
  margin-top: 1%;
  color: grey;
}

.approve-div{
  margin-top:auto;
  margin-bottom:auto;
  margin-left: 20%;

}
.approve-button:hover{
  color:black;
  background-color: whitesmoke;
}

.approve-button:focus{
  color:black;
  background-color: whitesmoke;
}

</style>