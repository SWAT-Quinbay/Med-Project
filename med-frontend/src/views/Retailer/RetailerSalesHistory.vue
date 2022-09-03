<template>
  <div>
    <div class="row">
      <div class="col-12 mb-3">
        <p class="navigation--label">Retailer / Sales History</p>
      </div>
      <div class="col-12 px-2">
        <SalesHistoryTable />
      </div>
    </div>
  </div>
</template>
<script>
import SalesHistoryTable from "@/components/RetailerComponents/SalesHistoryTable.vue";

export default {
  name: "DealerRequestHistory",
  components: {
    SalesHistoryTable,
  },
};
</script>
<style scoped>
.navigation--label {
  margin: 0;
  font-size: 14px;
  margin-left: 10px;
  color: #999999;
}
</style>
