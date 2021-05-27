<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div >
    <div >
        <div class="card-body">
            <form action="/Catalog/update/${bookDto.id}" method="POST" enctype = "multipart/form-data">
            <table>
                <tr>
                    <td class="col-1">
                        Book name
                    </td>
                    <td style="width: 220px"><input class="col-2" type="text" id="name" name="name" value="${bookDto.name}" /> </td>
                </tr>
                <tr>
                    <td class="col-1">
                        Book year
                    </td>
                    <td style="width: 220px"><input class="col-2" type="number" id="year" name="year" value="${bookDto.year}" /></td>
                </tr>
                <tr>
                    <td class="col-1">
                        Book pages
                    </td>
                    <td style="width: 220px"><input class="col-2" type="number" id="pages" name="pages" value="${bookDto.pages}" /></td>
                </tr>
                <tr>
                    <td class="col-1">
                        Book authors
                    </td>
                    <td style="width: 220px">    <select class="col-2" id="multiple-authors" name="authors" multiple="multiple">
                    <c:forEach items="${listAuthor}" var="author">
                        <option value="${author.id}">
                            ${author.name}
                        </option>
                    </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td class="col-1">
                        Genres
                    </td>
                    <td style="width: 220px">    <select class="col-2" id="multiple-genres" name="genres" multiple="multiple">
                        <c:forEach items="${listGenre}" var="genre">
                            <option value="${genre.id}">
                                    ${genre.name}
                            </option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td class="col-1">
                        Book description
                    </td>
                    <td style="width: 220px"><input class="col-2" type="text" id="description" name="description" aria-multiline="true" size="30" value="${bookDto.description}" /></td>
                </tr>
                <tr>
                    <td class="col-1">
                        product image
                    </td>
                    <td style="width: 220px">         <input class="button-3" type = "file" name = "file" size = "50" />

                    </td>
                </tr>

                <tr>
                    <td class="col-1">
                        Product pdf
                    </td>
                    <td style="width: 220px"><input class="button-3" type = "file" name = "pdf" size = "50" />

                    </td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align:center; font-size: 16px;"  >
                        <button class="button-3" type="submit" value="Update book">Update book</button>
                    </td>

                </tr>
                <tr>
                    <td colspan="2" style="text-align: left; font-size: 18px; padding: 20px;">
                        <a href="/Catalog/delete/${bookDto.id}" style="padding-right: 20px;">Delete book</a>
                        <a href="/reviews/show/${book.id}">Reviews</a>
                    </td>
                </tr>

            </table>
            </form>
        </div>
    </div>
</div>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<c:if test="${not empty error}">
    ${error}
</c:if>
<br />