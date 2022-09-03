import BillingPageProductList from "@/components/RetailerComponents/BillingPageProductList.vue";
import ButtonComponent from "@/components/ButtonComponent.vue";
export default {
  name: "BillingPageProductArea",
  data() {
    return {
      searchKey: "",
    };
  },
  components: {
    BillingPageProductList,
    ButtonComponent,
  },
};
