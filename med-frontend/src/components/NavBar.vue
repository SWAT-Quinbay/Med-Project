<template>
    <nav class="navbar navbar-expand-lg" id="NavBar">
  <div class="container-fluid">
     <a class="navbar-brand" href="#">
      <img src="@/assets/logo.png" alt="" width="150">
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
        <li class="nav-item me-4" v-for="(data,index) in navLinks" :key="index">
          <router-link :to="{ name : data.name }" class="nav--link">{{ data.label }}</router-link>
        </li>
      </ul>
      <div class="d-flex">
         <ButtonComponent
                label="Logout"
                buttonStyle="btn--primary"
                @onClick="logout()"
                type="button"
              />
      </div>
    </div>
  </div>
</nav>
</template>
<script>
import ButtonComponent from "@/components/ButtonComponent.vue";
import { deleteToken } from "@/utils/storage"

export default {
    name : "NavBar",
    props : {
        navLinks : {
            type : Array,
            default : () => []
        }
    },
    components : {
      ButtonComponent
    },
    methods : {
      logout(){
        deleteToken()
        this.$router.replace("/")
      }
    }
}
</script>
<style scoped>
    #NavBar{
      box-shadow: 0 5px 20px rgb(0 41 112 / 10%);
      background-color: #FFFFFF;
    }
    .nav--link{
      color: black;
      text-decoration: none;
    }
</style>