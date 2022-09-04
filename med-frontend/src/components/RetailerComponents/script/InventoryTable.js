import InventoryTableList from "@/components/RetailerComponents/InventoryTableList.vue";
import ButtonComponent from "@/components/ButtonComponent.vue";
import { mapGetters } from "vuex";

export default {
  name: "InventoryTable",
  components: {
    InventoryTableList,
    ButtonComponent,
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
      user: "getUserFromState",
      retailerInventory: "getRetailerInventory",
    }),
  },
  methods: {
    // clearSearch() {
    //   this.searchText = "";
    //   this.$store.dispatch("GET_ALL_PRODUCT_BY_RETAILER_ID", this.user.userId);
    // },
    // searchName() {
    //   this.$store.dispatch("GET_ALL_PRODUCT_BY_RETAILER_ID", this.searchText);
    // },
    closeActionModal() {
      this.showBuyProductModal = false;
    },
    selectedProductFromList(data) {
      this.showBuyProductModal = true;
      this.selectedProduct = data;
    },
    requestActionCall(data) {
      console.log(data);
    },
  },
  created() {
    this.$store.dispatch("GET_ALL_PRODUCT_BY_RETAILER_ID", this.user.userId);
  },
};
