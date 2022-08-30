<template>
     <!-- <div class="login">
        <h4 style="text-align:center">Request Medicine</h4>
        <form>
            <input type="text"  placeholder="username" /><br>
            <input type="password"  placeholder="Password" /><br> -->
            <!-- <select name="medicinename" v-model="medicinename">
                <option value="retailseller" >paracetomol</option>
                <option value="wholesaleseller">Dolo</option>
            </select>
            <input type="number" v-model="count">
          <button @click="requestmed()" @click.prevent>Send Request</button>   -->
            <!-- <p style="text-align:center">Don't have an account?</p> -->
    <!--    </form>
    </div> --> 



    <!-- <div>
    <div v-if="requestitems.length > 0" class="cart">

    <div class="cartitem"  v-for="(item,index) in requestitems" :key="index">
        <div class="left">
            <p>{{item.productName}}</p>
            <p>{{item.price}}</p>
        </div>
        <div class="right">
            <div class="quantity">
                <p >{{item.count}}</p>
                <button @click="setrequestquantity(index)" @click.prevent><span style="color:black;font-size:2vh">+</span></button>
                  
                </div>
        </div>

    </div>
 <button @click="requestmed()">Send Request</button>
            
    </div>
     <div v-else>
        <img style="width:100%;max-height:100%;object-fit:contain;" src="../assets/notfound.webp" />
    </div>

   
</div> -->


<div>
<div v-if="requestitems.length > 0">
    <div class="container">
    <ul class="responsive-table">
    <li class="table-header">
      <div class="col col-1">S.no</div>
      <div class="col col-2">Item Name</div>
      <div class="col col-3">Quantity</div>
    </li>
    </ul>
    </div>
<div class="container" v-for="(item,index) in requestitems" :key="index">
  
  <ul class="responsive-table">
    <li >
      <div class="col col-1">{{index+1}}</div>
      <div class="col col-2">{{item.productName}}</div>
      
      <div class="col col-3">{{item.count}} <button @click="setrequestquantity(index)" @click.prevent>+</button></div>
    </li>
  </ul>
</div>
 <button class="btn" @click="requestmed()">Send Request</button>
</div>

<div v-else>
        <img style="width:100%;max-height:100%;object-fit:contain;" src="../assets/notfound.webp" />
    </div>
</div>

</template>

<script src="../scripts/requestcomponent.js">

</script>

<style scoped>

body {
  font-family: 'lato', sans-serif;
}
.container {
  max-width: 1000px;
  margin-left: auto;
  margin-right: auto;
  padding-left: 10px;
  padding-right: 10px;
}

h2 {
  font-size: 26px;
  margin: 20px 0;
  text-align: center;
  
}

.responsive-table li{
  
    border-radius: 3px;
    padding: 25px 30px;
    display: flex;
    justify-content: space-between;
    margin-bottom: 25px;
  }
  .table-header {
    background-color: #fb900e;
    font-size: 14px;
    text-transform: uppercase;
    letter-spacing: 0.03em;
  }
  .table-row {
    background-color: #ffffff;
    box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.1);
  }
  .col-1 {
    flex-basis: 10%;
  }
  .col-2 {
    flex-basis: 40%;
  }
  .col-3 {
    flex-basis: 25%;
  }
  .col-4 {
    flex-basis: 25%;
  }
  
  @media all and (max-width: 767px) {
    .table-header {
      display: none;
    }
    .table-row{
      
    }
    li {
      display: block;
    }
    .col {
      
      flex-basis: 100%;
      
    }
  }
.btn{
    padding:1vh 1vh 1vh 1vh;
    margin:1vh 1vh 1vh 1vh;
    border-radius:5px;
    border:none;
    background-color: #ff9412;
    align-content: center;
    justify-content: center;
      display:flex;
    margin:auto;
    color:white;
}
.quantity button
{
    max-height: 10%;
    width:20%;
    border-radius:100%;
    border:none;
    box-shadow: 0px 0px 3px 1px rgb(157, 157, 157);
    background-color: #fdefde;
}

.cart{
    width:70%;
    display:flex;
    margin:auto;
    flex-direction: column;
    height:max-content;
    background-color: rgb(255, 255, 255);
    border-radius:7px;
    
}
.cartitem{
    max-width:100%;
    /* border-radius:17px; */
    background-color: rgb(255, 255, 255);
    height:max-content;
    display:flex;
    padding:2vh 2vh 2vh 2vh;
    margin:2vh 2vh 2vh 2vh;
    border:none;
    border-left: 3px solid #fdc886;
    justify-content: space-between;
}
.left{
    flex:70%;
    max-width: 70%;
    background-color: #fdefde;
    border-right:2px solid #fdc886;
     padding:1vh 2vh 1vh 2vh;
}
.right{
    flex:27%;
    max-width: 27%;
    background-color: #fdefde;
    border-radius:7px;
    padding:1vh 1vh 1vh 1vh;
}
.quantity{
    display:flex;
    flex-direction:row; 
    margin:auto;
    max-width: 100%;
    justify-content: center;
}
</style>
