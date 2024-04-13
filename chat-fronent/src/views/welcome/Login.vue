<script setup>

import {inject, onMounted, onUnmounted, reactive, ref} from "vue";
import router from "../../router/router.js";
import {login} from "@/net/account";
import {Lock, User} from "@element-plus/icons-vue";
import {get} from "@/net/net";
import {ListenMessage, socketLogin} from "@/net/Socket";
import {websocketstore} from "@/store/websocketstore";
/*
* ref 单个值
* reactive 一个对象
* */
const formRef = ref()
const form = reactive({
  username: '',
  password: '',
  remember: false
})

const rules = {
  username: [
    { required: true, message: '请输入用户名' },
  ],
  password: [
    { required: true, message: '请输入密码'}
  ]
}
const socketStore = websocketstore();
function userLogin() {
  formRef.value.validate((isValid) => {
    if(isValid) {
      login(form.username, form.password, form.remember ,socketStore.socket,() => router.push({name:'index'}))
    }
  });
}








</script>



<template>
  <div id="all" >
    <div id="title" style="top: 10%;width: 100%;height: 10%;position: absolute;">
      <span style="align-content: center;margin-right: auto;margin-left: auto;position: absolute;right: 43%;font-size: 21px;">
        <b>校园论坛</b>
      </span>
    </div>

    <div id="contain" style="top: 20vh;" >
      <div style="position: absolute;margin-top: 15%;width: 100%;">
        <el-form :model="form" :rules="rules" ref="formRef">
          <el-form-item prop="username">
            <el-input v-model="form.username" autocapitalize="on" minlength="" maxlength="18" type="text" placeholder="用户名/邮箱">
              <template #prefix>
                <el-icon>
                  <User/>
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" type="password" maxlength="20" style="margin-top: 10px" placeholder="密码" show-password>
              <template #prefix>
                <el-icon>
                  <Lock/>
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-row style="margin-top: 5px">
            <el-col :span="12" style="text-align: left">
              <el-form-item prop="remember">
                <el-checkbox v-model="form.remember" label="记住我"/>
              </el-form-item>
            </el-col>
            <el-col :span="12" style="text-align: right">
              <el-link @click="router.push('/forget')">忘记密码？</el-link>
            </el-col>
          </el-row>
        </el-form>
        <div>

          <div style="margin-top: 40px">
            <el-button  @click="userLogin" style="width: 270px;left: 15%;position: relative" type="success" plain>立即登录</el-button>
          </div>
          <el-divider>
            <span style="color: grey;font-size: 13px">没有账号</span>
          </el-divider>
          <div>
            <el-button @click="router.push('enroll')" style="width: 270px;left: 15%;position: relative"  type="warning" plain>注册账号</el-button>
          </div>
        </div>
      </div>
    </div>

  </div>


</template>

<style scoped>
#all{
  position: relative;
  height: 100%;
  width: 100%;
  background-color: white;
}

#contain {
  position: relative;
  //background-color: red;
  height: 30%;
}


#button button{
  width: 14%;
}

</style>