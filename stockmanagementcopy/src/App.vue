<template>
<div>
  <!-- <HeaderComponent />        -->
  <!-- <WholesellerRequest /> -->
  <!-- <LoginComponent /> -->
  <router-view></router-view>
  <!-- <OrderComponent />
  <MainComponent /> -->
</div>
</template>

<script>
// import WholesellerRequest from './components/WholesellerRequest.vue'
// import OrderComponent from './components/OrderComponent.vue'
// import HeaderComponent from './components/HeaderComponent.vue'
// import MainComponent from './components/MainComponent.vue'
export default{
  components:{
    // WholesellerRequest,
    // OrderComponent,
    // HeaderComponent,
    // MainComponent,
  }
}
</script>

<style>
#app {
  

  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  background-color: #f6f2eb;
}
body{
  font-family: "Nunito", sans-serif;
}
</style>
