import axios from 'axios'

export const requestItems=({success,error,list})=>{
    console.log(list)
    // list=[{name:"paracetomol",price:5},{name:"paracetomol",price:7}]
    list.map((single_list)=>{
        let sid=localStorage.getItem("user")
        console.log("request")
        let temp={...single_list,retailSeller:sid}
        axios.post("http://10.30.1.86:8183/wholesaler/updateOrder",temp)
    .then((response)=>success && success(response))
    .catch((err)=>error && error(err))
})
window.location.reload();
    // axios.post("http://10.30.1.92:8081/validate",list)
    // .then((response)=>success && success(response))
    // .catch((err)=>error && error(err))
}