import { baseUserUrl } from "@/utils/urls";
import axios from "axios";
import { getToken } from "@/utils/storage.js"

axios.defaults.headers.common['Authorization'] = getToken();

export const createNewUser = ({ userData , successCallback , errorCallback }) => {
    axios.post(`${baseUserUrl}/users/add`, userData)
    .then((res)=>{
        successCallback && successCallback(res)
    })
    .catch((err)=>{
        errorCallback && errorCallback(err)
    })
}

export const updateUserName = ({ userData , successCallback , errorCallback }) => {
    axios.put(`${baseUserUrl}/users/update/name`, userData)
    .then((res)=>{
        successCallback && successCallback(res)
    })
    .catch((err)=>{
        errorCallback && errorCallback(err)
    })
}

export const updateUserPassword = ({ userData , successCallback , errorCallback }) => {
    axios.put(`${baseUserUrl}/users/update/password`, userData)
    .then((res)=>{
        successCallback && successCallback(res)
    })
    .catch((err)=>{
        errorCallback && errorCallback(err)
    })
}

export const updatePermission = ({  userId , successCallback , errorCallback }) => {
    axios.put(`${baseUserUrl}/users/update/active/${userId}`)
    .then((res)=>{
        successCallback && successCallback(res)
    })
    .catch((err)=>{
        errorCallback && errorCallback(err)
    })
}

export const getAllUser = ({searchText , successCallback , errorCallback }) => {
    axios.get(`${baseUserUrl}/users/search?page=0&query=${searchText}&size=10`)
    .then((res)=>{
        successCallback && successCallback(res)
    })
    .catch((err)=>{
        errorCallback && errorCallback(err)
    })
}


export const getAllInventoryProduct = ({searchText , successCallback , errorCallback }) => {
    axios.get(`${baseUserUrl}/inventory/search?page=0&query=${searchText}&size=10`)
    .then((res)=>{
        successCallback && successCallback(res)
    })
    .catch((err)=>{
        errorCallback && errorCallback(err)
    })
}

export const createNewProduct = ({productData , successCallback , errorCallback }) => {
    axios.post(`${baseUserUrl}/inventory/add`, productData)
    .then((res)=>{
        successCallback && successCallback(res)
    })
    .catch((err)=>{
        errorCallback && errorCallback(err)
    })
}

export const updateProductData = ({productData , successCallback , errorCallback }) => {
    axios.put(`${baseUserUrl}/inventory/update`, productData)
    .then((res)=>{
        successCallback && successCallback(res)
    })
    .catch((err)=>{
        errorCallback && errorCallback(err)
    })
}

export const updateProductPermission = ({  productId , successCallback , errorCallback }) => {
    axios.put(`${baseUserUrl}/inventory/update/active/${productId}`)
    .then((res)=>{
        successCallback && successCallback(res)
    })
    .catch((err)=>{
        errorCallback && errorCallback(err)
    })
}
