import { getAllProductFromAdmin} from "@/service/dealer.service";

export default {
    state : {
        productList : [],
        inventoryList : [],
        requestHistory : []
    },
    getters : 
    {
        getProductList(state){
            return state.productList;
        },
        getInventory(state){
            return state.inventoryList;
        },
        getRequestHistory(state){
            return state.requestHistory;
        }
    },
    mutations : {
        setProductList(state,productList){
            state.productList = productList
        },
        setInventory(state,inventoryList){
            state.inventoryList = inventoryList
        },
        setRequestHistory(state,requestHistory){
            state.requestHistory = requestHistory
        }
    },
    actions : {
        
        GET_ALL_PRODUCT_FROM_DEALER({commit},value=""){
            getAllProductFromAdmin({
                searchText : value,
                successCallback : ({data}) => {
                    commit("setProductList",data.content)
                },
                errorCallback : (err) => {
                    console.log(err)
                }
            })
        },
        
        
    }
}