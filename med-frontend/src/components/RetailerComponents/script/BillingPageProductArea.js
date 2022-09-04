import BillingPageProductList from "@/components/RetailerComponents/BillingPageProductList.vue";
import ButtonComponent from "@/components/ButtonComponent.vue";
import { mapGetters } from "vuex";

export default {
  name: "BillingPageProductArea",
  data() {
    return {
      searchKey: "",
    };
  },
  computed: {
    ...mapGetters({
      userId: "getUserId",
      productList: "getRetailerInventory",
    }),
  },
  components: {
    BillingPageProductList,
    ButtonComponent,
  },
  methods: {
    addToCart(data) {
      this.$store.dispatch("ADD_PRODUCT_TO_CART", { product: data });
    },
    // searchForProduct(searchKey) {
    //   this.$store.dispatch("GET_ALL_PRODUCT_BY_RETAILER_ID", this.userId);
    // },
  },
  created() {
    this.$store.dispatch("GET_ALL_PRODUCT_BY_RETAILER_ID", this.userId);
  },
};
