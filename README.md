### Med Project

## Table of Contents

        - Major Roles
                - Admin
                - Dealer
                - Retailer

        - Features
                - Admin
                        - Approve Dealer stock request (Dashboard)
                        - Manage Products
                        - Manage Users

                - Dealer
                        - Manage Retailer stock requests (Dashboard)
                        - View dealer Products
                        - request stock to Admin (Buy Product Page)
                        - View stock requests made by the dealer
                - Retailer
                        - Point of Sale (POS)
                        - Sales histories
                        - request stock to dealer (Buy Product Page)
                        - View stock requests made by the retailer

### Steps to run

#### Backend

        - Clone the repo
        - cd into the backend folder

        - Create Database for services
                - Postgres
                        - admin
                        - dealer
                        - retailer

                - Mongo DB
                        - sales

        - Open services in IntelliJ.
        - run the services

#### After running the services make sure to comment line in each service application.properties file

`#spring.jpa.hibernate.ddl-auto=update `

### Create Admin User

                - Open Postman
                - Send a post request to http://localhost:8081/users/add (for the testing purpose this endpoint is open)
                - Body
                        - {
                                "name":"Admin",
                                "username":"admin",
                                "password":"password",
                                "role":"ADMIN"
                        }

### Frontend

        -   cd med-frontend
        -  npm install
        -  npm run serve

### Things are done by the previous team.

        - Single Wholesale Dealer
        - Multiple Retailer

        - data stored and retrieved from DB and shown in the front end.
        - Implemented the selling part in retailers that use the Postgres DB

### Our updates and changes

        - Implemented JWT authentication that stores in Redis with associated UUID key.
                - The correspondent UUID key send to the front end and stored in the local storage.
                - The UUID keys are used to retrieve the JWT token from Redis.

        - Used Router Guards to protect the routes in the front end.

        - Rolewise authorization implemented.

        - Added another level of User (Admin)
                - Admin can approve or deny the stock request made by the dealer
                - Admin can add new products
                - Admin only adds new users (Retailer, Dealer)
                - Used Redis for storing product details.

        - Introduced Multiple wholesale dealers
                - Dealer can request stock to admin for a specific product.
                - Once the admin approves the request, the requested amount of stock will be added to the dealer's stock by using ``` Kafka ```.
                - Dealer can view their product and its details.
                - Dealer can approve or deny the stock request made by the retailer.

        - Improved the POS system
                - Enhanced the frontend Ui.
                - Retailers can request a specific amount of stock from a specific dealer.
                - stock incrementing on approval is done by using ``` Kafka ```.

        - Used Mongo DB for storing the sales histories.

### need to be done

    ### FrontEnd
    	- Responsive model
    	- pagination
    	- loaders and no record found text


    ### Backend
    	 - use Redis to store and retrieve users
    	 - add a DTO layer for sales service


    	 - store product details in all DB and use Kafka to update changes that occurred in the main DB (not a good way doesn't provide data nonredundant!)
