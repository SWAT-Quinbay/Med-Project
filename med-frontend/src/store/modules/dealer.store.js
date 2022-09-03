import {
  getAllProductFromAdmin,
  getAllProductByDealerId,
  getAllRequestHistory,
} from "@/service/dealer.service";

export default {
  state: {
    dealerProductList: [],
    dealerInventoryList: [],
    dealerRequestHistory: [],
  },
  getters: {
    getDealerProductList(state) {
      return state.dealerProductList;
    },
    getDealerInventory(state) {
      return state.dealerInventoryList;
    },
    getDealerRequestHistory(state) {
      return state.dealerRequestHistory;
    },
  },
  mutations: {
    setDealerProductList(state, productList) {
      state.dealerProductList = productList;
    },
    setDealerInventory(state, inventoryList) {
      state.dealerInventoryList = inventoryList;
    },
    setDealerRequestHistory(state, requestHistory) {
      state.dealerRequestHistory = requestHistory;
    },
  },
  actions: {
    GET_ALL_PRODUCT_FROM_ADMIN({ commit }, value = "") {
      getAllProductFromAdmin({
        searchText: value,
        successCallback: ({ data }) => {
          commit("setDealerProductList", data.content);
        },
        errorCallback: (err) => {
          console.log(err);
        },
      });
    },

    GET_ALL_PRODUCT_BY_DEALER_ID({ commit }, value = "") {
      getAllProductByDealerId({
        dealerId: value,
        successCallback: ({ data }) => {
          commit("setDealerInventory", data.content);
        },
        errorCallback: (err) => {
          console.log(err);
        },
      });
    },

    GET_ALL_REQUEST_HISTORY({ commit }, value = "") {
      getAllRequestHistory({
        dealerId: value,
        successCallback: ({ data }) => {
          commit("setDealerRequestHistory", data);
        },
        errorCallback: (err) => {
          console.log(err);
        },
      });
    },
  },
};
