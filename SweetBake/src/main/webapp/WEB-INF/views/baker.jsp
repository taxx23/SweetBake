<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@include file="header.jsp"%>

<h1>Kitchen</h1>

<table class="table bg-white align-middle">
    <tr>
        <th>Order</th>
        <th>Customer</th>
        <th>Items</th>
        <th>Pickup</th>
        <th>Notes</th>
        <th>Status</th>
        <th>Change status</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>#${order.orderId}</td>
            <td><c:out value="${order.customerName}"/></td>
            <td>
                <c:forEach var="item" items="${order.items}">
                    <div>${item.quantity} x ${item.categoryName}: ${item.details}</div>
                </c:forEach>
            </td>
            <td>${order.pickupDate}</td>
            <td><c:out value="${order.notes}"/></td>
            <td><span class="status ${order.status}">${order.status}</span></td>
            <td>
                <c:if test="${order.status != 'CANCELLED'}">
                    <form action="baker" method="post" class="d-flex gap-2">
                        <input type="hidden" name="orderId" value="${order.orderId}">
                        <select name="status" class="form-select form-select-sm">
                            <option value="ACCEPTED">ACCEPTED</option>
                            <option value="REJECTED">REJECTED</option>
                            <option value="BAKING">BAKING</option>
                            <option value="READY">READY</option>
                            <option value="PICKED_UP">PICKED_UP</option>
                        </select>
                        <button type="submit" class="btn btn-olive btn-sm">Save</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<%@include file="footer.jsp"%>
