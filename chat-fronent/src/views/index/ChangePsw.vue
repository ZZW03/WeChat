<script setup>
import {Edit, Pointer, Select, Setting} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import Card from "@/views/Components/Card.vue";
import {resetPsw,  savePrivacy} from "@/net/account";
import {userprivacystore} from "@/store/userprivacystore";




let loading =ref(false)
const formRef = ref()
const FormItem = reactive({
  password:'',
  newPassword:'',
  confirmPassword:''
})

const Privacy = reactive({
  phone:userprivacystore().userprivacy.phone,
  gender:userprivacystore().userprivacy.accountSex,
  email:userprivacystore().userprivacy.accountEmail,
  qq:userprivacystore().userprivacy.qq,
})






const rules = {
  password: [
    { required: true, message: '输入旧密码' },
  ],
  newPassword: [
    { required: true, message: '输入新密码' },
    { min:8, message: '密码长度不能小于8位',trigger: ['blur', 'change']}
  ],
  confirmPassword:[
    { required: true, message: '重复输入新密码' },
    { min:8, message: '密码长度不能小于8位',trigger: ['blur', 'change']},
    { validator: validatePsw,message:'两次密码不同' ,trigger: ['blur', 'change'] },
  ]
}



function validatePsw(){
  return FormItem.newPassword === FormItem.confirmPassword;
}

function ChangePSW(){
  formRef.value.validate((is)=>{
    if(is){
      resetPsw(FormItem.password,FormItem.newPassword,FormItem.confirmPassword)
    }else {
        ElMessage.warning("请先填写")
    }
  })
}

function SavePrivacy(type,status){
  savePrivacy(type,status)

}

</script>

<template>
<div style="display: flex;margin: auto" v-loading="loading">
  <div id="left">
    <card title="隐私设置" desc="请各位小伙伴注重自己的隐私" :icon="Setting" v-loading="loading">
      <div id="checkbox">
        <el-checkbox @change="SavePrivacy('phone',Privacy.phone)"
                     v-model="Privacy.phone" > 公开展示我的手机号</el-checkbox>
        <el-checkbox @change="SavePrivacy('qq',Privacy.qq)"
                     v-model="Privacy.qq">公开展示我的qq号</el-checkbox>
        <el-checkbox @change="SavePrivacy('accountEmail',Privacy.email)"
                     v-model="Privacy.email">公开展示我的电子邮箱</el-checkbox>
        <el-checkbox @change="SavePrivacy('accountSex',Privacy.gender)"
                     v-model="Privacy.gender">公开展示我的性别</el-checkbox>
      </div>
    </card>
    <Card style="margin: 20px 0" :icon="Edit" title="密码修改" desc="保护好自己的密码">
      <el-form  :model="FormItem" :rules="rules" ref="formRef" label-width="100" style="margin-left: 2%">
        <el-form-item label="原密码" prop="password" >
          <el-input maxlength="16" v-model="FormItem.password" type="password" show-password/>
        </el-form-item >
        <el-form-item label="新密码" prop="newPassword">
          <el-input maxlength="16" v-model="FormItem.newPassword" type="password" />
        </el-form-item>
        <el-form-item label="重复新密码" prop="confirmPassword">
          <el-input  maxlength="16" v-model="FormItem.confirmPassword" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button :icon="Select" plain style="position: relative;left: 20%;width: 50%;margin-left: 8%;display: inline-block"  type="success" @click="ChangePSW">保存</el-button>
        </el-form-item>
      </el-form>
    </Card>
  </div>
  <div id="right">
    <Card :icon="Pointer" title="温馨提示" desc="&emsp;请不要随意的将密码给他人">

    </Card>
  </div>

</div>
</template>

<style scoped>

#left{
  flex: 1;
  margin: 20px;

}

#right{
  width:  25%;
  margin: 20px;

}

#checkbox{
  margin: 10px 0 0 10px;
  display: flex;
  flex-direction: column;
}


</style>