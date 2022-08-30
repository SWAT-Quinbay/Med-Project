<template>
<!-- <div>
  <p>{{item}}hello</p>
  <button @click="addtocart(item)" @click.prevent>add</button>
</div>  -->

 <!-- <div class="card" >
         
        <div class="details" >
          <p>{{item.Brand}}</p>
          <p>{{item.productName}}</p>
   <input type="number" min="1" max="10" id="quantity" v-model="item.count" style="margin-bottom:8px"/> 
           <button @click="addrequest(item)">add to request</button>
          <button @click="addtocart(item)">Add to sales Cart</button> 
          
       
        </div>
      </div> -->

<div class="request">
    <div class="requestitems">
       
        <div class="left">
            <p>{{item.userId}} > {{item.productName}} > {{item.stock}}</p></div>
        <div class="right">
            <button style="background-color:green" @click="accepted(item)">✓</button>
              <button style="background-color:red" @click="denied(item)">x</button>
        </div>
    </div>
</div>
</template>


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

.rightsection {
  flex: 75%;
  height: max-content;
  background-color: rgb(222, 222, 222);
  /* padding: */
}

.cont1,
.cont2,
.cont3,
.cont4 {
  display: flex;
  flex-flow: row wrap;
  /*flex-direction: row; */
  /* flex-flow: row wrap; */
}
.card {
  box-shadow: 0px 0px 10px 2px rgb(157, 161, 162);
  height: max-content;
  flex: 22%;
  max-width: 22%;
  border-radius: 1vw;
  background-color: white;
  /* border:0px solid black; */
  margin: 3% 1% 1% 1%;
}
.card:hover {box-shadow: 0px 0px 20px 2px rgb(157, 161, 162),
    0px 0px 15px 2px rgb(157, 161, 162),
    0px 0px 10px 2px rgb(157, 161, 162),
    0px 0px 6px 2px rgb(157, 161, 162);
  /* margin-left: -0.15%;
  margin-top: -0.15%; */
  transition: 0.5s all;
}
.card img {
  /* width:100%; change this*/
  /* height:7vw; */
  display: flex;
  margin: auto;
  padding-top: 1vw;
  max-width: 100%;
  max-height: 50%;
  border-top-left-radius: 1vw;
  border-top-right-radius: 1vw;
}
.card .details {
  text-align: center;
}
.card button {
  width: 30%;
  background-color: #faac4c;
  color: white;
  border: none;
  margin: auto;
  position: relative;
  padding: 8px 10px 8px 10px;
  margin-left:3px;
  margin-right:3px;
  margin-bottom:1vh;
  border-radius: 7px;
  /* border-bottom-left-radius: 7px;
  border-bottom-right-radius: 7px; */
}
.noproducts{
  display: flex;
  margin: auto;
  height:100%;
  padding-top:5%;
  padding-bottom: 5%;
}
.noproducts img{
  object-fit: cover;
}
@media (max-width: 1200px) {
  .card {
    flex: 27%;
    max-width: 30%;
    /* height:340px; */
  }
}
@media (max-width: 1000px) {
  /* .body1 {
      flex:100%;
    }    no need for right section and left section bcoz form is fit content*/
  .card {
    flex: 46%;
    max-width: 50%;
    /* height:322px; */
      margin:4% 1% 1% 1%;
  }
  .leftsection {
    top: 10%;
  }
}
@media (max-width: 800px) {
  .card img {
    max-height: 50%;
  }
  .card{
    /* height:315px; */
    margin:4% 1% 1% 1%;
  }
}
@media (max-width: 600px) {
 
  .card{
    /* height:315px; */
    margin:6% 1% 1% 1%;
  }
}
@media (max-width: 400px) {
  .card {
    /* height:310px; */
    margin:8% 1% 1% 1%;
    flex: 98%;
    max-width: 100%;
    
  }
  .leftsection {
    top: 20%;
  }
}
/* .product-display {
  display: flex;
  flex-flow: row wrap;
  justify-content: space-evenly;
}
.products-details:hover{
    box-shadow: 2px 2px 2px 2px #0a0a09;
}
.product-details {
  background-color: #fdefde;
  width: 250px;
  margin: 10px;
  height: 100px;
  display: flex;
  justify-content: space-between;
  border-radius:10px;
  /* align-content: center; */
/* }
.button {
  width: 50px;
  height:20px;
  display: block;
  margin: 10px;
}
.product-details-right {
  /* display: flex;
  justify-content: center;
  align-content: center; */
  /* position:relative;
  top:50px; 
}  */
</style>
<script src="../scripts/cardcomp.js">
</script>