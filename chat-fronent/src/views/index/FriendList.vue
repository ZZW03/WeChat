<script setup>
import {ArrowRight, Plus, Search} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import router from "@/router/router";

const ToUser=ref()



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
          <el-button @click="router.push({name:'showInformation'})" class="information" style="width: 80%;border: none;margin-left:10%;margin-top:5% ; "  >好友通知 &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp; &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<el-icon><ArrowRight /></el-icon> </el-button>
          <el-button class="information" style="width: 80%;border: none;margin-left:10%;margin-top:5% ; "  >群聊通知 &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp; &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<el-icon><ArrowRight /></el-icon> </el-button>
        </div>
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
</style>