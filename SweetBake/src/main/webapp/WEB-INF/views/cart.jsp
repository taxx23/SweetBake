<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@include file="header.jsp"%>

<h1>My cart</h1>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<c:choose>
    <c:when test="${empty sessionScope.cart}">
        <p>Your cart is empty. <a href="home">Choose a dessert</a></p>
    </c:when>
    <c:otherwise>
        <table class="table bg-white align-middle">
            <tr>
                <th>Dessert</th>
                <th>Details</th>
                <th>Qty</th>
                <th>Price</th>
                <th></th>
            </tr>
            <c:forEach var="item" items="${sessionScope.cart}" varStatus="status">
                <tr>
                    <td>${item.categoryName}</td>
                    <td>${item.details}</td>
                    <td>${item.quantity}</td>
                    <td>AED <fmt:formatNumber value="${item.total}" maxFractionDigits="2"/></td>
                    <td>
                        <form action="cart" method="post">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="index" value="${status.index}">
                            <button type="submit" class="btn btn-outline-danger btn-sm">Remove</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <p class="total">Total: AED <fmt:formatNumber value="${cartTotal}" maxFractionDigits="2"/></p>

        <form action="cart" method="post" class="form-box">
            <input type="hidden" name="action" value="checkout">

            <label for="pickupDate" class="form-label">Pickup date</label>
            <input type="date" id="pickupDate" name="pickupDate" min="${minDate}" class="form-control mb-3" required>

            <label for="notes" class="form-label">Notes (message on the cake, requests)</label>
            <textarea id="notes" name="notes" maxlength="255" rows="3" class="form-control mb-3"></textarea>

            <button type="submit" class="btn btn-olive">Place order</button>
            <a href="home" class="btn btn-link">Add more desserts</a>
        </form>
    </c:otherwise>
</c:choose>

<%@include file="footer.jsp"%>
