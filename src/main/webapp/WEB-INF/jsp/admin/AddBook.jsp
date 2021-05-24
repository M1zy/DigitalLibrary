<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="/addingBook" method="POST" enctype = "multipart/form-data">
    <table>
        <tr>
            <td>
                Book name
            </td>
            <td style="width: 220px"><input type="text" id="name" name="name" value="${bookDto.name}" /></td>
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
            <td colspan="2" style="text-align:center;" >
                <input type="submit" value="Add book" />
            </td>

        </tr>

    </table>
    <c:if test="${not empty error}">
        ${error}
    </c:if>
</form>