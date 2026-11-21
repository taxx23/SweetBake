<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>SweetBake</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand bg-white border-bottom">
    <div class="container">
        <a class="navbar-brand" href="home">SweetBake</a>
        <div class="navbar-nav ms-auto">
            <c:choose>
                <c:when test="${sessionScope.user.role == 'CUSTOMER'}">
                    <a class="nav-link" href="home">Home</a>
                    <a class="nav-link" href="cart">Cart (${empty sessionScope.cart ? 0 : sessionScope.cart.size()})</a>
                    <a class="nav-link" href="my-orders">My Orders</a>
                    <a class="nav-link" href="logout">Logout</a>
                </c:when>
                <c:when test="${sessionScope.user.role == 'BAKER'}">
                    <a class="nav-link" href="baker">Kitchen</a>
                    <a class="nav-link" href="logout">Logout</a>
                </c:when>
                <c:when test="${sessionScope.user.role == 'ADMIN'}">
                    <a class="nav-link" href="admin">Admin</a>
                    <a class="nav-link" href="baker">Kitchen</a>
                    <a class="nav-link" href="logout">Logout</a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="home">Home</a>
                    <a class="nav-link" href="login">Login</a>
                    <a class="nav-link" href="register">Register</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<main class="container py-4">
