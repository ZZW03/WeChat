import {createApp, onMounted} from 'vue'
import App from './App.vue'
import router from './router/router'
// 引入Element Plus模块
import ElementPlus from 'element-plus'
// 引入CSS样式
import 'element-plus/dist/index.css'
// 引入图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import VueAxios from "vue-axios";
import axios from "axios";
import {ElMessage} from "element-plus";
import {createPinia} from "pinia";


axios.defaults.baseURL = 'http://localhost:8080'
const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    // 向应用实例中全局注册图标组件
    app.component(key, component)
}


app.use(ElementPlus)
app.use(router)
app.use(VueAxios,axios)
app.use(createPinia())
app.mount('#app')
