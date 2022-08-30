import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

import LoginComponent from "../components/LoginComponent.vue";
import OrderComponent from "../components/OrderComponent.vue";
import MainComponent from "../components/MainComponent.vue";
import RequestComponent from "../components/RequestComponent.vue";
import RetailerPage from "../components/RetailerComponent.vue";
import WholesellerPage from "../components/WholesellerComponent";
import WholesellerRequest from "../components/WholesellerRequest";

const routes = [
  { path: "/", component: LoginComponent },
  // {
  //   path : "/admin",
  //   component : AdminPage,
  //   children : [
  //     {
  //       path : "/retailers" , component : AdminRetailerPage,
  //       path : "/wholesellers" , component : AdminWholesellersPage
  //     }
  //   ]
  // },
  {
    path: "/retailer",
    component: RetailerPage,
    children: [
      { path: "/retailer/order", component: OrderComponent },
      { path: "/retailer/product", component: MainComponent },
      { path: "/retailer/request", component: RequestComponent },
    ],
  },
  {
    path: "/wholeseller",
    component: WholesellerPage,
    children: [
      { path: "/wholeseller/product", component: MainComponent },
      { path: "/wholeseller/request", component: WholesellerRequest },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  routes, // short for `routes: routes`
});


// router.beforeEach(async (to, from) => {
//   if (
//     // make sure the user is authenticated
//     !isAuthenticated &&
//     // ❗️ Avoid an infinite redirect
//     to.name !== 'Login'
//   ) {
//     // redirect the user to the login page
//     return { name: 'Login' }
//   }
// })


export default router;
