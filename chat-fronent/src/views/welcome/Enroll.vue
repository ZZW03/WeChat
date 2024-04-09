<template>
  <div id="all" >
    <div id="title" style="top: 10%;width: 100%;height: 10%;position: relative;">
      <div style="align-content: center;margin-right: auto;margin-left: auto;position: absolute;right: 43%;font-size: 21px;">
        <b>注册新用户</b>
        <div style="font-size: 14px;color: grey">欢迎您注册</div>
      </div>
    </div>

    <div id="contain" >
      <div style="position: relative;margin-top: 15%;width: 100%;top:20%">
        <el-form :model="form" :rules="rules" ref="formRef">
          <el-form-item prop="mail" style="position: relative;left: 10%">
            <el-input style="width: 70%" v-model="form.mail" prefix-icon="Message" type="email" placeholder="请输入邮箱" ></el-input>
          </el-form-item>
          <el-form-item prop="username" style="position: relative;left: 10%">
              <el-input style="width: 70%" v-model="form.username" prefix-icon="User" type="text" placeholder="请输入用户名" ></el-input>
          </el-form-item>
          <el-form-item prop="password" style="position: relative;left: 10%">
            <el-input   v-model="form.password" style="width: 70%;position: relative;bottom: 6%" prefix-icon="Lock" type="password" placeholder="请输入密码" show-password></el-input>
          </el-form-item>
          <el-form-item prop="repassword" style="position: relative;left: 10%">
            <el-input  v-model="form.repassword" style="width: 70%;bottom: 10%" prefix-icon="Lock" type="password" placeholder="重新输入密码" show-password></el-input>
          </el-form-item>
          <el-form-item style="position: relative;left: 10%;">
            <el-input  v-model="form.code" style="width: 50%;position: relative" prefix-icon="Discount" type="text" placeholder="输入验证码" show-password></el-input>
            <el-button @click="sendcode(form.mail,'register')" style="width: 20%;" type="default">发送邮箱</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div>
        <div style="margin-top: 100px">
          <el-button  @click="enroll(form.username,form.password,form.mail,form.code,()=>{router.push({name:'login'})})" style="width: 270px;left: 15%;position: relative" type="success" plain>注册</el-button>
        </div>
        <el-divider>
          <span style="color: grey;font-size: 13px">已有账号</span>
        </el-divider>
        <div>
          <el-button @click="evt => {router.push({name:'login'})}" style="width: 270px;left: 15%;position: relative"  type="warning" plain>前往登录</el-button>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router/router";
import {enRoll, Sendcode} from "@/net/account";



const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)){
    callback(new Error('用户名不能包含特殊字符，只能是中文/英文'))
  } else {
    callback()
  }
}

  const formRef = ref()

  const form = reactive({
    username:'',
    password:'',
    repassword:'',
    mail:'',
    code:'',
  })

  const rules = {
    username: [
      { validator: validateUsername, trigger: ['blur', 'change'] },
      { min: 8, message: '用户名长度不能少于8个字符' ,trigger: ['blur'] },
      { max: 18, message: '用户名长度不能多于12个字符' ,trigger: ['blur']},

    ],
    password: [
      {  required: true, message: '请输入密码'},
      { min: 8, message: '密码长度不能少于8个字符' ,trigger: ['blur']},
      { max: 18, message: '密码长度不能多于12个字符' ,trigger: ['blur']},

    ],
    repassword:[
      { required: true, message: '请在此输入密码' },
      { validator:validateRepassword ,message: '两次密码不一致' ,trigger: ['blur'] }

    ],
    mail:[
      { required: true, message: '请输入邮件' },
      {  type: "email" ,    message: '邮箱格式错误',trigger: ['blur']},

    ],
    code:[
      { required: true, message: '不能为空' },

    ]
  }



function validateRepassword(){
    return form.password === form.repassword;
}

function sendcode(mail,type) {
  formRef.value.validate((isValid) => {
    if (isValid) {
      Sendcode(mail,type)
    }
  })
}

function enroll(accountUsername,accountPassword,accountEmail,code,success){
  formRef.value.validate((isValid) => {
    if (isValid) {
      enRoll(accountUsername,accountPassword,accountEmail,code,success)
    }
  })
}

</script>



<style scoped>

#all{
  position: relative;
  height: 100%;
  width: 100%;
  background-color: white;
}

#contain{
  position: relative;
//background-color: red;
  height: 30%;
}

</style>