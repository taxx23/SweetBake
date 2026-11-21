<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@include file="header.jsp"%>

<div class="row g-4">
    <div class="col-md-5">
        <img src="${category.image}" alt="${category.name}" class="img-fluid rounded">
    </div>

    <div class="col-md-7">
        <h1>${category.name}</h1>
        <p class="text-muted">${category.description}</p>

        <form action="cart" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="categoryId" value="${category.categoryId}">

            <c:forEach var="group" items="${groups}">
                <h6 class="mt-3">${group.key}</h6>
                <c:forEach var="option" items="${group.value}" varStatus="status">
                    <div class="form-check">
                        <c:choose>
                            <c:when test="${group.key == 'TOPPING'}">
                                <input class="form-check-input" type="checkbox" name="TOPPING"
                                       id="option${option.optionId}" value="${option.optionId}"
                                       data-price="${option.price}" onchange="updatePrice()">
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-input" type="radio" name="${group.key}"
                                       id="option${option.optionId}" value="${option.optionId}"
                                       data-price="${option.price}" onchange="updatePrice()"
                                       <c:if test="${status.first}">checked</c:if>>
                            </c:otherwise>
                        </c:choose>
                        <label class="form-check-label" for="option${option.optionId}">
                            ${option.name}
                            <c:if test="${option.price > 0}">
                                <span class="text-muted">(AED <fmt:formatNumber value="${option.price}" maxFractionDigits="2"/>)</span>
                            </c:if>
                        </label>
                    </div>
                </c:forEach>
            </c:forEach>

            <label for="quantity" class="form-label mt-3">Quantity</label>
            <input type="number" id="quantity" name="quantity" value="1" min="1" max="20"
                   class="form-control quantity-input" onchange="updatePrice()" required>

            <p class="total mt-3">Total: AED <span id="total">0</span></p>

            <button type="submit" class="btn btn-olive">Add to cart</button>
            <a href="home" class="btn btn-link">Back</a>
        </form>
    </div>
</div>

<%@include file="footer.jsp"%>
