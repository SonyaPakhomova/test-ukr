# :chains: Description 
This project is an application that works with a database that stores data about the product, users, orders and order data

# :rocket: Ways to work 
- Use `//localhost:8080/goods/contains?contains=fee` to get all goods contains "fee";
- Use `//localhost:8080/goods/getAll` to get all goods in DB;
- Use `//localhost:8080/order/getAll` to get all order in DB;
- Use `//localhost:8080/orders/get-more-than-two` to get orders with two or more different goods;
- Use `//localhost:8080/orders/get-by-quantity` to get orders with total quantity between 5 and 10;
- Use `//localhost:8080/orders/getAll` to get all orders in DB;
- Use `//localhost:8080/users/delete-inactive` to delete all users from DB with inactive account status;
- Use `//localhost:8080/users/update-by-email?name=Kolya&email=roma@gmail.com` to update user name by email;

# ✔️ Instructions for setup 
Install MySQL and MySQL Workbench
Create a schema by using the script from init_db.sql in MySQL Workbench
In the db.properties and AppConfig change data properties to the ones you specified when installing MySQL

