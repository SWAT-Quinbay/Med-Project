<template>
  <div>
    <NavBar :NavBarData="routes" />
    <div class="mt-4 container">
      <router-view />
    </div>
  </div>
</template>
<script>
import NavBar from "@/components/NavBar.vue";
import { dealerNavBarRoutes } from "@/contants/navigationRoutes.js";
export default {
  name: "DealerHomePage",
  data() {
    return {
      routes: dealerNavBarRoutes,
    };
  },
  components: {
    NavBar,
  },
};
</script>
<style></style>
