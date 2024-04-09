import {defineStore} from "pinia";

export const userprivacystore = defineStore("general",{
    state: ()=>{
        return {
            userprivacy: {
                accountId:"",
                phone:"",
                qq:"",
                accountEmail:"",
                accountSex:"",
            }
        }
    }
})