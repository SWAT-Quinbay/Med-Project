<template>
<div>
  
    <div class="cont1">
    <CardComponent v-for="(item,index) in productList" :key="index" :item="item"/>
    <!-- <CardComponent v-for="(item,index) in productList" :key="index" :item="item"/> -->
    <!-- <CardComponent v-for="(item,index) in productList" :key="index" :item="item"/> -->

        <!-- <CardComponent />
    <CardComponent />
    <CardComponent /> -->
    
    </div>
    <!-- <CardComponent /> -->
</div>
</template>

<script src="../scripts/maincomponent.js">

</script>

<style scoped>

.cont1,
.cont2,
.cont3,
.cont4 {
  display: flex;
  flex-flow: row wrap;
  /*flex-direction: row; */
  /* flex-flow: row wrap; */
}
</style>
