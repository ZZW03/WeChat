<script setup>
import {ArrowRight, Plus, Search, User} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import router from "@/router/router";
import {get} from "@/net/net";
import FriendDetails from "@/views/Components/FriendDetails.vue";

const ToUser=ref()
const ListData = ref()

get('/friend/SelShip',(data)=>{
  ListData.value = data.data
})



</script>

<template>
  <div>
    <el-container id="container">
      <el-aside class="elAside">
        <div>
        <el-input :prefix-icon="Search" v-model="ToUser" style="width: 70%;margin-left: 5%;margin-top: 5%;"/>
        <el-button :icon="Plus" size="default" style="margin-left: 2%;margin-top: 5%"></el-button>
        </div>
        <div style="width: 100%;">
          <el-button @click="router.push({name:'showInformation'})" class="information" style="width: 85%;border: none;margin-left:10%;margin-top:5% ; "  >好友通知 &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp; &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<el-icon><ArrowRight /></el-icon> </el-button>
          <el-button class="information" style="width: 85%;border: none;margin-left:10%;margin-top:5% ; "  >群聊通知 &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp; &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<el-icon><ArrowRight /></el-icon> </el-button>
        </div>
        <el-divider/>

        <el-scrollbar style="height: calc(100vh - 60px)">
          <el-menu style="height: calc(100vh - 60px)"
          >
            <el-sub-menu >
              <template #title>
                <el-icon>
                  <User />
                </el-icon>
                <b>好友列表</b>
              </template>
              <el-menu-item  class="item" v-for="item in ListData">
                <FriendDetails :Id="item.toId"/>
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
  </div>
</template>

<style scoped>
#container{
  height: 100vh;
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
</style>