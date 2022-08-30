<template>
   <header class="headr" id="head">
        <!-- <div> -->
            <!-- <h3>
               {{mytitle}}</h3> -->
               <!-- <img src="../assets/bliblilogo.png"> -->
               <!-- <img style="height:50px;width:100px;" src="https://www.static-src.com/frontend/static/img/logo-blibli-white.f8255fc.svg"> -->
        <!-- </div> -->
        <div class="nav">
            <div class="navitem">
            <ul>
                <!-- <li><img style="height:50px;width:100px;" src="https://www.static-src.com/frontend/static/img/logo-blibli-white.f8255fc.svg"></li> -->
                <!-- <li><router-link to="/wholeseller"><li style="color:white">Home</li></router-link></li> -->
                <!-- {{statevalue}} -->
                <!-- {{change}} -->
                <li><router-link to="/wholeseller/product"><li style="color:white">Products</li></router-link></li>
                <li><router-link to="/wholeseller/request"><li style="color:white">Requests</li></router-link></li>
                <li><button @click="logout()" @click.prevent>Logout</button></li>
                <!-- <li><router-link to="/wholeseller/order"><li style="color:white"><ion-icon name="cart">cart</ion-icon></li></router-link></li> -->
                  <!-- <li><input type="text" v-model="user" placeholder="search here"> -->
                <!-- <li><input type="text"  v-model="searchitem" placeholder="search here">
                    <button  @click="filter()" @click.prevent><ion-icon name="search"></ion-icon></button></li> -->
                   <!-- <li> <button class="btn"  value="Login">Login</button></li> -->
                  <!-- <li> <button class="btn"  value="Register">Register</button></li> -->
            </ul>
            </div>
            
            
            
        </div>

    </header>
</template>

<script src='../scripts/headercomp.js'>


</script>



<style scoped>
.headr h3{
    padding-top: 1vw;
}
.headr{
    top:0%;
    position: sticky;
    width:100%;
    height: max-content;
    z-index: 2;
    /* background-color:#A80038; */
    background-color:#ff9412;
    color:white;
    text-align: center;
    
    /* border-radius: 1vw; */
}
.headr input{
    border-radius: 5px;
    border:none;
    height:17px;
    width:150px;
}
header .nav{
    width:100%;
    height:max-content;
    /* background-color:#26c1a1; */
}
.navitem ul {
    margin: 0px 0 0 0; /*16px earlier*/
}
.navitem li{
    padding-top: 10px;
    padding-bottom:10px ;
}
.navitem li
{
    display: inline-block;
    margin-left: 5%;
}
.navitem li:hover{
    margin-top:-2%;
    font-weight:bolder;
    transition: 0.3s all;
    border:none;
    
}
/* .btn {
   background-color: #0095da; 
  background-color:white;
  color:#0095da;
  border: none;
  margin-bottom:4%;
  width:max-content;
  margin-left: 5px;
  position: relative;
   padding: 8px 10px 8px 10px; 
  border-radius: 7px;
}  */
button{
    padding:1vh 1vh 1vh 1vh;
    margin:1vh 1vh 1vh 1vh;
    border-radius:5px;
    border:1px solid rgb(255, 255, 255);
    background-color: #ff9412;
    align-content: center;
    justify-content: center;
      display:flex;
    margin:auto;
    color:white;
}
</style>

