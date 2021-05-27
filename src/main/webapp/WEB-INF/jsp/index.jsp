<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Online Library</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <link href="<c:url value="../../resources/css/style.css" />" rel="stylesheet">
    <style type="text/css">
        body{
            background: #c7b39b url(../../resources/logincss/prism.png);
        }
    </style>
</head>
<body>
    <div class="shell">
        <div id="header">
            <a id="logo" href="/"  >
                <img src="../../resources/css/images/Library.png" height="64" width="300" alt=""   />
            </a>
            <div id="cart"> <a style="font-size:18px;margin: 30px; " href="/user/account" class="cart-link">  Your account</a>
            </div>

            <div id="navigation">
                <ul>
                    <li><a href="/" class="active">Home</a></li>

                    <li><a href="/user/AdminPage">Admin</a></li>
                    <li><a href="/user/registration">Registration</a></li>
                    <%
                        if(session.getAttribute("user")==null)
                        {
                    %>
                    <li><a href="/user/login">Login</a></li>

                    <%
                    }
                    else
                    {%>
                    <li><a href="/user/logoutUser">logout</a></li>
                    <%

                        }
                    %>

                </ul>
            </div>
        </div>
        <div id="main">
            <div class="cl">&nbsp;</div>
            <div id="content">
                <img src="../../resources/css/images/elibrary.jpg" width="720" height="252" alt="" />
                <div class="books">
                    <c:if test="${mode == 'Mode_home'||mode==null}">
                        <jsp:include page="user/Books.jsp" />
                    </c:if>
                    <c:if test="${mode == 'Mode_registration' && user == null}">
                        <jsp:include page="user/registration.jsp" />
                    </c:if>
                    <c:if test="${mode == 'Mode_login' && user == null}">
                        <jsp:include page="user/login.jsp" />
                    </c:if>
                    <c:if test="${mode == 'Mode_book'}">
                        <jsp:include page="user/Product_desc.jsp" />
                    </c:if>
                    <c:if test="${mode == 'Mode_reviews'}">
                        <jsp:include page="user/Reviews.jsp" />
                    </c:if>
                    <c:if test="${mode == 'Mode_user'}">
                        <jsp:include page="user/Account.jsp" />
                    </c:if>
                    <c:if test="${mode == 'Mode_error'}">
                        <jsp:include page="error.jsp" />
                    </c:if>
                    <c:if test="${mode == 'Mode_archive'}">
                        <jsp:include page="user/Catalogue.jsp" />
                    </c:if>
                </div>
            </div>

            <div id="sidebar">
                <div class="box search" style="background:#2e4519;">
                    <h2 style="font-family:CountrySide,serif; font-size:20px; color:orange;">Search by </h2>
                    <div class="box-content">

                        <input type="text" class="field" id ="searchtextbox" />

                        <input type="button" class="search-submit" value="Search" onclick="search()" style="font-size:13px;" />
                        <p class="col-1" style="color: black; background-color: darkslategrey;"> please enter item name inside the textbox</p>
                    </div>
                </div>

                <div class="box categories" style="border: 1px solid #232323;">
                    <c:if test="${category == false}">
                        <div style="background-color: white">
                        <a href="/switchCategory">Switch category</a>
                        </div>
                    <h2 style="font-family:CountrySide,serif; font-size:23px; color:orange;">Book authors </h2>
                    <div class="box-content">
                        <ul>

                            <c:forEach items="${listAuthors}" var="author">
                                <li><a style="font-family:CountrySide,serif; font-size:18px;" href="/searchByAuthor/${author.id}">${author.name}</a></li>
                            </c:forEach>

                        </ul>
                    </div>
                    </c:if>
                    <c:if test="${category == true}">
                    <div style="background-color: white">
                        <a href="/switchCategory">Switch category</a>
                    </div>
                        <h2 style="font-family:CountrySide,serif; font-size:23px; color:orange;">Book genres</h2>
                        <div class="box-content">
                            <ul>

                                <c:forEach items="${listGenres}" var="genre">
                                    <li><a style="font-family:CountrySide,serif; font-size:18px;" href="/searchByGenre/${genre.id}">${genre.name}</a></li>
                                </c:forEach>

                            </ul>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="cl">&nbsp;</div>
        </div>
        <div >

            <div class="cols" style="background-color: dimgrey; color:black; font-size:15px;">
                <div class="cl">&nbsp;</div>
                <div class="col">
                    <h3 class="ico ico1" style="font-family:CountrySide,serif; font-size:18px;">emails</h3>
                    <p>If you have any questions, please write me </p>
                    <p class="more"><a href="http://slabko-12@mail.ru" class="bul">slabko-12@mail.ru</a></p>
                    <p class="more"><a href="http://Kirilslabko123@gmail.com" class="bul">Kirilslabko123@Gmail.com</a></p>
                </div>
                <div class="col">
                    <h3 class="ico ico2" style="font-family:CountrySide,serif; font-size:18px;">Additional</h3>
                    <p>Additional means of communication</p>
                    <p class="more"><a href="https://vk.com/assassindark" class="bul">VK</a></p>
                </div>

            </div>
        </div>

        <div id="footer" style="font-family:CountrySide,serif; font-size:18px; color:black; ">
            <p class="right"> &copy; 2020 Digital Library. Designed by Kirill Slabko </p>
        </div>


    </div>


<script type="text/javascript">
    function search() {
        window.location = "/search/" + document.getElementById("searchtextbox").value;

    }
</script>
</body>

</html>