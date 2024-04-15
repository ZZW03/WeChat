<script setup>

import {get} from "@/net/net";
import {ref} from "vue";
import FriendDetails from "@/views/Components/FriendDetails.vue";
import router from "@/router/router";
import SessionDetail from "@/views/Components/SessionDetail.vue";



const session = ref()
get('/message/getsession',(data)=>{
  session.value = data.data
})


function clickHandel(id) {
  router.push({
    name: 'messageContent', // 目标页面的路径
    query: {
      id: id, // 携带的数据
    }
  });
}


</script>

<template>
<div>
  <el-container id="container">
    <el-aside class="elAside">

      <el-scrollbar style="height: 100%">
        <el-menu style="height: calc(93vh )"
        >
            <el-menu-item  class="item" v-for="item in session">
              <SessionDetail :FormId="item.fromId" :Id="item.toId" :SessionId="item.sessionId" @click="clickHandel(item.toId)"/>
            </el-menu-item>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-main id="main-container" >
        <router-view v-slot="{ Component }">
          <transition name="el-fade-in-linear" mode="out-in">
            <component :is="Component"/>
          </transition>
        </router-view>
    </el-main>
  </el-container>
</div>
</template>

<style scoped>

.item{
  height: 10%;
}
.item:hover{
  background-color: whitesmoke;
}
.item:focus{
  background-color: whitesmoke;
}
#main-container{
  padding: 0;
}
</style>