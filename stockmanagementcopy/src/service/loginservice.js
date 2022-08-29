import axios from 'axios'

export const validateUser=({success,error,user})=>{
    console.log(user)
    axios.post("http://10.30.1.92:8081/validate",user)
    .then((response)=>success && success(response))
    .catch((err)=>error && error(err))
}