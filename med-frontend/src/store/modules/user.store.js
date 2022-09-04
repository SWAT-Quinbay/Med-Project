import { authUser } from "@/service/auth.service";
import { getToken, setToken } from "@/utils/storage";
import { redirectDashboard } from "@/utils/roleRedirect";

export default {
  state: {
    currentUser: {
      username: "",
      authenticated: false,
      role: "",
      userId: "",
    },
    token: "",
    userId: "",
  },
  getters: {
    getUserFromState(state) {
      return state.currentUser;
    },
    getUserId(state) {
      return state.userId;
    },
  },
  mutations: {
    setUserData(state, userData) {
      const { username, authenticated, roles, userId, tokenKey } = userData;
      state.currentUser.userId = userId;
      state.userId = userId;
      state.currentUser.username = username;
      state.token = tokenKey;
      state.currentUser.authenticated = authenticated;
      state.currentUser.role = roles[0].authority;
    },
    clearUserState(state) {
      state.currentUser = {};
      state.userId = "";
      state.token = "";
    },
  },
  actions: {
    AUTHENTICATE_USER({ commit }, { userData }) {
      authUser({
        userData,
        successCallback: (res) => {
          if (res.status === 200) {
            const { data } = res;
            commit("setUserData", data);
            while (getToken() == null) {
              console.log("not yet Stored");
              setToken(data.token);
            }
            redirectDashboard(data);
          } else {
            console.log(res);
          }
        },
        errorCallback: (err) => {
          console.log(err);
        },
      });
    },
    LOGOUT_USER({ commit }) {
      commit("clearUserState");
    },
  },
};
