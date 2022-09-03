<template>
    <div class="request--card request--approved--card">
        <div class="row">
            <div class="col-12">
                <font-awesome-icon icon="fa-solid fa-circle-check" class="approved--icon" />
                <!-- <font-awesome-icon icon="fa-solid fa-circle-xmark" class="denied--icon" />
                <font-awesome-icon icon="fa-solid fa-circle-exclamation" class="pending--icon"  /> -->
            </div>
            <div class="col-12">
                <p class="order--id--text">REQUEST ID: 1</p>
                <p class="order--id--company--name">Divine Medi Warehouse</p>
            </div>
            <div class="col-12 mt-2">
                <BadgeComponent
                    :label="'Approved'"
                    :class="[
                    !checkStatus ? 'badge--success--solid' : 'badge--warning--solid',
                    'badge--solid--lg',
                    ]"
                />
            </div>
        </div>
    </div>
</template>
<script>

import BadgeComponent from "@/components/BadgeComponent.vue"

export default {
    name : "ProductStepperCard",
    components : {
        BadgeComponent
    },
    props : {
        request : {
            type : Object,
            default : () => {}
        }
    },
    computed: {
    checkStatus() {
      return this.request === "Completed";
    },
  },
}
</script>
<style scoped>
    .request--card:hover{
        cursor: pointer;
    }

    .request--approved--card{
        background-color: #F0FCF6;
        border-radius: 8px;
        border: 1px solid #CEECE4;
        margin-bottom: 15px;
        padding: 12px;
    }
    .request--pending--card{
        background-color: #fcf9f0;
        border-radius: 8px;
        border: 1px solid #ece5ce;
        margin-bottom: 15px;
        padding: 12px;
    }
    .request--denied--card{
        background-color: #fcf0f0;
        border-radius: 8px;
        border: 1px solid #eccece;
        margin-bottom: 15px;
        padding: 12px;
    }
    .approved--icon{
        color: #27BD68;
        font-size: 30px;
    }
    .pending--icon{
        color: #faa338;
        font-size: 30px;
    }
    .denied--icon{
        color: #bd2727;
        font-size: 30px;
    }
    .order--id--text{
        margin: 0;
        font-size: 12px;
        color: #999999;
        font-weight: 500;
        margin-top: 5px;
    }
    .order--id--company--name{
        margin: 0;
        font-size: 15px;
        color: #000000;
    }
</style>