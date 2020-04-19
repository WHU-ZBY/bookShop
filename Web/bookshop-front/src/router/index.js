import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../components/Home.vue'
import welcome from '../components/welcome.vue'
import shopCart from'../components/shopCart.vue'
import myOrders from '../components/myOrders'
import register from '@/components/register'
Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/register',
            component: register,
        },
        {
            path: '/',
            component: Login,
        },{
            path: '/Home',
            name: 'Home',
            component: Home,
            redirect: { path: '/1' },
            children: [
                {
                    path: '/1',
                    component: welcome
                },
                {
                    path: '/5',
                    component: shopCart
                },  {
                    path: '/7',
                    component: myOrders
                },
              
            ]
        }
    ]
})
