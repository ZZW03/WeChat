<script setup>
import {get} from "@/net/net";
import {reactive, ref} from "vue";
import axios from "axios";

const prop = defineProps({
  Id : Number
})

let UserDetail
let loading = ref(false)
get(`/account/getOtherDetail?id=${prop.Id}`,(data)=>{
  UserDetail = reactive({
    NickName: "" + data.data.accountNickName,
    Avatar: "" + `${axios.defaults.baseURL}/images/${data.data.accountAvatar}`,
    Desc:""+data.data.accountDesc,
  });
  loading.value = true;
})
</script>

<template>
  <div v-if="loading">
    <el-avatar class="User-icon" :src="UserDetail.Avatar"/>
    <span style="display: inline-block; height:50%;width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; position: relative; bottom: 8px;font-size: 15px">{{UserDetail.NickName}}</span>
    <span style="display: inline-block; width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; position: relative; right: 100px; top: 10px; font-size: 11px;color: grey">{{UserDetail.Desc}}</span>
  </div>
</template>

<style scoped>
.User-icon{
  margin-bottom: 5px;
  margin-right: 8px;
  margin-left: -20px;
}
div{
  font-family: "Microsoft JhengHei",serif;
}


.text-span{

}

</style>