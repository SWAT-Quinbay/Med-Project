import ButtonComponent from "@/components/ButtonComponent.vue";
export default {
  name: "BillingPageProductList",
  components: {
    ButtonComponent,
  },
  data() {
    return {
      paymentMethod: "Cash",
    };
  },
};
