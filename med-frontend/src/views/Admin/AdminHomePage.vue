<template>
    <div>
        <NavBar :navLinks="routes"/>
        <div class="mt-4 container">
            <router-view/>
        </div>
    </div>
</template>
<script>
import NavBar from "@/components/NavBar.vue"
import { adminNavBarRoutes } from "@/utils/navigationRoutes.js"

export default {
    name : "AdminMainPage",
    data(){
        return{
            routes : adminNavBarRoutes
        }
    },
    components : {
        NavBar 
    } 
}
</script>
<style>
    
</style>