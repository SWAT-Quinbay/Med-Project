import Vuex from 'vuex';
import Vue from 'vue'

import user from '@/store/modules/user.store';
import admin from '@/store/modules/admin.store'

Vue.use(Vuex)

export default new Vuex.Store({
   modules:{
    user,
    admin
   }
})
