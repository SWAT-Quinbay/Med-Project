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



</style>
