export default{
    name:"CardComponent",
props:{
    item:{
        type:Object,
        required:true,

    }
},

    methods : {
        addtocart(item)
        {
            console.log(item)
            this.$store.dispatch('SET_CART_ITEM',item)

        },
        addrequest(item)
        {
            console.log(item)
            this.$store.dispatch('SET_REQUEST_ITEM',item)
            
        }
    }
}