import ButtonComponent from "@/components/ButtonComponent.vue";
import { deleteToken } from "@/utils/storage";

export default {
  name: "NavBar",
  props: {
    NavBarData: {
      type: Object,
      default: () => {},
    },
  },
  components: {
    ButtonComponent,
  },
  methods: {
    logout() {
      deleteToken();
      this.$router.replace("/");
    },
  },
};
