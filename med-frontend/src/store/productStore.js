import Vuex from 'vuex';
import Vue from 'vue'
import {getProductList} from '../service/getproduct'
Vue.use(Vuex)


export default {
    state : {productList : []},
    getters  : {
            getProductList(state) {
                return state.productList;
            }
    },
    mutations :{
        setProductList(state,newval) {
            state.productList = newval;
            console.log(state.productList)
        }

    },
    actions : {
        GET_PRODUCT_LIST() {
            // console.log(value)
            console.log("inside store")
            getProductList(
                {
                    success: (response) => {
                        console.log(" got response from getproduct api")
                        this.commit('setProductList', response.data);
                        console.log(response.data)
                    },
                    error: () => {
                        this.commit('setProductList',[])
                    },
                }
            )
        }

    }
}