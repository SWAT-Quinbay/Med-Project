import { mapGetters } from 'vuex';
import {sendCartItems} from '../service/order'
// import { requestItems } from "@/service/request";
export default{
    name:"OrderComponent",
    computed:{
        ...mapGetters({
            cartitems:'getCartItem'
        })
    },
    methods:{
        setquantity(index){
            console.log(index)
            this.$store.dispatch('SET_ITEM_COUNT',index)
        },
        checkout(){
           
            sendCartItems({
                success:(response)=>{
                    console.log(response)
                    //setitem
                    this.$store.dispatch('GET_PRODUCT_LIST')
                    this.$router.push({path:'/retailer/product'})
                    window.location.reload();
                   
                },
                error:(err)=>{
                    console.log(err)
                },
                list:this.cartitems
            })
        
        }
    },
}