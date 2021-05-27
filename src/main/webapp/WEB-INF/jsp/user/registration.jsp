<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<p id="head">
        <b id="Itemshead">  Registration
        </b>
    </p>
 <form action="/user/registerUser" method="POST" >
    <table style="width:500px; height:400px; font-size: 18px;">
        <tr>

            <td class="col-1">FullName</td>
            <td style="width: 363px">
                <input class="col-2" type="text" id="fullname" name="name" size="30" placeholder="FullName" value="${userDto.name}" />
            </td>
        </tr>
        <tr>

            <td class="col-1">Email</td>
            <td  style="width: 363px">
                <input class="col-2" type="email" id="email" size="30" name="email" placeholder="Email" value="${userDto.email}" />
            </td>
        </tr>
        <tr>

            <td class="col-1">Password</td>
            <td style="width: 363px">
                <input class="col-2" type="password" id="password" size="30" name="password" placeholder="password" value="${userDto.password}" />
            </td>
        </tr>

        <tr>

            <td class="col-1">Confirm password</td>
            <td style="width: 363px">
                <input class="col-2" type="password" size="30" id="password2" name="conpassword" placeholder="Confirm password" value="" />
            </td>
        </tr>
        <tr>

            <td colspan="2" align="center">
                <button class="button-3" type="submit">Register</button>
            </td>
        </tr>
        <tr>

            <td colspan="2" align="center" style="height: 18px; color: red;">
                <c:if test="${not empty error }">
                ${error}
                </c:if>
            </td>
        </tr>
    </table>
 </form>