<template>
    <!-- <div>
    <div class="request">
    <div class="requestitems">
       
        <div class="left">
            <p>Paracetomol 50</p></div>
        <div class="right">
            <button style="background-color:green">✓</button>
              <button style="background-color:red">x</button>
        </div>
    </div>

     <div class="requestitems">
       
        <div class="left">
            <p>Paracetomol 50</p></div>
        <div class="right">
            <button style="background-color:green">✓</button>
              <button style="background-color:red">x</button>
        </div>
    </div>

     <div class="requestitems">
       
        <div class="left">
            <p>Paracetomol 50</p></div>
        <div class="right">
            <button style="background-color:green">✓</button>
              <button style="background-color:red">x</button>
        </div>
    </div>

     <div class="requestitems">
       
        <div class="left">
            <p>      Paracetomol 50</p></div>
        <div class="right">
            <button style="background-color:green">✓</button>
              <button style="background-color:red">x</button>
        </div>
    </div>
    </div>
    </div> -->
    <div>
  
    <div class="cont1">
    <CardComp v-for="(item,index) in reqList" :key="index" :item="item"/>
    <!-- <CardComp v-for="(item,index) in reqList" :key="index" :item="item"/> -->
    <!-- <CardComp  v-for="(item,index) in reqList" :key="index" :item="item"/> -->

        <!-- <CardComponent />
    <CardComponent />
    <CardComponent /> -->
    
    </div>
    <!-- <CardComponent /> -->
</div>
</template>

<script src="../scripts/wholesellerrequest.js">

</script>

<style scoped>
.request{
width:60%;
height:max-content;
display: flex;
margin: auto;
flex-direction: column;
background-color: rgb(243, 243, 243);
padding:1vh 1vh 1vh 1vh;
  border-radius:12px;
}
.requestitems{
    max-width:100%;
    /* margin-left:5%; */
    display:flex;
    margin:auto;
    background-color: white;
    justify-content: center;
    flex-direction: row;
    border-radius:7px;
    border:1px solid #ffd39c;
    /* box-shadow:2px 3px 2px 1px rgb(185, 185, 185); */
     margin:2vh 1vh 2vh 1vh;
     color:rgb(94, 93, 93)
}
.left{
    flex:70%;
    margin-left: 10px;
}
.right{
    width:max-content;
    display: flex;
    flex-direction: row;
    
}
button{
    padding:0.5vh 1vh 1vh 1vh;
    margin:1vh 1vh 1vh 1vh;
    height:50%;
    width:30px;
    border-radius:100%;
    border:none;
    background-color: #ff9412;
    align-content: center;
    justify-content: center;
    margin-right:5px;
    color:white;
}
</style>
