<template>
    <div>
        <div class="row">
        <div class="col-12 mb-3">
            <p class="navigation--label"> Dealer / Buy Products</p>
        </div>
        <div class="col-12 px-2">
            <BuyProductsTable/>
        </div>
    </div>
    </div>
</template>
<script>

import BuyProductsTable from "@/components/DealerComponents/BuyProductsTable.vue"

export default {
    name : "DealerBuyProducts",
    components : {
        BuyProductsTable
    }
}
</script>
<style scoped>
    .navigation--label{
        margin: 0;
        font-size: 14px;
        margin-left: 10px;
        color: #999999;
    }
</style>