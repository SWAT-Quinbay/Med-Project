import { requestItems } from "@/service/request";
import { mapGetters } from 'vuex';
export default{
    name:"RequestComponent",
    data(){
        return{
        count:0,
        // request:{
        //     medicinename:'',
        //     count:'',
        //     sellerId:localStorage.getItem("uname")
        // },
        };
     },
     computed:{
        ...mapGetters({
            requestitems:'getRequestItem'
        })
    },
     methods:{
        setrequestquantity(index){
            console.log(index)
            this.$store.dispatch('SET_RITEM_COUNT',index)
        },
        requestmed(){
            requestItems({
            success:(response)=>{
                console.log(response)
                //setitem
            },
            error:(err)=>{
                console.log(err)
            },
            list:this.requestitems})
        }
      
     }
    
     
}