import { checkAuthToken , authUser } from "@/service/auth.service";
import { setToken } from "@/utils/storage";
import { redirectDashboard } from "@/utils/roleRedirect"

export default {
    state : {
        currentUser : {
            username : "",
            authenticated : false,
            role : ""
        }
    },
    getters : 
    {
        getUserFromState(state){
            return state.currentUser
        }
    },
    mutations : {
        setUserData(state,userData){
            const { username , authenticated , roles } = userData;
            state.currentUser.username = username;
            state.currentUser.authenticated = authenticated;
            state.currentUser.role = roles[0].authority;
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