<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>SweetBake</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@400;600&family=Jost:wght@300;400;500&display=swap" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>

<nav class="top-nav">
    <div class="container d-flex flex-wrap justify-content-between align-items-center">
        <a href="home" class="logo">SweetBake<span>Cake Studio</span></a>
        <div class="nav-links">
            <c:choose>
                <c:when test="${sessionScope.user.role == 'CUSTOMER'}">
                    <a href="home">Home</a>
                    <a href="menu">Menu</a>
                    <a href="cart">Cart (${empty sessionScope.cart ? 0 : sessionScope.cart.size()})</a>
                    <a href="my-orders">My Orders</a>
                    <a href="logout" class="btn btn-olive btn-sm">Logout</a>
                </c:when>
                <c:when test="${sessionScope.user.role == 'BAKER'}">
                    <a href="baker">Kitchen</a>
                    <a href="logout" class="btn btn-olive btn-sm">Logout</a>
                </c:when>
                <c:when test="${sessionScope.user.role == 'ADMIN'}">
                    <a href="admin">Admin</a>
                    <a href="baker">Kitchen</a>
                    <a href="logout" class="btn btn-olive btn-sm">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="home">Home</a>
                    <a href="menu">Menu</a>
                    <a href="login">Login</a>
                    <a href="register" class="btn btn-olive btn-sm">Register</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
