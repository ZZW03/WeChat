import { defineStore } from 'pinia';
import axios from "axios";

export const userstore = defineStore("general",{
    state: ()=>{
        return {
            user: {
                accountUsername: "",
                accountEmail:"",
                accountRole:"",
                registerTime:""
            }
        }
    }
})