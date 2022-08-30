<template>
<!-- <div>
    <div v-if="cartitems.length > 0" class="cart" >

    <div class="cartitem" v-for="(item,index) in cartitems" :key="index">
        <div class="left">
            <p>{{item.productName}}</p>
          <p>Price {{item.price}}</p>
        </div>
        <div class="right">
            <div class="quantity">
                <p >{{item.stockOrdered}}</p>
                <button @click="setquantity(index)" @click.prevent><span style="color:black;font-size:2vh">+</span></button>
                  
                </div>
        </div>

    </div>

            <button @click="checkout()">Send Sales Report</button>
    </div>
    <div v-else>
        <img style="width:100%;max-height:100%;object-fit:contain;" src="../assets/notfound.webp" />
    </div>
    
</div> -->

<div>
<div v-if="cartitems.length > 0">
    <div class="container">
    <ul class="responsive-table">
    <li class="table-header">
      <div class="col col-1">S.no</div>
      <div class="col col-2">Item Name</div>
      <div class="col col-3">Unit Price</div>
       <div class="col col-3">Price</div>
      <div class="col col-4">Quantity</div>
    </li>
    </ul>
    </div>
<div class="container" v-for="(item,index) in cartitems" :key="index">
  
  <ul class="responsive-table">
    <li >
      <div class="col col-1">{{index+1}}</div>
      <div class="col col-2">{{item.productName}}</div>
      <div class="col col-3">{{item.retailPrice}}</div>
      <div class="col col-3">{{item.price}}</div>
      <div class="col col-4">{{item.stockOrdered}} <button @click="setquantity(index)" @click.prevent>+</button></div>
    </li>
  </ul>
</div>

<button class="btn" @click="checkout()">Send Sales Report</button>
</div>

<div v-else>
        <img style="width:100%;max-height:100%;object-fit:contain;" src="../assets/notfound.webp" />
    </div>
</div>
</template>

<script src='../scripts/ordercomponent.js'>

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
    /* .col {
      display: flex;
      padding: 10px 0;
      &:before {
        color: #6C7A89;
        padding-right: 10px;
        content: attr(data-label);
        flex-basis: 50%;
        text-align: right;
      }
    } */
  }



button{
   top:0%;
   position: relative;
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

/* 
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
    /* background-color: rgb(255, 255, 255);
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
}  */
</style>
