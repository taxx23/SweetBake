<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@include file="header.jsp"%>

<h1>Admin</h1>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<h2 class="mt-4">Desserts</h2>
<c:forEach var="category" items="${categories}">
    <form action="admin" method="post" class="row g-2 mb-2">
        <input type="hidden" name="action" value="updateCategory">
        <input type="hidden" name="categoryId" value="${category.categoryId}">
        <div class="col-md-3">
            <input type="text" name="name" value="${category.name}" class="form-control" required>
        </div>
        <div class="col-md-4">
            <input type="text" name="description" value="${category.description}" class="form-control">
        </div>
        <div class="col-md-3">
            <input type="text" name="image" value="${category.image}" class="form-control">
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-olive w-100">Save</button>
        </div>
    </form>
</c:forEach>

<h2 class="mt-4">Options and prices</h2>
<c:forEach var="category" items="${categories}">
    <h5 class="mt-3">${category.name}</h5>
    <c:forEach var="option" items="${options}">
        <c:if test="${option.categoryId == category.categoryId}">
            <div class="d-flex gap-2 mb-2">
                <form action="admin" method="post" class="d-flex gap-2 flex-grow-1">
                    <input type="hidden" name="action" value="updateOption">
                    <input type="hidden" name="optionId" value="${option.optionId}">
                    <span class="option-type">${option.optionType}</span>
                    <input type="text" name="name" value="${option.name}" class="form-control" required>
                    <input type="number" name="price" value="${option.price}" min="0" step="0.01" class="form-control price-input" required>
                    <button type="submit" class="btn btn-olive btn-sm">Save</button>
                </form>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="removeOption">
                    <input type="hidden" name="optionId" value="${option.optionId}">
                    <button type="submit" class="btn btn-outline-danger btn-sm">Remove</button>
                </form>
            </div>
        </c:if>
    </c:forEach>
</c:forEach>

<div class="row g-4 mt-2">
    <div class="col-md-6">
        <h2>Add option</h2>
        <form action="admin" method="post" class="form-box">
            <input type="hidden" name="action" value="addOption">
            <label class="form-label">Dessert</label>
            <select name="categoryId" class="form-select mb-2">
                <c:forEach var="category" items="${categories}">
                    <option value="${category.categoryId}">${category.name}</option>
                </c:forEach>
            </select>
            <label class="form-label">Type</label>
            <select name="optionType" class="form-select mb-2">
                <option>SIZE</option>
                <option>FLAVOR</option>
                <option>FILLING</option>
                <option>FROSTING</option>
                <option>SAUCE</option>
                <option>TOPPING</option>
            </select>
            <label class="form-label">Name</label>
            <input type="text" name="name" class="form-control mb-2" required>
            <label class="form-label">Price (AED)</label>
            <input type="number" name="price" min="0" step="0.01" class="form-control mb-3" required>
            <button type="submit" class="btn btn-olive">Add option</button>
        </form>
    </div>

    <div class="col-md-6">
        <h2>Add baker</h2>
        <form action="admin" method="post" class="form-box">
            <input type="hidden" name="action" value="addBaker">
            <label class="form-label">Full name</label>
            <input type="text" name="fullName" class="form-control mb-2" required>
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control mb-2" required>
            <label class="form-label">Phone</label>
            <input type="text" name="phone" class="form-control mb-2">
            <label class="form-label">Password</label>
            <input type="password" name="password" minlength="8" class="form-control mb-3" required>
            <button type="submit" class="btn btn-olive">Add baker</button>
        </form>
    </div>
</div>

<%@include file="footer.jsp"%>
