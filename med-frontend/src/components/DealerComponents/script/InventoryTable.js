import InventoryTableList from "@/components/DealerComponents/InventoryTableList.vue";
import { mapGetters } from "vuex";

export default {
  name: "InventoryTable",
  components: {
    InventoryTableList,
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
      delearProductList: "getDealerInventory",
    }),
  },
  methods: {
    closeActionModal() {
      this.showBuyProductModal = false;
    },
    selectedProductFromList(data) {
      this.showBuyProductModal = true;
      this.selectedProduct = data;
    },
  },
  created() {
    this.$store.dispatch("GET_ALL_PRODUCT_BY_DEALER_ID", this.userId);
  },
};
