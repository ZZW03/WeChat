import {ElMessage} from "element-plus";

const authItemName = "authorize"

// 存储Token 到localStorage 或者 sessionStorage中
function storeAccessToken(remember, token, expire){
    const authObj = {
        token: token,
        expire: expire
    }
    const str = JSON.stringify(authObj)
    if(remember)
        localStorage.setItem(authItemName, str)
    else
        sessionStorage.setItem(authItemName, str)
}

//从 到localStorage或者sessionStorage中 中拿token
function GetAccessToken() {
    const str = localStorage.getItem(authItemName) || sessionStorage.getItem(authItemName);
    if(!str) return null
    const authObj = JSON.parse(str)
    if(new Date(authObj.expire) <= new Date()) {
        deleteAccessToken()
        ElMessage.warning("登录状态已过期，请重新登录！")
        return null
    }
    return authObj.token
}

// 删除 token
function deleteAccessToken() {
    localStorage.removeItem(authItemName)
    sessionStorage.removeItem(authItemName)
}

/*防止请求头*/
const accessHeader = () => {
    return {
        'Authorization': `Bearer ${GetAccessToken()}` , 'Content-Type': 'application/x-www-form-urlencoded' ,
    }
}

const imgHeader = () => {
    return {
        'Authorization': `Bearer ${GetAccessToken()}`

    }
}

export {storeAccessToken,GetAccessToken,deleteAccessToken,accessHeader,imgHeader}