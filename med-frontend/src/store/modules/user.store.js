import { checkAuthToken , authUser } from "@/service/auth.service";
import { setToken } from "@/utils/storage";
import { redirectDashboard } from "@/utils/roleRedirect"

export default {
    state : {
        currentUser : {
            username : "",
            authenticated : false,
            role : "",
            userId : ""
        }
    },
    getters : 
    {
        getUserFromState(state){
            return state.currentUser
        },
        getUserId(state){
            return state.currentUser.userId;
        }
    },
    mutations : {
        setUserData(state,userData){
            const { username , authenticated , roles , userId } = userData;
            state.currentUser.userId = userId;
            state.currentUser.username = username;
            state.currentUser.authenticated = authenticated;
            state.currentUser.role = roles[0].authority;
        },
        clearUserState(state){
            state.currentUser = {};
        }
    },
    actions : {
        UPDATE_USER_DATA({commit},{ authToken }){
            checkAuthToken({
                authToken,
                successCallback : (res) => {
                    console.log(res)
                    commit.setUserData(res)
                },
                errorCallback : (err) => {
                    console.log(err)
                }
            })
        },
        AUTHENTICATE_USER({commit},{ userData }){
            authUser({
                userData,
                successCallback : (res) => {
                    if(res.status === 200){
                        const { data } =  res
                        setToken(data.token)
                        commit('setUserData',data);
                        redirectDashboard(data)
                    }else{
                        console.log(res)
                    }
                },
                errorCallback : (err) => {
                    console.log(err)
                }
            })
        }
    }
}