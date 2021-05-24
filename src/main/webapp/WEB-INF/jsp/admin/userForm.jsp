<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
</head>
<body>
<center>
    <h1>User Management</h1>
    <h2>
        <a href="/user/allUsers/newUser">Add New User</a>
        &nbsp;&nbsp;&nbsp;
        <a href="/user/allUsers">List All Users</a>

    </h2>
</center>
<div align="center">
    <c:if test="${userDto == null}">
    <form action="/user/allUsers/saveUser" method="post">
        </c:if>
            <c:if test="${userDto != null}">
            <form action="/user/allUsers/update" method="post">
                </c:if>
    <table border="1" cellpadding="5">
        <caption>
            <h2>
                <c:if test="${userDto != null}">
                    Edit User
                </c:if>
                <c:if test="${userDto == null}">
                    Add New User
                </c:if>
            </h2>
        </caption>
        <c:if test="${userDto != null}">
            <input type="hidden" name="id" value="<c:out value='${userDto.id}' />" />
        </c:if>
        <tr>
            <th>User Name: </th>
            <td>
                <input type="text" name="name" size="45"
                       value="<c:out value='${userDto.name}' />"
                />
            </td>
        </tr>
        <tr>
            <th>User Email: </th>
            <td>
                <input type="email" name="email" size="45"
                       value="<c:out value='${userDto.email}' />"
                />
            </td>
        </tr>
        <tr>
            <th>Password: </th>
            <td>
                <input type="text" name="password" size="15"
                       value="<c:out value='${userDto.password}' />"
                />
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Save" />
            </td>
        </tr>
        <tr><td colspan="2">
            <c:if test="${not empty error }">
                ${error}
            </c:if>
        </td></tr>
    </table>
</form>
</div>
</body>
        </html>