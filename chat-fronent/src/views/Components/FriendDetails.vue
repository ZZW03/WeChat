<script setup>
import {get} from "@/net/net";
import {reactive, ref} from "vue";
import {userdetailstore} from "@/store/userdetailstore";
import axios from "axios";

const prop = defineProps({
  Id : Number
})

let UserDetail
let loading = ref(false)
get(`/account/getOtherDetail?id=${prop.Id}`,(data)=>{
  UserDetail = reactive({
    NickName: "" + data.data.accountNickName,
    Sex: "" + data.data.accountSex,
    Avatar: "" + `${axios.defaults.baseURL}/images/${data.data.accountAvatar}`,
    Age: "" + data.data.accountAge,
    Address: "" + data.data.accountAddress,
    QQ:""+data.data.accountQq,
    Phone:""+data.data.accountPhone,
  });

  loading.value = true;
})
</script>

<template>
  <div v-if="loading">{{UserDetail.NickName}}</div>
</template>

<style scoped>

</style>