<template>
    <div class="login">
        <h4 style="text-align:center">Login</h4>
        <form>
            <input type="text" v-model="user.userId" placeholder="username" /><br>
            <input type="password" v-model="user.password" placeholder="Password" /><br>
            <select name="role" v-model="user.role" >
                <option value="retailseller">Shopkeeper</option>
                <option value="wholesaleseller">Wholeseller</option>
            </select>
            <button @click="validate()" @click.prevent>Login</button>
            <!-- <p style="text-align:center">Don't have an account?</p> -->
        </form>
    </div>
</template>



<script src="../scripts/logincomponent.js">

</script>

<style scoped>
body{
    background-color: #f6f2eb;
}
.login{
    width:30%;
    height:max-content;
    padding:2vw;
    background-color:#f6f2eb;
    top:50%;
    left:50%;
    transform:translate(-50%,-50%);
    position: absolute;
    border-radius: 25px;
}
input{
    padding:2vh 1vh 2vh 1vh;
    margin:0.5vh 1vh 0.5vh 1vh;
    border-radius:7px;
    border:1px solid rgb(139, 139, 139);
}
form{
    width:80%;
    display:flex;
    flex-flow:column wrap;
    margin:auto;
}
select{
    padding:1vh 0.5vh 1vh 0.5vh;
    margin:0.5vh 1vh 0.5vh 1vh;
    border-radius:7px;
    border:1px solid rgb(139, 139, 139);
}
button{
    padding:1vh 1vh 1vh 1vh;
    margin:1vh 1vh 1vh 1vh;
    border-radius:5px;
    border:none;
    background-color: #ff9412;
}
@media (max-width: 1000px) {
    .login{width:40%;}
    input{
    padding:1vh 0.5vh 1vh 0.5vh;
    margin:0.5vh 1vh 0.5vh 1vh;
    border-radius:7px;
    border:1px solid rgb(139, 139, 139);
}
}
@media (max-width: 750px) {
    .login{width:60%;}
    input{
    padding:1vh 0.5vh 1vh 0.5vh;
    margin:0.5vh 1vh 0.5vh 1vh;
    border-radius:7px;
    border:1px solid rgb(139, 139, 139);
}
}

</style>
