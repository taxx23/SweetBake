<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@include file="header.jsp"%>

<section class="hero">
    <div class="container">
        <h1 class="big-word">SweetBake</h1>
        <div class="row align-items-center g-4">
            <div class="col-md-5">
                <h2 class="title">Cakes that turn your moments into art</h2>
                <p class="small-caps">Natural ingredients<br>Our own recipes<br>Beauty in every detail</p>
                <a href="menu" class="btn btn-olive">Design your cake</a>
                <a href="menu" class="link ms-3">View menu</a>
            </div>
            <div class="col-md-7">
                <c:if test="${not empty topCategory}">
                    <img src="${topCategory.image}" alt="${topCategory.name}" class="photo">
                    <p class="small-caps text-center mt-3 mb-1">Most ordered right now</p>
                    <h3 class="item-name text-center mt-0">
                        <a href="builder?category=${topCategory.categoryId}" class="plain-link">${topCategory.name}</a>
                    </h3>
                </c:if>
            </div>
        </div>
    </div>
</section>

<section class="about">
    <div class="row g-0">
        <div class="col-md-5 about-text">
            <p class="small-caps">About the studio</p>
            <h2 class="title">Inspired by the taste of life</h2>
            <p>SweetBake is a cake studio where natural ingredients and attention to detail create desserts you remember.</p>
        </div>
        <div class="col-md-7">
            <img src="desserts/cakes.jpg" alt="Cake" class="photo-full">
        </div>
    </div>
</section>

<c:if test="${not empty reviews}">
    <section class="section">
        <div class="container">
            <p class="small-caps">Reviews</p>
            <h2 class="title">What our customers say</h2>
            <div class="row g-4 mt-1">
                <c:forEach var="review" items="${reviews}">
                    <div class="col-md-4">
                        <div class="white-box h-100">
                            <p class="stars">
                                <c:forEach begin="1" end="${review.rating}">&#9733;</c:forEach>
                            </p>
                            <p><c:out value="${review.comment}"/></p>
                            <p class="small-caps mb-0"><c:out value="${review.customerName}"/></p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
</c:if>

<section class="green-band">
    <div class="container d-flex flex-wrap justify-content-between align-items-center gap-3">
        <h2 class="title mb-0">Let us make your moment special</h2>
        <a href="menu" class="btn btn-light-outline">Start your order</a>
    </div>
</section>

<%@include file="footer.jsp"%>
