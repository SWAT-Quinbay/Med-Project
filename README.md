# stock_management

Readme(Retailer Login)

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

        - Open services in intelij
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
