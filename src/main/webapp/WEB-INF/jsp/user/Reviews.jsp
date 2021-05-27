<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <p id="head">
        <b id="Itemshead">  Reviews
        </b>
    </p>
<form action="/reviews/addBookReview" method="POST" >
    <table>

        <tr>
            <td class="col-1">
                Comment
            </td>
            <td style="width: 220px"><input class="col-2" type="text" multiple="multiple" id="text" name="text" value="${text}" /></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center; height: 36px;" >
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button class="button-3" type="submit" value="Send review">Send review</button>
            </td>

        </tr>

                <ul class="Reviews">
    <c:forEach items="${listReview}" var="review">
                <div style="margin:10px;">
                    <a class="col-1" style="margin-right: 20px;">
                            ${review.user.name}
                    </a>
                    <a class="col-2"">
                           ${review.text}
                    </a>
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