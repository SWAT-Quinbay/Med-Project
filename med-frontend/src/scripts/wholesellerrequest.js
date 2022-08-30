// import { mapGetters } from 'vuex';
// export default{
//     name:"WholesellerRequest",
//     created(){
//         this.$store.dispatch('GET_REQ');
//     },
//     computed:{
//         ...mapGetters({
//             reqList : 'getReqList',
//             // requestList:'getRequestItem',
//           }),
//     },
// }

import CardComp from "../components/CardComp.vue"
import { mapGetters } from 'vuex';
export default{
    name:"WholesellerRequest",
    components :{
        CardComp,
        // RequestComponent
    },
    computed:{
        ...mapGetters({
            reqList : 'getreqList',
            // requestList:'getRequestItem',
          }),
    },
    created(){
        console.log("mounting main component")
        this.$store.dispatch('GET_REQ')
        // this.$store.dispatch('GET_PRODUCT_LIST')
    },
}