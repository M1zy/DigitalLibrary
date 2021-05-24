<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://www.google.com/recaptcha/api.js"></script>
<form action="loginUser" method="POST">
    <table style="font-size:16px;">
        <tr>
            <td>Enter Email:</td>
            <td>
                <input type="email" id="email" name="email" value="${userDto.email}" />
            </td>
        </tr>
        <tr>
            <td style="height: 44px">Enter password:</td>
            <td style="height: 44px">
                <input type="password" id="password" name="password" value="${userDto.password}" />
            </td>
        </tr>
        <tr>
            <td>
                <div class="g-recaptcha"
                     data-sitekey="6LfzXtcaAAAAANyH86ryal_F2lyjCe69hrQ8bWsJ"></div>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Login" />
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <c:if test="${not empty error}">
                        ${error}
                </c:if>
            </td>
        </tr>
    </table>
</form>