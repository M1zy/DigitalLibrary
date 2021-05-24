<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div >
    <div >
        <div class="card-body">
            <form action="/Catalog/update/${bookDto.id}" method="POST" enctype = "multipart/form-data">
            <table>
                <tr>
                    <td>
                        Book name
                    </td>
                    <td style="width: 220px"><input type="text" id="name" name="name" value="${bookDto.name}" /> </td>
                </tr>
                <tr>
                    <td>
                        Book year
                    </td>
                    <td style="width: 220px"><input type="number" id="year" name="year" value="${bookDto.year}" /></td>
                </tr>
                <tr>
                    <td>
                        Book pages
                    </td>
                    <td style="width: 220px"><input type="number" id="pages" name="pages" value="${bookDto.pages}" /></td>
                </tr>
                <tr>
                    <td>
                        Book authors
                    </td>
                    <td style="width: 220px">    <select id="multiple-authors" name="authors" multiple="multiple">
                    <c:forEach items="${listAuthor}" var="author">
                        <option value="${author.id}">
                            ${author.name}
                        </option>
                    </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td>
                        Genres
                    </td>
                    <td style="width: 220px">    <select id="multiple-genres" name="genres" multiple="multiple">
                        <c:forEach items="${listGenre}" var="genre">
                            <option value="${genre.id}">
                                    ${genre.name}
                            </option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td>
                        Book description
                    </td>
                    <td style="width: 220px"><input type="text" id="description" name="description" aria-multiline="true" value="${bookDto.description}" /></td>
                </tr>
                <tr>
                    <td>
                        product image
                    </td>
                    <td style="width: 220px">         <input type = "file" name = "file" size = "50" />

                    </td>
                </tr>

                <tr>
                    <td>
                        Product pdf
                    </td>
                    <td style="width: 220px"><input type = "file" name = "pdf" size = "50" />

                    </td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align:center; font-size: 16px;" >
                        <input type="submit" value="Update book" />
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