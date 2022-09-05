import InventoryTableList from "@/components/RetailerComponents/InventoryTableList.vue";
import ButtonComponent from "@/components/ButtonComponent.vue";
import SpinnerProgress from "@/components/SpinnerProgress";
import { mapGetters } from "vuex";

export default {
  name: "InventoryTable",
  components: {
    InventoryTableList,
    ButtonComponent,
    SpinnerProgress,
  },
  data() {
    return {
      showConfirmModal: false,
      showBuyProductModal: false,
      searchText: "",
      selectedProduct: {},
    };
  },
  computed: {
    ...mapGetters({
      userId: "getUserId",
      retailerInventory: "getRetailerInventory",
      loader: "getRetailerLoader",
    }),
  },
  methods: {
    clearSearch() {
      this.searchText = "";
      this.$store.dispatch("GET_ALL_PRODUCT_BY_RETAILER_ID", {
        retailerId: this.userId,
      });
    },
    searchName() {
      this.$store.dispatch("GET_ALL_PRODUCT_BY_RETAILER_ID", {
        retailerId: this.userId,
        searchKey: this.searchText,
      });
    },
    closeActionModal() {
      this.showBuyProductModal = false;
    },
    selectedProductFromList(data) {
      this.showBuyProductModal = true;
      this.selectedProduct = data;
    },
  },
  created() {
    this.$store.dispatch("GET_ALL_PRODUCT_BY_RETAILER_ID", {
      retailerId: this.userId,
    });
  },
};
