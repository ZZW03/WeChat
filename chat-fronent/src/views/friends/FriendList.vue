<script setup>
import {ArrowRight, Plus, Search, User} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import router from "@/router/router";
import {get} from "@/net/net";
import FriendDetails from "@/views/Components/FriendDetails.vue";
import {userdetailstore} from "@/store/userdetailstore";
import {websocketstore} from "@/store/websocketstore";
import {SendToAddFriend, SendToOne} from "@/net/Socket";
import {findByName} from "@/net/account";
import {userstore} from "@/store/userstore";
import {ElMessage} from "element-plus";

const ToUser=ref()
const ListData = ref()

get('/friend/SelShip',(data)=>{
  ListData.value = data.data
})

const socket = websocketstore().socket

function addFriend(){
  findByName(ToUser.value,(data)=>{
    SendToAddFriend(userstore.user.id,data,socket,3000,()=>{
      ElMessage.success("发送成功")
    })
  })

}



</script>

<template>
  <div>
    <el-container id="container">
      <el-aside class="elAside">
        <div>
        <el-input placeholder="请输入对方账号" :prefix-icon="Search" v-model="ToUser" style="width: 70%;margin-left: 5%;margin-top: 5%;"/>
        <el-button  class="add" :icon="Plus" size="default" style="margin-left: 2%;margin-top: 5%" @click="addFriend"/>
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
</style>