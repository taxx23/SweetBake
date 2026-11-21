<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@include file="header.jsp"%>

<section class="section">
    <div class="container">
        <p class="small-caps">Our menu</p>
        <h1 class="title">Choose your dessert</h1>

        <div class="row g-4 mt-2">
            <c:forEach var="category" items="${categories}">
                <div class="col-md-6 col-lg-4">
                    <div class="menu-card">
                        <img src="${category.image}" alt="${category.name}" class="card-photo">
                        <h3 class="item-name">${category.name}</h3>
                        <p>${category.description}</p>
                        <a href="builder?category=${category.categoryId}" class="btn btn-olive">Order</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>

<%@include file="footer.jsp"%>
