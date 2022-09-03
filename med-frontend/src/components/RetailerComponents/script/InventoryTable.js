import InventoryTableList from "@/components/RetailerComponents/InventoryTableList.vue";
import ButtonComponent from "@/components/ButtonComponent.vue";

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
  methods: {
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
};
