import SalesHistoryTableList from "@/components/RetailerComponents/RequestHistoryTableList.vue";
import { mapGetters } from "vuex";

export default {
  name: "SalesHistoryTable",
  components: {
    SalesHistoryTableList,
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
      dealerRequestHistory: "getDealerRequestHistory",
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
    this.$store.dispatch("GET_ALL_REQUEST_HISTORY", this.userId);
  },
};
