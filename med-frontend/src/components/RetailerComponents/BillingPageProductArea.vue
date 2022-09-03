<template>
  <div>
    <div class="search--card">
      <div class="row align-items-center">
        <div class="col-12 col-md-9 my-2 pe-md-0">
          <input
            type="text"
            class="search--bar"
            v-model="searchKey"
            @blur="searchForProduct(searchKey)"
            placeholder="Search Product"
          />
        </div>
        <div class="col-12 col-md-3 my-2">
          <ButtonComponent
            label="Search"
            :disabled="false"
            buttonStyle="btn--primary--sm--100"
            @onClick="addNewProduct()"
            type="button"
          />
        </div>
      </div>
    </div>
    <div class="my-3">
      <div class="row g-3">
        <BillingPageProductList
          class="col-12 col-md-6 col-lg-4"
          v-for="(data, index) in 25"
          :key="index"
          :product="data"
          @addToCart="addToCart(data)"
        />
      </div>
    </div>
  </div>
</template>
<script>
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
</script>
<style scoped>
.search--card {
  background-color: #ffffff;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  padding: 1px 10px;
  border-radius: 10px;
}
.search--bar {
  background-color: #f1f1f1;
  border: 0.7px solid #e2e2e2;
  height: 40px;
  margin: 0;
  border-radius: 10px;
  padding-left: 10px;
  width: 100%;
}
.search--bar::placeholder {
  font-size: 14px;
}
.search--bar:focus {
  border: 1px solid #cdcdcd;
  outline: none;
}
</style>
