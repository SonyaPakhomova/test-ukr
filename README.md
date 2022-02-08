# :chains: Description 
This project is an application that works with a database that stores data about the product, users, orders and order data

# :rocket: Ways to work 
- Use `//localhost:8080/goods/contains?contains=fee` to get all goods contains "fee";
- Use `//localhost:8080/goods/getAll` to get all goods in DB;
- Use `//localhost:8080/goods/get-by-name?name=cake` to get good by name;
- Use `//localhost:8080/order/getAll` to get all order in DB;
- Use `//localhost:8080/order/create?userId=3&orderNumber=2365` to create new order;
- Use `//localhost:8080/orders/get-more-than-two` to get orders with two or more different goods;
- Use `//localhost:8080/orders/get-by-quantity` to get orders with total quantity between 5 and 10;
- Use `//localhost:8080/orders/getAll` to get all orders in DB;
- Use `//localhost:8080/orders/create?orderId=3&goodsId=5&quantity=6&total=452` to create new orders;
- Use `//localhost:8080/users/delete-inactive` to delete all users from DB with inactive account status;
- Use `//localhost:8080/users/update-by-email?name=Kolya&email=roma@gmail.com` to update user name by email;
- Use `//localhost:8080/users/create?name=Dima&email=dima@gmail.com&status=true` to create new user;
- Use `//localhost:8080/users/get-by-name?name=Roma` to get user by name;
- Use `//localhost:8080/users/get-by-email?email=roma@gmail.com` to get user by email;
- Use `//localhost:8080/orders/get-average?email=alice@gmail.com` to get average price of order by user email;


# ✔️ Instructions for setup 
Install MySQL and MySQL Workbench
Create a schema by using the script from init_db.sql in MySQL Workbench
In the db.properties and AppConfig change data properties to the ones you specified when installing MySQL

