import axios from "axios";
import {ElMessage} from "element-plus";
import {accessHeader, imgHeader} from "@/net/header";



axios.defaults.withCredentials = true;//允许跨域携带cookie信息



// post请求 内部调用
function internalPost(url, data, headers, success, failure = defaultFailure , error = defaultError){
    axios.post(url, data, { withCredentials: true,headers: headers }).then(({data}) => {
        if(data.code === 200)
            /*成功处理器 将data.data返回 */
            success(data)
        else
            /*成功处理器 将时报处理器返回 */
            failure(data.message, data.code, url)
    }).catch(err => error(err))
}

//get请求 内部调用
function internalGet(url, headers, success, failure = defaultFailure, error = defaultError){
    axios.get(url, { withCredentials: true,headers: headers }).then(({data}) => {
        if(data.code === 200)
            success(data)
        else
            failure(data.message, data.code, url)
    }).catch(err => error(err))
}


// 默认的错误处理器
const defaultError = (error) => {
    console.info(error)
    ElMessage.error('发生了一些错误，请联系管理员')
}

// 默认的失败处理器
const defaultFailure = (message, status, url) => {
    console.warn(`请求地址: ${url}, 状态码: ${status}, 错误信息: ${message}`)
    ElMessage.warning(message)
}

// 供外部调用的post请求
function post(url, data, success) {
    internalPost(url, data, accessHeader() , success)
}

function get(url, success, failure = defaultFailure) {
    internalGet(url, accessHeader(), success, failure)
}

function postE(url, data, success, failure = defaultFailure) {
    internalPost(url, data, imgHeader() , success, failure)
}

export {post,postE,get}