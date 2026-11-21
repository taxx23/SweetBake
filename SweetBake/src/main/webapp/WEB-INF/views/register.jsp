<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@include file="header.jsp"%>

<div class="form-box small-box">
    <h1>Register</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <p id="formError" class="text-danger"></p>

    <form action="register" method="post" onsubmit="return checkRegister()">
        <label for="fullName" class="form-label">Full name</label>
        <input type="text" id="fullName" name="fullName" class="form-control mb-3" required>

        <label for="email" class="form-label">Email</label>
        <input type="email" id="email" name="email" class="form-control mb-3" required>

        <label for="phone" class="form-label">Phone</label>
        <input type="text" id="phone" name="phone" placeholder="05XXXXXXXX" class="form-control mb-3" required>

        <label for="password" class="form-label">Password</label>
        <input type="password" id="password" name="password" class="form-control mb-3" required>

        <label for="confirmPassword" class="form-label">Confirm password</label>
        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control mb-3" required>

        <button type="submit" class="btn btn-olive w-100">Create account</button>
    </form>

    <p class="mt-3 mb-0">Already have an account? <a href="login">Login</a></p>
</div>

<%@include file="footer.jsp"%>
