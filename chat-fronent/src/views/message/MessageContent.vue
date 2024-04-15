<script setup>
import router from "@/router/router";
import {useRoute} from "vue-router";
import {SendToOne} from "@/net/Socket";
import {websocketstore} from "@/store/websocketstore";
import {get} from "@/net/net";
import {reactive, ref} from "vue";
import axios from "axios";
import {CirclePlus, More, Phone, Platform, Search, VideoCamera} from "@element-plus/icons-vue";

const socket = websocketstore().socket
const route = useRoute();
let toId = route.query.id

let loading =ref(false)
let UserDetail
get(`/account/getOtherDetail?id=${toId}`,(data)=> {
  console.log(data.data)
  UserDetail = reactive({
    NickName:data.data.accountNickName,
    Avatar: "" + `${axios.defaults.baseURL}/images/${data.data.accountAvatar}`,
  });
  loading.value = true
})



</script>

<template>
  <div class="main" v-if="loading">
    <el-container class="contain-main">
      <el-header class="header">
        <div style="margin-top: 2% ;display: inline-block;font-family: 'Microsoft Yahei',serif">{{UserDetail.NickName}}</div>
        <div style="display:inline-block;position: relative;left: 77%">
          <el-button class="icon"  size="large" circle ><el-icon size="30"><Phone /></el-icon></el-button>
          <el-button class="icon"  size="large" circle ><el-icon size="30"><VideoCamera /></el-icon></el-button>
          <el-button class="icon"  size="large" circle ><el-icon size="30"><Platform /></el-icon></el-button>
          <el-button class="icon"  size="large" circle ><el-icon size="30"><CirclePlus /></el-icon></el-button>
          <el-button class="icon"  size="large" circle ><el-icon size="30"><More /></el-icon></el-button>
        </div>
      </el-header>
      <el-divider/>
      <el-main class="body">Main</el-main>
      <el-divider/>
      <el-footer class="foot">Footer</el-footer>
    </el-container>
  </div>
</template>

<style scoped>
.main{
  background-color: whitesmoke;
  height: 100%;
  width: 100%;
  display: flex;
  border-radius: 10px;
  padding: 0;
  margin: 0;
}
.header{
  height: 5%;
}
.body{
  height: 50%;
}
.foot{
  height: 30%;
}
.contain-main{
  padding: 0;
}

.icon{
  border: none;
  background-color: whitesmoke;
}
.icon:hover{
  background-color: whitesmoke;
}

.icon:focus{
  background-color: whitesmoke;
}


</style>