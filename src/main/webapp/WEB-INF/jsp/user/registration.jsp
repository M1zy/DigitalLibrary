<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<p id="head">
        <b id="Itemshead">  Registration
        </b>
    </p>
 <form action="/user/registerUser" method="POST" >
    <table style="width:400px; height:400px;">
        <tr>

            <td>FullName</td>
            <td style="width: 363px">
                <input type="text" id="fullname" name="name" value="${userDto.name}" />
            </td>
        </tr>
        <tr>

            <td>email</td>
            <td style="width: 363px">
                <input type="email" id="email" name="email" value="${userDto.email}" />
            </td>
        </tr>
        <tr>

            <td>password</td>
            <td style="width: 363px">
                <input type="password" id="password" name="password" value="${userDto.password}" />
            </td>
        </tr>

        <tr>

            <td>confirm password</td>
            <td style="width: 363px">
                <input type="password" id="password2" name="conpassword" value="" />
            </td>
        </tr>
        <tr>

            <td colspan="2" align="center">
                <input type="submit" value="Register" />
            </td>
        </tr>
        <tr>

            <td colspan="2" align="center" style="height: 18px">
                <c:if test="${not empty error }">
                ${error}
                </c:if>
            </td>
        </tr>
    </table>
 </form>