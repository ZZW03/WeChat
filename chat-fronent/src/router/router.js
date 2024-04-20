import {createRouter,createWebHistory} from "vue-router";
import WelcomeView from '../views/WelcomeView.vue'
import Login from '../views/welcome/Login.vue'
import Forget from '../views/welcome/Forget.vue'
import Index from '../views/index/Index.vue'
import {ElMessage} from "element-plus";
import Enroll from "@/views/welcome/Enroll.vue";
import UserSetting from "@/views/index/UserSetting.vue";
import ChangePsw from "@/views/index/ChangePsw.vue";
import FriendList from "@/views/friends/FriendList.vue";
import messageSession from "@/views/message/MessageSession.vue";
import MessageContent from "@/views/message/MessageContent.vue";
import FriendInformation from "@/views/friends/FriendInformation.vue";
import GroupInformation from "@/views/groups/GroupInformation.vue";



const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'welcome',
            component: WelcomeView,
            children:[
                {
                    path: 'login',
                    name: 'login',
                    component: Login,
                },

                {
                    path: 'forget',
                    name: 'forget',
                    component: Forget,
                },
                {
                    path: 'enroll',
                    name: 'enroll',
                    component: Enroll,
                }
            ],
            redirect:'/login'
        },
        {
            path: '/index',
            name: 'index',
            component: Index,
            children:[
                {
                    path: 'usersetting',
                    name: 'usersetting',
                    component: UserSetting,
                },
                {
                    path:'changepsw',
                    name:'changepsw',
                    component: ChangePsw
                },
                {
                    path:'friendList',
                    name:'friendList',
                    component: FriendList,
                    children:[
                        {
                            path:'friendInformation',
                            name:'friendInformation',
                            component: FriendInformation,
                        },
                        {
                            path:'groupInformation',
                            name:'groupInformation',
                            component: GroupInformation,
                        }
                    ]
                },
                {
                    path:'messageSession',
                    name:'messageSession',
                    component: messageSession,
                    children:[
                        {
                            path:'messageContent',
                            name:'messageContent',
                            component: MessageContent,
                        }
                    ]
                }

            ]
        }

    ]
})

/*
* token过期 不允许登录
* 登录之后 不允许回到登录
* 未登录 不允许进入别的页面
* */
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem("authorize") || sessionStorage.getItem("authorize");
    if (to.matched.length === 0) {
        // 如果to路径没有匹配到任何路由，进行重定向，例如到404页面
        next('/');
        ElMessage.warning("未找到此页面你")
    } else {
        if (token) {
            const decodedToken = JSON.parse(atob(token.split('.')[1]));
            const expirationTime = decodedToken.exp;
            //如果过去了 直接返回到首页
            if (expirationTime < Date.now() / 1000) {
                console.log('令牌过期');
                ElMessage.warning("请重新登录")
                localStorage.removeItem("authorize")
                sessionStorage.removeItem("authorize")
                next('/')
            } else {
                if (to.name.startsWith('login') && token) {
                    next('/index')
                } else {
                    next()
                }
            }
        } else if (to.fullPath.startsWith('/index')) {
            console.log(1)
            ElMessage.warning("请先登录")
            next('/')
        } else {
            next()
        }
    }

})

export default router