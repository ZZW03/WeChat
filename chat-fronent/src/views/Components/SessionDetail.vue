<script setup>

import {reactive, ref} from "vue";
import {get} from "@/net/net";
import axios from "axios";

const prop = defineProps({
  FormId:Number,
  Id : Number,
  SessionId: Number
})

let messageBodyJson
let UserDetail
let MessageDetail
let loading = ref(false)

get(`/account/getOtherDetail?id=${prop.Id}`,(data)=>{
  UserDetail = reactive({
    NickName: "" + data.data.accountNickName,
    Avatar: "" + `${axios.defaults.baseURL}/images/${data.data.accountAvatar}`,
  });

  get(`/message/getOneSessionMessage?SessionId=${prop.SessionId}`,(data)=>{
    MessageDetail = reactive({
      nickName:data.data.accountNickName + ":",
      fromId:data.data.fromId,
      messageBody:data.data.messageBody
    })
    messageBodyJson = JSON.parse(MessageDetail.messageBody)
    if(prop.FormId == MessageDetail.fromId){
      MessageDetail.nickName = ""
    }
    loading.value = true;
  })
})



</script>

<template>
  <div v-if="loading">
    <el-avatar class="User-icon" :src="UserDetail.Avatar"/>
    <span style="display: inline-block; height:50%;width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; position: relative; bottom: 8px;font-size: 15px">{{UserDetail.NickName}}</span>
    <span style="display: inline-block; width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; position: relative; right: 100px; top: 10px; font-size: 11px;color: grey">{{MessageDetail.nickName}}  {{messageBodyJson.msgbody}}</span>
  </div>
</template>

<style scoped>

.User-icon{
  margin-bottom: 3%;
  margin-right: 1%;
  margin-left: 1%;
}
div{
  font-family: "Microsoft JhengHei",serif;
}

</style>