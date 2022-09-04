import ButtonComponent from "@/components/ButtonComponent.vue";
import CartProductItem from "@/components/RetailerComponents/CartProductItem.vue";
import { mapGetters } from "vuex";

export default {
  name: "BillingPageProductList",
  components: {
    ButtonComponent,
    CartProductItem,
  },
  data() {
    return {
      paymentMethod: "Cash",
      orderDataToModal: {},
    };
  },
  computed: {
    ...mapGetters({
      cartProducts: "getCartProduct",
      totalPrice: "getTotalPrice",
    }),
    // getNetPrice() {
    //   return Math.floor(this.totalPrice + (this.totalPrice / 100) * this.tax);
    // },
    // calculateTax() {
    //   return Math.floor((this.totalPrice / 100) * this.tax);
    // },
  },
};
