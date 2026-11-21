<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@include file="header.jsp"%>

<section class="section">
    <div class="container">
        <p class="small-caps">My account</p>
        <h1 class="title">My orders</h1>

        <c:if test="${empty orders}">
            <div class="white-box">
                <p class="mb-0">You have no orders yet. <a href="menu" class="link">Choose a dessert</a></p>
            </div>
        </c:if>

        <c:forEach var="order" items="${orders}">
            <div class="white-box mb-3">
                <div class="d-flex justify-content-between align-items-center">
                    <h3 class="item-name mt-0">Order #${order.orderId}</h3>
                    <span class="status ${order.status}">${order.status}</span>
                </div>
                <p class="small-text">
                    Pickup: ${order.pickupDate} &middot;
                    Total: AED <fmt:formatNumber value="${order.totalPrice}" maxFractionDigits="2"/>
                </p>

                <ul>
                    <c:forEach var="item" items="${order.items}">
                        <li>${item.quantity} x ${item.categoryName}: ${item.details}</li>
                    </c:forEach>
                </ul>

                <c:if test="${not empty order.notes}">
                    <p class="small-text">Notes: <c:out value="${order.notes}"/></p>
                </c:if>

                <c:if test="${order.status == 'PENDING'}">
                    <form action="my-orders" method="post">
                        <input type="hidden" name="action" value="cancel">
                        <input type="hidden" name="orderId" value="${order.orderId}">
                        <button type="submit" class="btn btn-outline-danger btn-sm">Cancel order</button>
                    </form>
                </c:if>

                <c:if test="${order.status == 'PICKED_UP' && !order.reviewed}">
                    <form action="my-orders" method="post" class="row g-2">
                        <input type="hidden" name="action" value="review">
                        <input type="hidden" name="orderId" value="${order.orderId}">
                        <div class="col-sm-3">
                            <select name="rating" class="form-select" required>
                                <option value="5">5 stars</option>
                                <option value="4">4 stars</option>
                                <option value="3">3 stars</option>
                                <option value="2">2 stars</option>
                                <option value="1">1 star</option>
                            </select>
                        </div>
                        <div class="col-sm-6">
                            <input type="text" name="comment" maxlength="500" class="form-control" placeholder="Write a short review">
                        </div>
                        <div class="col-sm-3">
                            <button type="submit" class="btn btn-olive w-100">Send review</button>
                        </div>
                    </form>
                </c:if>

                <c:if test="${order.reviewed}">
                    <p class="small-text mb-0">Thank you for your review.</p>
                </c:if>
            </div>
        </c:forEach>
    </div>
</section>

<%@include file="footer.jsp"%>
