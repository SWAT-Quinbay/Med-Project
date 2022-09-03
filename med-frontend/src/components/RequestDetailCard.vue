<template>
  <div class="request--detail--card">
    <div v-if="true">
      <p class="request--id">REQUEST ID: 1</p>
      <div class="request--detail--body">
        <div class="row justify-content-between align-items-center">
          <div class="col-auto">
            <p class="request--text--header">Customer Name:</p>
            <p class="request--text--description">Vignesh Ravichandran</p>
          </div>
          <div class="col-auto">
            <BadgeComponent
              :label="'InActive'"
              :className="[
                checkStatus
                  ? 'badge--success--outline'
                  : 'badge--error--outline',
                'badge--outline--sm',
              ]"
            />
          </div>
        </div>

        <p class="request--text--header">Username:</p>
        <p class="request--text--description">admin</p>
        <p class="request--text--header">Request Date:</p>
        <p class="request--text--description">22-08-2022</p>
        <p class="request--text--header">Request Information:</p>
        <p class="request--text--description"></p>
      </div>
      <div class="request--detail--footer">
        <div class="row justify-content-end align-items-center g-2">
          <div class="col-3">
            <ButtonComponent
              label="Reject"
              buttonStyle="btn--black--outline"
              @onClick="deactivateConfirmation()"
              type="button"
            />
          </div>
          <div class="col-3">
            <ButtonComponent
              label="Approve"
              buttonStyle="btn--black"
              @onClick="deactivateConfirmation()"
              type="button"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="request--notfound" v-if="false">
      <p class="select--request">Select any request from left!</p>
    </div>
  </div>
</template>
<script>
import BadgeComponent from "@/components/BadgeComponent.vue";
import ButtonComponent from "@/components/ButtonComponent.vue";
export default {
  name: "RequestDetailCard",
  components: {
    BadgeComponent,
    ButtonComponent,
  },
  computed: {
    checkStatus() {
      return true;
    },
  },
};
</script>
<style scoped>
.request--detail--card {
  background-color: #ffffff;
  border-radius: 8px;
  border: 1px solid rgb(209, 213, 216);
  margin-bottom: 15px;
  padding: 12px;
  height: 555px;
  overflow-y: scroll;
}
.select--request {
  color: #999999;
  font-size: 15px;
  text-align: center;
  margin-top: 20%;
}
.request--detail--body {
  background-color: #fafbfc;
  border-radius: 8px;
  border: 1px solid rgb(209, 213, 216);
  margin-bottom: 15px;
  padding: 12px;
  height: 430px;
  overflow: scroll;
}
.request--id {
  font-size: 14px;
  color: #353535;
  font-weight: 700;
  margin-top: 8px;
}
.request--text--header {
  font-size: 14px;
  color: #000000;
  font-weight: 600;
  margin: 0;
  margin-top: 10px;
}
.request--text--description {
  font-size: 13px;
  color: #5b5b5b;
  font-weight: 500;
  margin: 0px;
}
</style>
