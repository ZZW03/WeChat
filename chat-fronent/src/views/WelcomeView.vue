<script setup>

</script>

<template>
  <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex;">
    <div style="flex: 1;" >
      <el-image style="width: 100%;height: 100%" fit="cover"
                src="https://img1.baidu.com/it/u=4097856652,4033702227&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800">
      </el-image>
      </div>
    <div class="welcome-title">
      <div style="font-size: 30px;font-weight: bold">欢迎来到我们的校园论坛</div>
      <div style="font-size: 15px;margin-top: 10px">在这里你可以在这里了解校园的方方面面,校园活动,考试时间等。</div>
      <div style="font-size: 15px;margin-top: 5px">也可以在这里自由发布帖子,来帮助你解决问题。</div>
      <a href="https://beian.miit.gov.cn/" target="_blank" style="color: white">浙ICP备2023041194号-1</a>
    </div>
    <div class="right-card">

      <router-view v-slot="{ Component }">
        <transition name="el-fade-in-linear" mode="out-in">
          <component :is="Component" style="height: 100%"/>
        </transition>
      </router-view>

    </div>

  </div>



</template>

<style scoped>

.right-card{
    width: 400px;
    z-index: 1;
    background-color: var(--el-bg-color);
  }
.welcome-title {
  position: absolute;
  bottom: 30px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;
}
</style>