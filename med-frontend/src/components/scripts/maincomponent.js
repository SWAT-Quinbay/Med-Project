import { mapGetters } from 'vuex';
import CardComponent from '../components/CardComponent.vue'
// import RequestComponent from '../components/RequestComponent.vue'
export default{
    name:"MainComponent",
    components :{
        CardComponent,
        // RequestComponent
    },
    computed:{
        ...mapGetters({
            productList : 'getProductList',
            // requestList:'getRequestItem',
          }),
    },
    created(){
        console.log("mounting main component")
        this.$store.dispatch('GET_PRODUCT_LIST')
        // this.$store.dispatch('GET_PRODUCT_LIST')
    },
}