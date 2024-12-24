import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/Login";
import Products from '@/views/Products';
import Register from "@/views/Register";
import Shopping from "@/views/Shopping";
import Checkout from "@/views/Checkout";
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    hidden:true
  },
  {
    path: '/product',
    name: 'Products',
    component: Products,
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
  },
  {
    path: '/shopping',
    name: 'Shopping',
    component: Shopping,
  },
  {
    path: '/checkout',
    name: 'Checkout',
    component: Checkout
  }
]

const router = new VueRouter({
  routes
})

export default router
