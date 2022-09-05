import { getAllProductFromAdmin } from "@/service/dealer.service";
import Vue from "vue";
import {
  getAllProductByRetailerId,
  getAllRequestHistory,
  getAllProductsFromAllDealers,
  getAllSalesHistory,
} from "@/service/retailer.service";

export default {
  state: {
    retailerLoader: false,
    retailerProductList: [],
    retailerInventoryList: [],
    retailerSalesHistory: [],
    retailerBuyProductList: [],
    retailerRequestHistory: [],
  },
  getters: {
    getRetailerLoader(state) {
      return state.retailerLoader;
    },
    getRetailerProductList(state) {
      return state.retailerProductList;
    },
    getRetailerInventory(state) {
      return state.retailerInventoryList;
    },
    getRetailerBuyProductList(state) {
      return state.retailerBuyProductList;
    },
    getRetailerSalesHistory(state) {
      return state.retailerSalesHistory;
    },
    getRetailerRequestHistory(state) {
      return state.retailerRequestHistory;
    },
  },
  mutations: {
    setRetailerLoaderToggle(state) {
      state.retailerLoader = !state.retailerLoader;
    },
    setRetailerProductList(state, productList) {
      state.retailerProductList = productList;
    },
    setRetailerInventory(state, inventoryList) {
      state.retailerInventoryList = inventoryList;
    },
    setRetailerSalesHistory(state, salesHistory) {
      state.retailerSalesHistory = salesHistory;
    },
    setRetailerBuyProductList(state, productList) {
      state.retailerBuyProductList = productList;
    },
    setRetailerRequestHistory(state, requestHistory) {
      state.retailerRequestHistory = requestHistory;
    },
  },
  actions: {
    GET_ALL_PRODUCT_FROM_DEALER({ commit }, value = "") {
      commit("setRetailerLoaderToggle");
      getAllProductFromAdmin({
        searchText: value,
        successCallback: ({ data }) => {
          commit("setRetailerProductList", data.content);
        },
        errorCallback: (err) => {
          console.log(err);
          Vue.$toast.error("Something gone wrong! Please again later.");
        },
      });
      commit("setRetailerLoaderToggle");
    },
    GET_ALL_PRODUCT_BY_RETAILER_ID({ commit }, { retailerId, searchKey = "" }) {
      commit("setRetailerLoaderToggle");
      getAllProductByRetailerId({
        retailerId,
        searchKey,
        successCallback: ({ data }) => {
          commit("setRetailerInventory", data);
        },
        errorCallback: (err) => {
          console.log(err);
          Vue.$toast.error("Something gone wrong! Please again later.");
        },
      });
      commit("setRetailerLoaderToggle");
    },

    GET_ALL_PRODUCTS_FROM_ALL_DEALERS({ commit }, value = "") {
      commit("setRetailerLoaderToggle");
      getAllProductsFromAllDealers({
        searchKey: value,
        successCallback: ({ data }) => {
          commit("setRetailerBuyProductList", data);
        },
        errorCallback: (err) => {
          console.log(err);
          Vue.$toast.error("Something gone wrong! Please again later.");
        },
      });
      commit("setRetailerLoaderToggle");
    },

    GET_ALL_REQUEST_HISTORY_RETAILER(
      { commit },
      { retailerId = "", requestId = "", status = "" }
    ) {
      commit("setRetailerLoaderToggle");
      getAllRequestHistory({
        retailerId,
        requestId,
        status,
        successCallback: ({ data }) => {
          commit("setRetailerRequestHistory", data);
        },
        errorCallback: (err) => {
          console.log(err);
          Vue.$toast.error("Something gone wrong! Please again later.");
        },
      });
      commit("setRetailerLoaderToggle");
    },

    GET_ALL_SALES_HISTORY(
      { commit },
      { retailerId, value = "", orderId = "" }
    ) {
      commit("setRetailerLoaderToggle");
      getAllSalesHistory({
        retailerId,
        orderId,
        searchKey: value,
        successCallback: ({ data }) => {
          commit("setRetailerSalesHistory", data);
        },
        errorCallback: (err) => {
          console.log(err);
          Vue.$toast.error("Something gone wrong! Please again later.");
        },
      });
      commit("setRetailerLoaderToggle");
    },
  },
};
