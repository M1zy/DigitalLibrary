
<h1>Error</h1>
<c:if test="${error}">
    ${error}
</c:if>
<c:forEach items="${details}" var="detail">
    ${detail}
</c:forEach>
