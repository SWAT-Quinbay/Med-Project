<template>
  <div>
    <div class="container-fluid mt-5">
      <div class="row px-3">
        <div class="col-12 col-md-6 col-lg-7 col-xl-9 order-1 order-md-0 mt-2">
          <BillingPageProductArea />
        </div>
        <div class="col-12 col-md-6 col-lg-5 col-xl-3 order-0 order-md-1 mt-2">
          <div class="fixed--col">
            <BillingPOS />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import BillingPOS from "@/components/RetailerComponents/BillingPOS.vue";
import BillingPageProductArea from "@/components/RetailerComponents/BillingPageProductArea.vue";
export default {
  name: "RetailerDashBoard",
  components: {
    BillingPOS,
    BillingPageProductArea,
  },
};
</script>
<style scoped>
.fixed--col {
  position: sticky;
  top: 5%;
}
</style>
