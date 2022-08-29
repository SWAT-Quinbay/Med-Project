export default{
    state:{cartitems:[],arrid:[]},
    getters:{
        getCartItem(state){
            return state.cartitems;
        }
    },
    mutations:{
        setCartItem(state,value)
        {
            if(value.length==0)
            {
                console.log("empty cart")
                state.cartitems.push([])
            }
            else{
            let temp={...value,stockOrdered:1,price:value.retailPrice}
            state.cartitems.push(temp);
            }
        },
        setcartquantity(state,id)
        {
            if(state.cartitems[id].stockOrdered==state.cartitems[id].stockInHand)
            {
                alert("exceeding stock limit!!")
            }
            else{
            state.cartitems[id].stockOrdered+=1;
            state.cartitems[id].price=state.cartitems[id].stockOrdered*state.cartitems[id].retailPrice;}
        }
    },
    actions:{
        SET_CART_ITEM(state,item)
        {
                this.commit('setCartItem',item);
        },
        SET_ITEM_COUNT(state,id){
            console.log(id)
            this.commit('setcartquantity',id)
        },
        EMPTY_CART(){
            this.commit('setCartItem',[])
        }
    }
}