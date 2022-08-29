import axios from 'axios'

export const sendCartItems=({success,error,list})=>{
    console.log(list)
    
        axios.post("http://10.30.1.92:8082/sales/add",list)
    .then((response)=>success && success(response))
    .catch((err)=>error && error(err))
}