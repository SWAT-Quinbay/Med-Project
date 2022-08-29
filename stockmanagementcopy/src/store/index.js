import Vuex from 'vuex';
import Vue from 'vue'

import productStore from './productStore';
import requestStore from './requestStore';
import request from './request';
import cartStore from './cart';

Vue.use(Vuex)

const homePageStore = {
    state : {val : 'asdfghjk'},
    getters  : {
            getStateValue1(state) {
                return state.val;
            }
    },
    mutations :{
        setStateValue1(state,newval) {
            state.val = newval;
        }

    },
    actions : {
        SET_SOME_VALUE() {
            this.commit('setStateValue1','casdfghjkllkj')
        }

    }
}

export default new Vuex.Store({
   modules:{
    homePageStore,
    productStore,
    cartStore,
    requestStore,
    request
   }
})