<template>
  <tr class="table--tr--single--entity align-middle">
    <td>{{ index + 1 }}</td>
    <td>{{ product.name }}</td>
    <td>{{ product.netQuantity }}</td>
    <td>₹ {{ product.price }}</td>
    <td>
    <BadgeComponent
        :label="checkAvailabilityStatus ? 'Available' : 'Out of Stock'"
        :className="[
          checkAvailabilityStatus ? 'badge--success--outline' : 'badge--error--outline',
          'badge--outline--sm',
        ]"
      />
      </td>
    <td>
      <BadgeComponent
        :label="checkProductionStatus ? 'In Production' : 'Stopped'"
        :className="[
          checkProductionStatus ? 'badge--success--outline' : 'badge--error--outline',
          'badge--outline--sm',
        ]"
      />
    </td>
    <td>{{ toDataString(product.createdAt) }}</td>
    <td>
      <ButtonComponent
        label="Edit"
        buttonStyle="btn--edit--sm"
        @onClick="toggleActionModal(product)"
        type="button"
      />
    </td>
    <td>
      <ButtonComponent
        label="Stop Production"
        buttonStyle="btn--delete--sm"
        @onClick="changeProductionStatus()"
        type="button"
      />
    </td>
  </tr>
</template>
<script>
import BadgeComponent from "@/components/BadgeComponent.vue";
import ButtonComponent from "@/components/ButtonComponent.vue";
export default {
  name: "InventoryTableList",
  props: {
    product: {
      type: Object,
      default: () => {},
    },
    index : {
        type : Number,
        default : 0
    }
  },
  components: {
    BadgeComponent,
    ButtonComponent,
  },
  computed: {
    checkProductionStatus() {
      return this.product.available;
    },
    checkAvailabilityStatus(){
      return this.product.netQuantity > 0
    }
  },
  methods : {
    changeProductionStatus(){
      this.$emit('activateModal', {productId : this.product.id})
    },
    toggleActionModal(data){
        this.showActionModal = true;
        const constructedModalData = {
          modalHeader: "Edit Product Information",
          productData: data,
          modalButtonName: "Save Product",
        };
        this.$emit("sendDataToEditModal", constructedModalData);
    },
    toDataString(date){
      return new Date(date).toDateString()
    }
  }
};
</script>
<style scoped>
.table--tr--single--entity {
  border-bottom: 1px solid rgba(233, 233, 233, 0.683);
}

.table--tr--single--entity > td {
  padding-left: 15px;
  padding-top: 8px;
  padding-bottom: 8px;
  font-size: 14px;
  color: #646464;
}
</style>
