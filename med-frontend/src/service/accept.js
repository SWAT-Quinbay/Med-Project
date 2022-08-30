import axios from 'axios'

export const accept=({success,error,item})=>{
    console.log(item)
    let temp={...item,status:"accepted"}
    axios.post("http://10.30.1.86:8183/wholesaler/decrementStock",temp)
    .then((response)=>success && success(response))
    .catch((err)=>error && error(err))
}


export const reject=({success,error,item})=>{
    console.log(item)
    let temp={...item,status:"denied"}
    axios.post("http://10.30.1.86:8183/wholesaler/declineOrder",temp)
    .then((response)=>success && success(response))
    .catch((err)=>error && error(err))
}


