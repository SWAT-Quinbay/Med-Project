export default{
    name:"HeaderComp",
    methods:{
        logout(){
            //remove item from local storage
            localStorage.clear();
            this.$router.push({path:'/'})
        }
    }
}