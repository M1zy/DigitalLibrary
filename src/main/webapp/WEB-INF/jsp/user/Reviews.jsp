<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <p id="head">
        <b id="Itemshead">  Reviews
        </b>
    </p>
<form action="/reviews/addBookReview" method="POST" >
    <table>

        <tr>
            <td>
                Comment
            </td>
            <td style="width: 220px"><input type="text" multiple="multiple" id="text" name="text" value="${text}" /></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center; height: 36px;" >
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="submit" value="Send review" />
            </td>

        </tr>

                <ul class="Reviews">
    <c:forEach items="${listReview}" var="review">
                <div style="margin:10px;">

                           ${review.user.email}
                    &nbsp;&nbsp;
                           ${review.text}

                </div>
    </c:forEach>
                </ul>

    </table>
    <c:if test="${not empty error }">
        ${error}
    </c:if>
    <c:if test="${not empty finder }">
        ${finder}
    </c:if>
</form>