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
import {createPinia} from "pinia";
import 'vue3-emoji/dist/style.css'
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { fab } from '@fortawesome/free-brands-svg-icons'
import { FontAwesomeIcon, FontAwesomeLayers, FontAwesomeLayersText } from '@fortawesome/vue-fontawesome'
import V3Emoji from 'vue3-emoji'
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";


library.add(fas, far, fab)


axios.defaults.baseURL = 'http://localhost:8080'
const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    // 向应用实例中全局注册图标组件
    app.component(key, component)
}

app.use(Toast, {
    transition: "Vue-Toastification__bounce",
    maxToasts:1,
    newestOnTop: true
});
app.use(V3Emoji)
app.component('font-awesome-icon', FontAwesomeIcon)
app.component('font-awesome-layers', FontAwesomeLayers)
app.component('font-awesome-layers-text', FontAwesomeLayersText)
app.use(ElementPlus)
app.use(router)
app.use(VueAxios,axios)
app.use(createPinia())
app.mount('#app')
