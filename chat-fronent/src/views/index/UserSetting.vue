<script setup>
import {userdetailstore} from "@/store/userdetailstore"
import Card from "@/views/Components/Card.vue";
import {Close, Edit, Message, Notebook, Refresh, Select, User} from "@element-plus/icons-vue";
import {userstore} from "@/store/userstore";
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {imgHeader} from "@/net/header";
import {get} from "@/net/net";
import {ModifyEmail, SaveDetail, Sendcode} from "@/net/account";
import router from "@/router/router";

const  store = userdetailstore()
let UserDetail
get('/account/getDetail', (data) => {
  store.userdetail = data.data
  UserDetail = reactive({
    NickName: "" + data.data.accountNickName,
    Sex: "" + data.data.accountSex,
    Avatar: "" + userdetailstore().avatarUrl,
    Age: "" + data.data.accountAge,
    Address: "" + data.data.accountAddress,
    QQ:""+data.data.accountQq,
    Phone:""+data.data.accountPhone,
  });
  get('account/getAccount',(data) =>{
    userstore().user = data.data
    loading.value = false;
  })
});







const  loading = ref(true)
const formRef = ref()
const modify = ref(true)
const ChangeMail = reactive({
    OldMail:"",
    NewMail:"",
    Code:"",
})
const rules = {
  OldMail: [
    { required: true, message: '输入旧邮箱' },
    {  type: "email" ,    message: '邮箱格式错误',trigger: ['blur']},
    { validator: validateOldMail,message:'原邮箱错误' ,trigger: ['blur', 'change'] },
  ],
  NewMail: [
    { required: true, message: '输入新邮箱' },
    {  type: "email" ,    message: '邮箱格式错误',trigger: ['blur']},
  ],

}

function validateOldMail(){
  return ChangeMail.OldMail === userstore().user.accountEmail;
}

function SendC(){
  formRef.value.validate((isValid) => {
    if (isValid) {
      Sendcode(ChangeMail.NewMail,"1")
    }else{
      ElMessage.warning("请先填写")
    }
  })
}

function ChangeM(){
  formRef.value.validate((isValid) => {
    if(isValid){
      ModifyEmail(ChangeMail.OldMail,ChangeMail.NewMail,ChangeMail.Code,()=> router.push({name:'usersetting'}))
    }else{
      ElMessage.warning("请先填写")
    }
  })
}

function SaveD(){
  SaveDetail(UserDetail.NickName,UserDetail.Sex,UserDetail.Age,UserDetail.Address,UserDetail.QQ,UserDetail.Phone)
  ChangeModify()
}

function ChangeModify(){
  modify.value = !modify.value;
}

function beforeAvatarUpload(rawFile) {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('头像只能是 JPG/PNG 格式的')
    return false
  } else if(rawFile.size / 1024 > 100) {
    ElMessage.error('头像大小不能大于 100KB')
    return false
  }
  return true
}

function uploadSuccess(response){
  ElMessage.success('头像上传成功')
  console.info(response)
  userdetailstore().userdetail.accountAvatar = response.data.substring(8)
}

</script>

<template>
  <div style="display: flex" v-loading="loading">
    <div id="setting-left" v-if="!loading">
      <card :icon="User" title="账号信息设置" desc="在此编辑个人信息">
        <el-form :model="UserDetail" >
          <el-form-item label="用户名 :&emsp;">
            <el-input  v-model="UserDetail.NickName"  :disabled="modify"/>
          </el-form-item>
          <el-form-item  style="width: 300px" label="性别 :">
            <el-radio-group v-model="UserDetail.Sex" :disabled="modify">
              <el-radio label="1">男</el-radio>
              <el-radio label="2">女</el-radio>
            </el-radio-group>
            <el-form-item label="年龄:" style="margin-left: 20px">
              <el-input style="width: 40px;position: relative;left: 10px"  :disabled="modify" v-model="UserDetail.Age"/>
            </el-form-item>
          </el-form-item>
          <el-form-item  label="手机号 : &ensp;" >
            <el-input  v-model="UserDetail.Phone" :disabled="modify" />
          </el-form-item>
          <el-form-item  label="QQ号:&emsp;&ensp;" >
            <el-input v-model="UserDetail.QQ" :disabled="modify"/>
          </el-form-item>
          <el-form-item  label="地址信息 :" :disabled="modify">
            <el-input type="textarea" v-model="UserDetail.Address" :rows="6"  :disabled="modify"/>
          </el-form-item>
        </el-form>
        <el-form-item >
          <el-button :icon="Select" plain style="width: 30%;margin-left: 8%;display: inline-block" :disabled="modify" type="success" @click="SaveD()">保存</el-button>
          <el-button :icon="Edit " plain style="width: 30%;margin-left: 8%;display: inline-block"  type="success" @click="ChangeModify">进行修改</el-button>
        </el-form-item>
      </card>


      <card style="margin-top: 1%"  :icon="Message" title="电子邮箱" desc="你可以在此设置电子邮箱" >
        <el-form label-position="top" :model="ChangeMail" :rules="rules" ref="formRef">
          <el-form-item prop="OldMail">
            <el-input v-model="ChangeMail.OldMail" placeholder="原电子邮箱"/>
          </el-form-item>
          <el-form-item prop="NewMail">
            <el-input v-model="ChangeMail.NewMail" placeholder="现电子邮箱"/>
          </el-form-item>
          <el-form-item >
            <el-row>
              <el-col :span="18">
                <el-input v-model="ChangeMail.Code" maxlength="6" placeholder="请输入验证码" />
              </el-col>
              <el-col :span="6">
                <el-button @click="SendC" type="success" plain style="">获取验证码</el-button>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item>
            <el-button :icon="Refresh" plain style="width: 30%;margin-left: 8%;display: inline-block"  type="success" @click="ChangeM">重置</el-button>
          </el-form-item>
        </el-form>
      </card>
    </div>

    <div id="setting-right"   v-if="!loading">
      <div style="position: sticky;top: 20px">
        <card>
          <div style="text-align: center;padding: 5px 15px 0 15px">
            <div>
              <el-avatar :src="store.avatarUrl" size="large" />
              <div style="margin: 5px 0">
                <el-upload
                    :action="axios.defaults.baseURL + '/avatar'"
                    :show-file-list="false"
                    :before-upload="beforeAvatarUpload"
                    :on-success="uploadSuccess"
                    :headers="imgHeader()">
                  <el-button size="small" round>修改头像</el-button>
                </el-upload>
              </div>
              <div>你好,{{UserDetail.NickName}}</div>
            </div>
            <div style="color: grey">
              <el-divider style="margin-top: 10%"></el-divider>
              <div style="margin-top: -5%">
                {{  }}
              </div>
            </div>
          </div>
        </card>
        <card style="font-size: 13px">
          <div>
            注册时间: {{ userstore().user.registerTime }}
          </div>
          <div style="color: grey">
            {{ }}
          </div>
        </card>
      </div>
    </div>

  </div>
</template>

<style scoped>

#setting-left{
  flex: 1;
  margin: 20px;
}

#setting-right{
  width:  25%;
  margin: 20px;
}
</style>