<script setup>
import {reactive, ref} from "vue";
import {get} from "@/net/net";
import FriendAddReq from "@/views/Components/FriendAddReq.vue";


let Req = ref ()

get('friend/SelAllReq',(data)=>{
  Req.value = data.data
})



</script>

<template>
  <div >
    <FriendAddReq class="FriendAddReq" :Item="item" v-for="item in Req"/>
  </div>
</template>

<style scoped>
.FriendAddReq{
  margin-top: 1%;
}
</style>