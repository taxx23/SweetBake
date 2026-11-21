<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@include file="header.jsp"%>

<section class="section">
    <div class="container">
        <p class="small-caps">My account</p>
        <h1 class="title">My cart</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <c:choose>
            <c:when test="${empty sessionScope.cart}">
                <div class="white-box">
                    <p class="mb-0">Your cart is empty. <a href="menu" class="link">Choose a dessert</a></p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row g-4">
                    <div class="col-lg-8">
                        <div class="white-box table-responsive">
                            <table class="table align-middle">
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
                            <a href="menu" class="link">Add more desserts</a>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <form action="cart" method="post" class="white-box summary">
                            <input type="hidden" name="action" value="checkout">
                            <p class="small-caps">Total price</p>
                            <p class="total">AED <fmt:formatNumber value="${cartTotal}" maxFractionDigits="2"/></p>

                            <label for="pickupDate" class="form-label">Pickup date</label>
                            <input type="date" id="pickupDate" name="pickupDate" min="${minDate}" class="form-control mb-3" required>

                            <label for="notes" class="form-label">Notes</label>
                            <textarea id="notes" name="notes" maxlength="255" rows="3" class="form-control mb-3"
                                      placeholder="Message on the cake or any request"></textarea>

                            <button type="submit" class="btn btn-olive w-100">Place order</button>
                        </form>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</section>

<%@include file="footer.jsp"%>
