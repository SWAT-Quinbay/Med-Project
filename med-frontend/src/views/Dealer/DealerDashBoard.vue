<template>
  <div>
    <div class="row">
      <div class="col-12 mb-3">
        <p class="navigation--label">Dealer / Dashboard</p>
      </div>
    </div>
    <div class="row">
      <div class="col-12 col-md-4 px-2">
        <!-- <InventoryTable/> -->
      </div>
      <div class="col-md-8">
        <div class="row">
          <div class="col-12">
            <div class="request--search--box">
              <div class="row justify-content-between align-items-center">
                <div class="col-12 col-md-4">
                  <p class="table--header">
                    Request Information
                    <span class="total--client--badge"
                      >{{ requestList.length }} Request</span
                    >
                  </p>
                </div>
                <div class="col-12 col-md-6">
                  <div class="row justify-content-end align-items-center gx-2">
                    <div class="col-6">
                      <input
                        type="text"
                        class="action--input"
                        placeholder="Search Request Id"
                      />
                    </div>
                    <div class="col-auto">
                      <ButtonComponent
                        label="Search"
                        buttonStyle="btn--primary--sm--outline"
                        @onClick="confirmAction()"
                        type="button"
                      />
                    </div>
                    <div class="col-auto">
                      <ButtonComponent
                        label="Clear"
                        buttonStyle="btn--danger--sm--outline"
                        @onClick="confirmAction()"
                        type="button"
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-12 col-md-4">
            <div class="action--form--controller">
              <select class="action--input">
                <option :value="null">Select Request Status</option>
                <option value="APPROVED">Approved</option>
                <option value="PENDING">Pending</option>
                <option value="REJECTED">Rejected</option>
              </select>
            </div>
            <div class="stepper--list--card">
              <RequestStepperCard
                v-for="(data, index) in requestList"
                :requestData="data"
                @sendDataToDetailTab="switchSelectedProduct"
                :key="index"
                :index="index"
              />
            </div>
          </div>
          <div class="col-12 col-md-8">
            <RequestDetailCard
              :requestData="requestList[selectedIndex]"
              @approveRequest="activateApproveRequestModal"
              @denyRequest="activateDenyRequestModal"
            />
          </div>
        </div>
      </div>
    </div>
    <Transition>
      <RequestActionModal
        v-if="showRequestActionModal"
        :requestModalData="requestModalData"
        @closeAction="closeRequestActionModal"
        @saveAction="saveActionCall"
      />
    </Transition>
  </div>
</template>
<script>
import RequestStepperCard from "@/components/RequestStepperCard.vue";
import RequestDetailCard from "@/components/RequestDetailCard.vue";
import ButtonComponent from "@/components/ButtonComponent.vue";
import RequestActionModal from "@/components/RequestActionModal.vue";
import { changeRequestStatus } from "@/service/admin.service";
import { mapGetters } from "vuex";
import Vue from "vue";
export default {
  name: "DealerDashBoard",
  data() {
    return {
      selectedRequest: {},
      showRequestActionModal: false,
      requestModalData: {},
      selectedIndex: 0,
    };
  },
  components: {
    RequestStepperCard,
    RequestDetailCard,
    ButtonComponent,
    RequestActionModal,
  },
  computed: {
    ...mapGetters({
      userId: "getUserId",
      requestList: "getRequestHistory",
    }),
  },
  methods: {
    closeRequestActionModal() {
      this.showRequestActionModal = false;
    },
    switchSelectedProduct({ data, index }) {
      this.selectedRequest = data;
      this.selectedIndex = index;
    },
    activateApproveRequestModal({ id }) {
      this.showRequestActionModal = true;
      const modalData = {
        modalTitle: "Do you want to approve the request?",
        modalActionButtonLabel: "Approve",
        approved: true,
        // requestData: this.selectedRequest,
        requestId: id,
      };
      this.requestModalData = modalData;
    },
    activateDenyRequestModal({ id }) {
      this.showRequestActionModal = true;
      const modalData = {
        modalTitle: "Do you want to Deny the request?",
        modalActionButtonLabel: "Deny",
        approved: false,
        // requestData: this.selectedRequest,
        requestId: id,
      };
      this.requestModalData = modalData;
    },
    saveActionCall(data) {
      console.log(data);
      changeRequestStatus({
        requestData: data,
        successCallback: (res) => {
          if (res.status === 200) {
            Vue.$toast.success("Request Responded!");
            this.$store.dispatch("GET_ALL_REQUEST", this.userId);
          } else {
            Vue.$toast.success("Request Failed!");
          }
        },
        errorCallback: (err) => {
          console.log(err);
          Vue.$toast.error(err.message);
        },
      });
      this.showRequestActionModal = false;
    },
  },
  created() {
    this.$store.dispatch("GET_ALL_REQUEST", this.userId);
  },
};
</script>
<style scoped>
.request--search--box {
  background-color: #ffffff;
  border-radius: 10px;
  border: 1px solid rgb(209, 213, 216);
  margin-bottom: 10px;
  padding: 10px;
}

.navigation--label {
  margin: 0;
  font-size: 14px;
  margin-left: 10px;
  color: #999999;
}

.total--client--badge {
  padding: 3px 10px;
  font-size: 11px;
  border-radius: 10px;
  background-color: #dddddd7e;
  margin-left: 10px;
}

.table--header {
  font-size: 17px;
  font-weight: 700;
  margin: 0;
}

.select--request--text {
  color: #656565;
}

.action--input {
  width: 100%;
  height: 37px;
  background-color: #f1f1f1;
  border-radius: 10px;
  color: #313131;
  font-size: 14px;
  border: 1px solid #f1f1f1;
  padding-left: 10px;
  outline: none;
}
.action--input:focus {
  border: 1px solid #c5c5c5;
}

.action--input--label {
  font-size: 13px;
  font-weight: 500;
}

select {
  color: #656565 !important;
  font-size: 13px;
}

.action--input::placeholder {
  font-size: 13px;
}

.action--form--controller {
  background-color: #ffffff;
  border-radius: 10px;
  border: 1px solid rgb(209, 213, 216);
  margin-bottom: 10px;
  padding: 8px;
}

.stepper--list--card {
  background-color: #ffffff;
  border-radius: 8px;
  border: 1px solid rgb(209, 213, 216);
  margin-bottom: 10px;
  padding: 15px 15px;
  height: 490px;
  overflow-y: scroll;
}

.stepper--list--card::-webkit-scrollbar {
  width: 5px;
}

.stepper--list--card::-webkit-scrollbar-thumb {
  background-color: #e6e6e6 !important;
  width: 5px;
  border-radius: 10px;
}
</style>
