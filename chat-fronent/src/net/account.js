import {deleteAccessToken, storeAccessToken} from "@/net/header";
import {ElMessage} from "element-plus";
import {get, post, postE} from "@/net/net";



/*
*   登录
*   1.请求'/api/auth/login'
*   2.设置data数据
*   3.设置请求头
*   4.返回data 通过storeAccessToken方法 并且通过remember 来判断data是放在Local 还是 Session 中
*   5.success(data) 用success 处理data
* */
function login(username, password, remember, success){
    post('/auth/login', {
        username: username,
        password: password
    },(data) => {
        if(data.code === 200 ){
            storeAccessToken(remember, data.data.token, data.data.expire)
            ElMessage.success(`登录成功，欢迎 ${data.data.name} 来到我们的系统`)
            success(data)
        }else{

            ElMessage.error(data)
        }
    })
}

function logout(){
    post('/auth/logout',{},(response)=>{
        if(response.code === 200){
                    ElMessage.success(response.data)
                    deleteAccessToken()
                    setTimeout(()=>{
                        location.reload ()
                    },500);
                }else {
                    ElMessage.error(response.data)
                }
    })
}
/*
*
* 发送邮件
*
* */
function Sendcode(mail,type){
    postE('/account/sendcode',{
        email: mail,
        type: type,
    },(data)=>{
        if(data.code === 200 ){
            ElMessage.success(data.data)
        }else{
            ElMessage.error(data.message)
        }
    })
}




/*
* 重置密码
* */
function resetPsw(password,newPassword,newPasswordRepeat){
    postE('/account/resetPsw',{
        password:password,
        newPassword:newPassword,
        newPasswordRepeat:newPasswordRepeat
    },(data)=>{
        if(data.code === 200){
            ElMessage.success(data.data)
        }else{
            ElMessage.error(data.message)
        }
    })

}

function enRoll(accountUsername,accountPassword,accountEmail,code,success){
    postE('account/enRoll',{
        accountUsername:accountUsername,
        accountPassword:accountPassword,
        accountEmail:accountEmail,
        code:code
    },(data)=>{
        if(data.code === 200){
            ElMessage.success(data.data)
            success(data)
        }else{
            ElMessage.error(data.message)
        }
    })
}



function  confirmReset(email,code,success){
    get(`/account/confirmCode?email=${email}&code=${code}`,(data)=>{
        if(data.code === 200){
            ElMessage.success(data)
            success(data)
        }else{
            ElMessage.error(data.message)
        }

    })
}

function forgetPsw(email,newPassword,newPasswordRepeat,success){
    postE('/account/forgetPsw',{
        email:email,
        newPassword:newPassword,
        newPasswordRepeat:newPasswordRepeat
    },(data) =>{
        if(data.code === 200){
            ElMessage.success(data)
            success(data)
        }else{
            ElMessage.error(data.message)
        }
    })
}

function SaveDetail(NickName,Sex,Age,Address,QQ,Phone){
    postE('/account/updateDetail',{
        accountNickName:NickName,
        accountAddress:Address,
        accountSex:Sex,
        accountAge:Age,
        accountQq:QQ,
        accountPhone:Phone
    },(data)=>{
        if(data.code === 200){
            ElMessage.success(data)
        }else{
            ElMessage.error(data.message)
        }
    })
}

function ModifyEmail(email,newEmail,code,success){
    postE('/account/modifyemail',{
        email:email,
        newEmail:newEmail,
        code:code
    },(data)=>{
        if(data.code === 200){
            ElMessage.success(data)
            success(data)
        }else{
            ElMessage.error(data.message)
        }
    })
}

function savePrivacy(Type, status){

    postE('/account/updatePrivacy',{
        types : Type,
        status: status
    },(data)=>{
        if(data.code === 200){
            ElMessage.success(data)
        }else{
            ElMessage.error(data.message)
        }
    })
}

function findByName(Username,success){
    get(`/account/getIdByUsername?name=${Username}`,(data)=>{
        if(data.code === 200){
            success(data.data)
        }else{
            ElMessage.error(data.message)
        }
    })
}

export {logout,login,Sendcode,confirmReset,enRoll,forgetPsw,SaveDetail,ModifyEmail,resetPsw,savePrivacy,findByName}