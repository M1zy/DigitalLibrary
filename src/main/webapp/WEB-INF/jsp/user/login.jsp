<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://www.google.com/recaptcha/api.js"></script>
<p id="head">
    <b id="Itemshead">  Authorization
    </b>
</p>
<form action="loginUser" method="POST">
    <table style="font-size:16px;">
        <tr>
            <td class="col-1">Enter email:</td>
            <td>
                <input class="col-2" type="email" id="email" name="email" value="${userDto.email}" />
            </td>
        </tr>
        <tr>
            <td class="col-1" style="height: 44px">Enter password:</td>
            <td style="height: 44px">
                <input class="col-2" type="password" id="password" name="password" value="${userDto.password}" />
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
                <button type="submit" class="button-3">Login</button>
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