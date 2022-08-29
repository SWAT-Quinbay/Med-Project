export default{
    state:{requestitems:[],},
    getters:{
        getRequestItem(state){
            return state.requestitems;
        }
    },
    mutations:{
        setRequestItem(state,value)
        {
            let seller=localStorage.getItem("user")
            let temp={...value,count:1,price:value.retailPrice,sellerId:seller}
            state.requestitems.push(temp);
        },
        setrequestquantity(state,id)
        {
            state.requestitems[id].count+=1;
            state.requestitems[id].price=state.requestitems[id].count*state.requestitems[id].retailPrice;
        }
    },
    actions:{
        SET_REQUEST_ITEM(state,item)
        {
        //     let flag=0;
        //     state.cartitems.map((p)=>{
        //         if(p.productId===item.productId){
        //             console.log(state.cartitems.length,p.productId,item.productId)
        //             flag=p.productId;
        //         }

        //    })
        //     if(state.cartitems.length===0 && flag==0) {
                this.commit('setRequestItem',item);
            // }
            // else
            // {
            //     // let index=state.cartitems.indexOf(item);
            //     this.$store.dispatch('SET_ITEM_COUNT',flag)
            // }
        },
        SET_RITEM_COUNT(state,id){
            console.log(id)
            this.commit('setrequestquantity',id)
        }
    }
}