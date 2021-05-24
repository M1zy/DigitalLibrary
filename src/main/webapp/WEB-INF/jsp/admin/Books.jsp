<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<p id="head">
    <b id="Itemshead" style="font-family:Impact,serif; font-size:27px; color:black;">  Book Catalog
    </b>
</p>
<c:forEach items="${listBook}" var="book" >
    <ul>
        <li class="last" style="margin:3px;">
            <a href="redactingBook/${book.id}"><img src="../../../resources/BookFiles/${book.image}" width="252" height="300" alt="" /></a>
            <div  class="product-info">
                <h3 >${book.name} (${book.year})</h3>
                <div  class="product-desc">
                    <p >${book.description}</p>
                </div>
            </div>
        </li>
    </ul>
</c:forEach>
<ul style="padding-inline: 100px; font-size: 30px;">
    <c:if test="${currentPage gt 1}">
        <a href="/Catalog/${currentPage - 1}">Previous</a>
    </c:if>
    <c:forEach begin="1" end="${nOfPages}" var="i">
        <c:choose>
            <c:when test="${i!=currentPage}">
                <a href="/Catalog/<c:out value="${i}"/>"><c:out value="${i}"/></a>
            </c:when>
            <c:otherwise>
                <c:out value="${i}"/>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <%--For displaying Next link --%>
    <c:if test="${currentPage < nOfPages}">
        <a href="/Catalog/${currentPage + 1}">Next</a>
    </c:if>
</ul>