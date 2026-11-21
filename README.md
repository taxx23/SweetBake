# SweetBake

Custom cake and dessert ordering website for one bakery. INT322 Web Technologies term project.

Stack: JSP, JSTL, Bootstrap 5, Java Servlets (MVC), MySQL with JDBC and DAO, BCrypt, HttpSession, Servlet Filter.
Server: GlassFish 7 (Jakarta EE 10), JDK 17 or newer, NetBeans.

## How to run

1. Open the `SweetBake` folder in NetBeans (File > Open Project).
2. In NetBeans: Services > Databases > MySQL Server > Connect.
3. Run `SweetBake/database/schema.sql`, then `SweetBake/database/data.sql` (right click the connection > Execute Command, paste, Run SQL).
4. Open `src/main/java/com/sweetbake/util/DBConnection.java` and write your MySQL password in `PASSWORD`.
5. Right click the project > Clean and Build, then Run.
6. Open `http://localhost:8080/SweetBake/`.

## Test accounts

| Email | Password | Role |
| --- | --- | --- |
| admin@sweetbake.com | Admin@123 | ADMIN |
| baker@sweetbake.com | Baker@123 | BAKER |
| customer@sweetbake.com | Customer@123 | CUSTOMER |

## Project structure

```
SweetBake/
  database/        schema.sql, data.sql, erd.png
  src/main/java/com/sweetbake/
    util/          DBConnection
    model/         User, Category, Option, Order, OrderItem, Review
    dao/           UserDAO, MenuDAO, OrderDAO, ReviewDAO
    controller/    HomeServlet, BuilderServlet, CartServlet, MyOrdersServlet,
                   BakerServlet, AdminServlet, LoginServlet, RegisterServlet, LogoutServlet
    filter/        AuthFilter
  src/main/webapp/
    index.jsp      redirects to /home
    css/ js/ desserts/
    WEB-INF/views/ header, footer, home, builder, cart, my-orders, baker, admin, login, register
```

## Pages

| URL | Who | What it does |
| --- | --- | --- |
| /home | everyone | Most ordered dessert, latest reviews, all desserts |
| /login, /register | guests | Login and create a customer account |
| /builder?category=1 | customer | Choose options and quantity, add to cart |
| /cart | customer | Cart in the session, pickup date, notes, place order |
| /my-orders | customer | Orders and status, cancel a pending order, review a picked-up order |
| /baker | baker, admin | All orders, change the status of a whole order |
| /admin | admin | Edit dessert names, descriptions, images, option names and prices, add or remove options, add bakers |
