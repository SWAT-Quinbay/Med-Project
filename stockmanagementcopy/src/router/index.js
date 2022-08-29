import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

import LoginComponent from '../components/LoginComponent.vue'
import OrderComponent from '../components/OrderComponent.vue'
import MainComponent from '../components/MainComponent.vue'
import RequestComponent from '../components/RequestComponent.vue'
import RetailerComponent from '../components/RetailerComponent.vue'
import WholesellerComponent from '../components/WholesellerComponent';
import WholesellerRequest from '../components/WholesellerRequest';

const routes = [
  { path: '/', component: LoginComponent },
  { path: '/retailer', component: RetailerComponent,children: [
    { path: '/retailer/order', component: OrderComponent },
    { path: '/retailer/product', component: MainComponent },
    { path: '/retailer/request', component: RequestComponent },
    ], 
    },
    { path: '/wholeseller', component: WholesellerComponent,children: [
      { path: '/wholeseller/product', component: MainComponent },
      { path: '/wholeseller/request', component: WholesellerRequest },
      ], },
  

]


const router = new VueRouter({
 
  mode : "history",
  routes, // short for `routes: routes`
})

export default router;



