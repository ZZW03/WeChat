import { defineStore } from 'pinia';
import axios from "axios";

export const userdetailstore = defineStore("general",{
    state: ()=>{
        return {
            userdetail: {
                accountId: "",
                accountNickName: "",
                accountAddress:"",
                accountSex:"",
                accountAge:"",
                accountAvatar:"",
                accountQq:"",
                accountPhone:""
            }
        }
    },getters: {
        avatarUrl() {
            if(this.userdetail.accountAvatar)
                return `${axios.defaults.baseURL}/images/${this.userdetail.accountAvatar}`
            else
                return 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
        }
    }
})