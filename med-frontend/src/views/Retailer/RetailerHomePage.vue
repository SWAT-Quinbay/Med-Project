<template>
    <div>
         <NavBar :NavBarData="routes"/>
        <div class="mt-4 container">
            <router-view/>
        </div>
    </div>
</template>
<script>
import NavBar from "@/components/NavBar.vue"
import { retailerNavBarRoutes } from "@/contants/navigationRoutes.js"

export default {
    name : "RetailerHomePage",
    data(){
        return{
            routes : retailerNavBarRoutes
        }
    },
    components : {
        NavBar 
    } 
}
</script>
<style>
    
</style> 