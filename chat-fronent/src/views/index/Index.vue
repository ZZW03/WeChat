<script setup>


import {inject, reactive, ref} from "vue";
import {
  Aim, Avatar,
  Back,
  Basketball, Bell, Calendar,
  Collection, DataLine, Edit,
  HomeFilled, House,
  Location, Lock, Message, MoreFilled, Operation,
  Promotion, School, Search, SwitchFilled,
  Tickets,
  User,
  View,
  Wallet
} from "@element-plus/icons-vue";
import {logout} from "@/net/account";
import {get} from "@/net/net";
import {userdetailstore} from "@/store/userdetailstore";
import {userstore} from "@/store/userstore";
import {userprivacystore} from "@/store/userprivacystore";
import router from "@/router/router";
import {ListenMessage, socketLogin} from "@/net/Socket";
import {websocketstore} from "@/store/websocketstore";

const  store = userstore
const  detailstore = userdetailstore()
const  loading = ref(true)


const socketStore = websocketstore();


function userLogout() {
    loading.value = true
    logout()
}


get('account/getAccount',(data) =>{
  store.user = data.data
  socketStore.initializeWebSocket();

  socketStore.socket.onopen = () => {

    socketLogin(data.data.id,socketStore.socket)
    ListenMessage(socketStore.socket)
  };


  get('/account/getDetail', (data) => {
    detailstore.userdetail = data.data
    get('/account/getPrivacy', (data) => {
      userprivacystore().userprivacy = data.data;
      loading.value = false
    });
  })
})







</script>


<template>
  <div class="container" v-loading="loading" element-loading-text="正在加载,请稍后~">
    <el-container class="main-container" v-if="!loading">
      <el-header class="container-head">
        <el-image style="height: 70%;padding-top: 0.5%;padding-left: 1%;display: inline-block" src="https://element-plus.org/images/element-plus-logo.svg"/>
        <el-dropdown class="User-icon">
            <el-avatar class="User-icon" :src="detailstore.avatarUrl"/>
            <template #dropdown>
              <el-dropdown-item @click="router.push({name:'usersetting'})">
                <el-icon><Edit /></el-icon>
                个人设置
              </el-dropdown-item>
              <el-dropdown-item>
                <el-icon><Bell /></el-icon>
                消息列表
              </el-dropdown-item>
              <hr>
              <el-dropdown-item @click="userLogout">
                <el-icon><Back /></el-icon>
                退出登录
              </el-dropdown-item>
            </template>
        </el-dropdown>


      </el-header>
      <el-container>
        <el-aside width="65px">
          <el-scrollbar style="height: calc(100vh - 60px)">
            <el-menu style="height: calc(100vh - 60px)"
                     router
                     :ellipsis="true"
                     :collapse="true"
                     :default-active="$route.path"
                     default-active="/index/postsquare"
                        >
              <el-menu-item index="1">
                <el-icon><Message /></el-icon>
                <template #title>查看消息</template>
              </el-menu-item>
              <el-menu-item index="/index/friendList">
                <el-icon><User /></el-icon>
                <template #title>查看好友</template>
              </el-menu-item >
              <el-sub-menu index="3">
                <template #title>
                  <el-icon>
                    <House />
                  </el-icon>
                </template>
                <el-menu-item index="/index/postsquare">
                  <template #title>
                    <el-icon>
                      <Location/>
                    </el-icon>
                    帖子广场
                  </template>
                </el-menu-item>
                <el-menu-item >
                  <template #title>
                    <el-icon>
                      <Wallet />
                    </el-icon>
                    失物招领
                  </template>
                </el-menu-item>
                <el-menu-item >
                  <template #title>
                    <el-icon>
                      <User />
                    </el-icon>
                    校园活动
                  </template>
                </el-menu-item>
                <el-menu-item>
                  <template #title>
                    <el-icon>
                      <Tickets />
                    </el-icon>
                    表白墙
                  </template>
                </el-menu-item>
                <el-menu-item>
                  <template #title>
                    <el-icon><Collection /></el-icon>
                    考研交流
                    <el-tag style="font-size: 10px;margin-left: 5%;padding: 5%">合作机构</el-tag>
                  </template>
                </el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="4">
                <template #title>
                  <el-icon><View /></el-icon>

                </template>
                <el-menu-item>
                  <el-icon>
                    <Promotion />
                  </el-icon>
                  热点事件
                </el-menu-item>
                <el-menu-item>
                  <el-icon><Basketball /></el-icon>
                  体育事件
                </el-menu-item>
                <el-menu-item>
                  <el-icon><SwitchFilled /></el-icon>
                  游戏焦点
                </el-menu-item>
                <el-menu-item>
                  <el-icon><Aim /></el-icon>
                  军事讨论
                </el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="5">
                <template #title>
                  <el-icon><MoreFilled /></el-icon>
                </template>
                <el-menu-item>
                  <el-icon><Calendar /></el-icon>
                  我的课表
                </el-menu-item>
                <el-menu-item>
                  <el-icon><DataLine /></el-icon>
                  成绩查询
                </el-menu-item>
                <el-menu-item>
                  <el-icon><School /></el-icon>
                  教室预约
                </el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="6">
                <template #title>
                  <el-icon><Operation /></el-icon>
                </template>
                <el-menu-item index="/index/usersetting">
                  <el-icon><message /></el-icon>
                  个人信息设置
                </el-menu-item>
                <el-menu-item index="/index/changepsw">
                  <el-icon><Lock /></el-icon>
                    密码重置
                </el-menu-item>
              </el-sub-menu>
            </el-menu>
          </el-scrollbar>
        </el-aside>

        <el-main id="main-container" >
          <el-scrollbar style="height: calc(100vh - 60px)">
            <router-view v-slot="{ Component }">
              <transition name="el-fade-in-linear" mode="out-in">
                <component :is="Component" style="height: 100%"/>
              </transition>
            </router-view>
          </el-scrollbar>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>


<style scoped>
.container {
  height: 100vh;
  width: 100vw;
}
.main-container{
  height: 100%;
  width: 100%;

}
.container-head{
  width: 100%;
  text-align: left;
  //display: flow;
  padding: 0;
  margin-left: 0;
  border-bottom: 1px solid var(--el-border-color);

}
.User-icon{
  position: relative;
  left: 78%;
  top: 15%;
  cursor: pointer;
}


#main-container{
    padding: 0;
    background-color: whitesmoke;
}



</style>