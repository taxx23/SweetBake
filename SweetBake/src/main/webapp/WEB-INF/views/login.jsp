<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@include file="header.jsp"%>

<section class="section">
    <div class="container">
        <div class="form-box">
            <p class="small-caps">My account</p>
            <h1 class="title">Login</h1>

            <c:if test="${param.registered == 'yes'}">
                <div class="alert alert-success">Account created. You can login now.</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form action="login" method="post">
                <label for="email" class="form-label">Email</label>
                <input type="email" id="email" name="email" class="form-control mb-3" required>

                <label for="password" class="form-label">Password</label>
                <input type="password" id="password" name="password" class="form-control mb-4" required>

                <button type="submit" class="btn btn-olive w-100">Login</button>
            </form>

            <p class="mt-3 mb-0">Don't have an account? <a href="register" class="link">Register</a></p>
        </div>
    </div>
</section>

<%@include file="footer.jsp"%>
