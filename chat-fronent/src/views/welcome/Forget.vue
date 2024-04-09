<template>
  <div>
    <div style="margin: 30px 20px">
      <el-steps :active="active" finish-status="success" align-center>
        <el-step title="验证电子邮件" />
        <el-step title="重新设定密码" />
      </el-steps>
    </div>


    <transition name="el-fade-in-linear" mode="out-in">
      <div style="text-align: center;margin: 0 20px;height: 100%" v-if="active=== 0">

        <div style="margin-top: 80px">
          <div style="font-size: 25px;font-weight: bold">重置密码</div>
          <div style="font-size: 14px;color: grey">请输入需要重置密码的电子邮件地址</div>
        </div>

        <div style="margin-top: 50px">
          <el-form :model="form" :rules="rule" @validate="onValidate" ref="formRef">
            <el-form-item prop="mail">
              <el-input v-model="form.mail"  placeholder="电子邮件地址">
                <template #prefix>
                  <el-icon><Message /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="code">
              <el-row :gutter="10" style="width: 100%">
                <el-col :span="17">
                  <el-input v-model="form.code" :maxlength="6" type="text" placeholder="请输入验证码">
                    <template #prefix>
                      <el-icon><EditPen /></el-icon>
                    </template>
                  </el-input>
                </el-col>
                <el-col :span="5">
                  <el-button type="success"  @click="sendcode"
                             :disabled="(isEmailValid) || coldTime > 0">
                    {{coldTime > 0 ? '请稍后 ' + coldTime + ' 秒' : '获取验证码'}}
                  </el-button>
                </el-col>
              </el-row>
            </el-form-item>
          </el-form>
        </div>
        <div style="margin-top: 70px">
          <el-button @click="confirmCode(form.mail,form.code,)" style="width: 270px;" type="danger" plain>开始重置密码</el-button>
        </div>
      </div>
    </transition>

    <transition name="el-fade-in-linear" mode="out-in">
      <div style="text-align: center;margin: 0 20px;height: 100%" v-if="active === 1">
        <div style="margin-top: 80px">
          <div style="font-size: 25px;font-weight: bold">重置密码</div>
          <div style="font-size: 14px;color: grey">请填写您的新密码，务必牢记，防止丢失</div>
        </div>
        <div style="margin-top: 50px">
          <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
            <el-form-item prop="password">
              <el-input v-model="form.password" :maxlength="18" type="password" placeholder="新密码">
                <template #prefix>
                  <el-icon><Lock /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="repassword">
              <el-input v-model="form.repassword" :maxlength="18" type="password" placeholder="重复新密码">
                <template #prefix>
                  <el-icon><Lock /></el-icon>
                </template>
              </el-input>
            </el-form-item>
          </el-form>
        </div>
        <div style="margin-top: 70px">
          <el-button @click="change(form.mail,form.password,form.repassword,() => router.push({name:'login'}))" style="width: 270px;" type="danger" plain>立即重置密码</el-button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";

import axios from "axios";
import {ElMessage} from "element-plus";
import {confirmReset, forgetPsw, Sendcode} from "@/net/account";
import {EditPen, Lock} from "@element-plus/icons-vue";
import router from "@/router/router";



const active = ref(0)
const formRef = ref()
const isEmailValid = ref(false)
const coldTime = ref(0)

const form = reactive({
  password:'',
  repassword:'',
  mail:'',
  code:'',
})

const rules = {
  password: [
    {  required: true, message: '请输入密码'},
    { min: 8, message: '密码长度不能少于8个字符',trigger: ['blur'] },
    { max: 18, message: '密码长度不能多于18个字符',trigger: ['blur'] },
  ],

  repassword:[
    { required: true, message: '请在此输入密码' },
    { validator:validateRepassword ,message: '两次密码不一致' ,trigger: ['blur'] }
  ],

  mail:[
    { required: true, message: '请输入邮件' },
    {  type: "email" ,    message: '邮箱格式错误'},

  ],

}

const rule = {
  mail:[
    { required: true, message: '请输入邮件' },
    {  type: "email" ,    message: '邮箱格式错误'},

  ],
}

const onValidate = (prop, isValid) => {
  if(prop === 'email')
    isEmailValid.value = isValid
}


function validateRepassword(){
  return form.password === form.repassword;
}


function sendcode() {
  formRef.value.validate((isValid) => {
    if (isValid) {
      Sendcode(form.mail,"forget")
    }
  })
}




function confirmCode(mail,code,success) {
  formRef.value.validate((isValid) => {
    if(isValid) {
       confirmReset(mail,code,()=>{active.value++});
    }
  })
}

function change(email,newPassword,newPasswordRepeat,success){
  formRef.value.validate((isValid) => {
    if (isValid) {
      forgetPsw(email,newPassword,newPasswordRepeat,success)
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