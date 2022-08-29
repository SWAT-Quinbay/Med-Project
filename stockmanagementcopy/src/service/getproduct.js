import axios from 'axios';

export const getProductList=({success,error})=>
{
    let user=localStorage.getItem("user")
    let apiendpoint=`http://10.30.1.92:8081/get/${user}`
    axios.get(apiendpoint)
    .then((response)=>{
        console.log(response)
        success && success(response)
    })
    .catch((err)=>{
        error && error(err)
    })
}