import axios from 'axios'

export const getreq=({success,error})=>{
    
        axios.get("http://10.30.1.86:8183/wholesaler/getOrders")
    .then((response)=>success && success(response))
    .catch((err)=>error && error(err))

    
    // axios.post("http://10.30.1.92:8081/validate",list)
    // .then((response)=>success && success(response))
    // .catch((err)=>error && error(err))
}