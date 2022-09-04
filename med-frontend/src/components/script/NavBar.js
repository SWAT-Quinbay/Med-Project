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
      this.$store.dispatch("LOGOUT_USER");
      deleteToken();
      this.$router.replace("/");
    },
  },
};
