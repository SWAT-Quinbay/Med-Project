<template>
  <tr class="table--tr--single--entity align-middle">
    <td>{{ index + 1 }}</td>
    <td>{{ user.name }}</td>
    <td>{{ user.username }}</td>
    <td>{{ toDataString(user.createdAt) }}</td>
    <td>
      <BadgeComponent
        :label="checkStatus ? 'Active' : 'InActive'"
        :className="[
          checkStatus ? 'badge--success--outline' : 'badge--error--outline',
          'badge--outline--sm',
        ]"
      />
    </td>
    <td>{{ user.role }}</td>
    <td>
      <ButtonComponent
        label="Edit"
        buttonStyle="btn--edit--sm"
        @onClick="editModal()"
        type="button"
      />
    </td>
    <td>
      <ButtonComponent
        :label="user.active ? 'Deactivate' : 'Activate'"
        buttonStyle="btn--delete--sm"
        @onClick="accessConfirmation()"
        type="button"
      />
    </td>
  </tr>
</template>
<script>
import BadgeComponent from "@/components/BadgeComponent.vue";
import ButtonComponent from "@/components/ButtonComponent.vue";



export default {
  name: "UserTableList",
  props: {
    user: {
      type: Object,
      default: () => {},
    },
    index : {
      type : Number,
      default: 0
    }
  },
  components: {
    BadgeComponent,
    ButtonComponent,
  },
  computed: {
    checkStatus() {
      return this.user.active;
    },
  },
  methods: {
    editModal(){
      this.$emit("editModal", { userData : this.user });
    },
    accessConfirmation() {
      this.$emit("activateModal", { userId: this.user.userId });
    },
    toDataString(date){
      return new Date(date).toDateString()
    }
  },

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
