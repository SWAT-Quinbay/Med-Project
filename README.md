# stock_management

Readme(Retailer Login)

Functionalities:
	1.Login with either the retail seller or the wholesale seller login
		Scripts to run for the creation of users:
			insert into users(userid,password,role)values('seller1','password','retailseller');
				insert into users(userid,password,role)values('seller2’,’password','retailseller');
				insert into users(userid,password,role)values(‘wholesaleseller1','password’,’wholesaleseller');

	2.insert the values :
		using the post method in postman with this url and the request body from the json file(retailseller.json)
		http://localhost:8081/post
	
-------------------------------------------------------------------------------

Read Me(wholeseller)


Firstly insert values in Wholesaler Database using PostMan

Api-Post method= http://localhost:8183/wholesaler/addDetails

In Response body(json)  add the below json objects one by one :

{
        "productId": 1,
        "productName": "dolo",
        "productPrice": 100,
        "stock": 30000,
        "imageUrl": "https://blog.talkcharge.com/wp-content/uploads/2019/10/Dolo-650-tablets.jpg"
}

{
        "productId": 2,
        "productName": “act”,
        "productPrice": 100,
        "stock": 30000,
        "imageUrl": ""https://5.imimg.com/data5/LW/HR/IU/SELLER-52615965/ketorolac-tromethamine-500x500.jpeg"
}

{
        "productId": 3,
        "productName": “vicks”,
        "productPrice": 100,
        "stock": 30000,
        "imageUrl": ""https://images.ctfassets.net/umpxkz97ty8t/2nSTCZrQgakrrJyY4QZ9j3/c937af88858c203fda3ae5b256b21c62/Product-Vicks-coughdrops.png?w=460&fm=png"
}

{
        "productId": 4,
        "productName": “paracetomol”,
        "productPrice": 100,
        "stock": 30000,
        "imageUrl": "https://i0.wp.com/tajlifesciences.com/wp-content/uploads/Paracetamol-500-MG-Tablet-manufcaturer-india-Taj-Life-Sciences-6-scaled.jpg?resize=2048%2C1365&ssl=1"
}



------------------------------------------------------------------------------

FRONT-END
For retail seller->
    User name: seller1
    Password:password

    PRODUCTS->To view the products present in the store
                   Does not display the products that are out of stock
    BUY->for adding it to the cart to sell it to the customer 
        send sales report->send sales report sends a report of all the products sold
        empties the cart once the saes report as sent
    REQUEST-> to request items to be added in the stock of the retailer
            empties the cart once the saes report as sent



For WholeSaleSeller->
    User name: wholesaleseller1
    Password:password
    REQUEST->to view, accept or decline the request 
 
