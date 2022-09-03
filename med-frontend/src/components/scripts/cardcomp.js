import {accept,reject} from "../service/accept"
export default{
    name:"CardComp",
    props:{
        item:{
            type:Object,
            required:true,
    
        }
    },
    methods:{
        accepted(item){
            accept(
                {
                    success: (response) => {
                        console.log(" got response from ws req status api")
                        // this.commit('setProductList', response.data);
                        console.log(response.data)
                        window.location.reload()
                    },
                    error: (err) => {
                        console.log(err)
                    },
                    item
                }
            )
        },
        denied(item){
            reject(
                {
                    success: (response) => {
                        console.log(" got response from ws req status api")
                        // this.commit('setProductList', response.data);
                        console.log(response.data)
                        window.location.reload()
                    },
                    error: (err) => {
                        console.log(err)
                    },
                    item
                }
            )
        }
    }
}