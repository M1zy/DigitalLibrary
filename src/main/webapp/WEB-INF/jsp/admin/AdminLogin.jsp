<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
    <link rel="stylesheet" href="../../../resources/logincss/style1.css" >

    <style>body
    {
        background: #c7b39b url(../../../resources/logincss/prism.png);
    }
    </style>
</head>
<body>
<form action="/user/loginAdmin" method="POST" class="form-2">
    <h1><span class="log-in">Login</span> and <span class="sign-up">Welcome</span></h1>
    <p class="float">
        <label for="email"><i class="icon-user"></i>Email</label>
        <input type="text" id="email" name="email" value="${userDto.email}" />
    </p>

    <p class="float">
        <label for="password"><i class="icon-lock"></i>Password</label>
        <input type="password" id="password" name="password" value="${userDto.password}" />
    </p>
    <p class="clearfix">
        <a href="/" class="log-twitter">Back to the main menu</a>
        <input type="submit" value="Login" />
    </p>
    <a>
        <c:if test="${not empty error}">
            ${error}
        </c:if>
    </a>

</form>




</body>
</html>
