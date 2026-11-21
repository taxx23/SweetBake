<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@include file="header.jsp"%>

<section class="section">
    <div class="container">
        <p class="small-caps">Design your order</p>
        <h1 class="title">${category.name}</h1>

        <form action="cart" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="categoryId" value="${category.categoryId}">

            <div class="row g-4 mt-2">
                <div class="col-lg-8">
                    <div class="white-box">
                        <c:forEach var="group" items="${groups}">
                            <p class="form-label mt-2">${group.key}</p>
                            <c:forEach var="option" items="${group.value}" varStatus="status">
                                <label class="check">
                                    <c:choose>
                                        <c:when test="${group.key == 'TOPPING'}">
                                            <input type="checkbox" name="TOPPING" value="${option.optionId}"
                                                   data-price="${option.price}" onchange="updatePrice()">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="radio" name="${group.key}" value="${option.optionId}"
                                                   data-price="${option.price}" onchange="updatePrice()"
                                                   <c:if test="${status.first}">checked</c:if>>
                                        </c:otherwise>
                                    </c:choose>
                                    ${option.name}
                                    <c:if test="${option.price > 0}">
                                        (AED <fmt:formatNumber value="${option.price}" maxFractionDigits="2"/>)
                                    </c:if>
                                </label>
                            </c:forEach>
                        </c:forEach>

                        <label for="quantity" class="form-label mt-3">Quantity</label>
                        <input type="number" id="quantity" name="quantity" value="1" min="1" max="20"
                               class="form-control quantity-input" onchange="updatePrice()" required>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="white-box summary">
                        <img src="${category.image}" alt="${category.name}" class="card-photo mb-3">
                        <p class="small-caps">Total price</p>
                        <p class="total">AED <span id="total">0</span></p>
                        <button type="submit" class="btn btn-olive w-100">Add to cart</button>
                        <a href="menu" class="link d-block text-center mt-3">Back to menu</a>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>

<%@include file="footer.jsp"%>
