import Vuex from 'vuex';
import Vue from 'vue'
import {getreq} from '../service/req'
Vue.use(Vuex)


export default {
    state : {reqList : []},
    getters  : {
            getreqList(state) {
                return state.reqList;
            }
    },
    mutations :{
        setReqList(state,newval) {
            state.reqList = newval;
            console.log(state.reqList)
        }

    },
    actions : {
        GET_REQ() {
            // console.log(value)
            console.log("inside WH store")
            getreq(
                {
                    success: (response) => {
                        console.log(" got response from getproduct api ws")
                        this.commit('setReqList', response.data);
                        console.log(response.data)
                    },
                    error: () => {
                        this.commit('setReqList',[])
                    },
                }
            )
        }

    }
}