import {validateUser} from '../service/loginservice'
export default{
    name:"Logincomponent",
    data(){
       return{
        user:{
            userId:'',
            password:'',
            role:'retailseller',
        },
       };
    },
    methods:{
        validate(){
            console.log(this.user)
            // this.$router.push({path:'/wholeseller'})
            validateUser({
                success:(response)=>{
                    console.log(response)
                    if(response.data.response)
                    {
                        if(response.data.role=="retailseller")
                        {
                            localStorage.setItem("user",response.data.userId)
                            this.$router.push({path:'/retailer/product'})

                        }
                        if(response.data.role=="wholesaleseller")
                        {
                            localStorage.setItem("user",response.data.userId)
                            this.$router.push({path:'/wholeseller/product'})
                        }
                        
                    }
                    else{
                        alert("Invalid username or password")
                    }
                    //setitem
                    // localStorage.setItem("sellerId",response.seller_Id)
                    // if(response.response==true)
                    // this.$router.push({path:'/product'})
                    // else{
                    //     alert("Invalid username or password")
                    // }

                },
                error:(err)=>{
                    console.log(err)
                    alert("Something went wrong ..try again")
                   
                },
                user:this.user
            })
        }
    },
}