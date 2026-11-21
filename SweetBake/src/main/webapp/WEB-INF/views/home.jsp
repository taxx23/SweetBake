<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@include file="header.jsp"%>

<c:if test="${not empty topCategory}">
    <section class="row align-items-center g-4 mb-5">
        <div class="col-md-6">
            <img src="${topCategory.image}" alt="${topCategory.name}" class="img-fluid rounded top-image">
        </div>
        <div class="col-md-6">
            <p class="text-muted text-uppercase small mb-1">Most ordered right now</p>
            <h1>${topCategory.name}</h1>
            <p>${topCategory.description}</p>
            <a href="builder?category=${topCategory.categoryId}" class="btn btn-olive">Order now</a>
        </div>
    </section>
</c:if>

<c:if test="${not empty reviews}">
    <section class="mb-5">
        <h2 class="mb-3">What our customers say</h2>
        <div class="row g-3">
            <c:forEach var="review" items="${reviews}">
                <div class="col-md-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <p class="stars mb-2">
                                <c:forEach begin="1" end="${review.rating}">&#9733;</c:forEach>
                            </p>
                            <p><c:out value="${review.comment}"/></p>
                            <p class="text-muted small mb-0"><c:out value="${review.customerName}"/></p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </section>
</c:if>

<section>
    <h2 class="mb-3">Our desserts</h2>
    <div class="row g-4">
        <c:forEach var="category" items="${categories}">
            <div class="col-sm-6 col-lg-4">
                <div class="card h-100">
                    <img src="${category.image}" alt="${category.name}" class="card-img-top dessert-image">
                    <div class="card-body">
                        <h5>${category.name}</h5>
                        <p class="text-muted">${category.description}</p>
                        <a href="builder?category=${category.categoryId}" class="btn btn-olive btn-sm">Order</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</section>

<%@include file="footer.jsp"%>
