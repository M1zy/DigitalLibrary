<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Simple Responsive Admin</title>


    <!-- CUSTOM STYLES-->
    <link href="../../../resources/assets/css/custom.css" rel="stylesheet" />
    <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    <style>
        body{
            background: #c7b39b url(../../../resources/logincss/prism.png);
        }
    </style>
</head>
<body>
<div class="shell">
    <div id="wrapper">
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="adjust-nav">
                <div class="navbar-header">

                    <a class="navbar-brand" href="/user/AdminPage">
                        <img src="../../../resources/logincss/logo.png" />

                    </a>
                    <span class="logout-spn" >
                  <a href="/user/logoutUser" style="color:red; margin-top:100px;">LOGOUT</a>
                </span>
                </div>


            </div>
        </div>
        <!-- /. NAV TOP  -->
        <div class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse" ">
                <a href="/" style="color:red; margin-top:100px;">Back to library</a>
                <h3 class="menu-title" style="font-family:Forte fantasy; font-size:20px; color:orange;">Books-menu</h3>
                <ul class="nav" id="main-menu" style="color:orange;">


                    <li class="active-link">
                        <a href="/Catalog/add" >Add book </a>
                    </li>


                    <li >
                        <a href="/Catalog/" >Books </a>
                    </li>
                    <li >
                        <a href="/Catalog/VerificationBooks" >Books requests </a>
                    </li>




                </ul>

                <h3 class="menu-title" style="font-family:Forte fantasy; font-size:20px; color:orange;">Users-menu</h3>
                <ul class="nav" >

                    <li >
                        <a href="/user/allUsers" >Users </a>
                    </li>
                    <li >
                        <a href="Comments.aspx" >Comments </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-lg-12">
                        <h2 style="font-family:Forte fantasy;text-align:center; font-size:25px; color:orange;" id="admin_header">ADMIN PANEL</h2>
                    </div>
                </div>

                <!-- /. ROW  -->
                <hr />

                <!--content Area-->
                <div id="main">
                    <div class="cl">&nbsp;</div>
                    <div id="content">
                <div class="books">
                <c:if test="${mode == 'Mode_Home' || mode == null}">
                    <jsp:include page="../admin/Home.jsp" />
                </c:if>

                <c:if test="${mode == 'Mode_adminAddBook'}">
                    <jsp:include page="../admin/AddBook.jsp" />
                </c:if>

                <c:if test="${mode == 'Mode_adminBooks'}">
                    <jsp:include page="../admin/Books.jsp" />
                </c:if>

                <c:if test="${mode == 'Mode_redactingBook'}">
                    <jsp:include page="../admin/RedactingBook.jsp" />
                </c:if>

                    <c:if test="${mode == 'Mode_allUsers'}">
                        <jsp:include page="../admin/AllUsers.jsp" />
                    </c:if>

                    <c:if test="${mode == 'Mode_newUser'}">
                        <jsp:include page="../admin/userForm.jsp" />
                    </c:if>

                    <c:if test="${mode == 'Mode_notVerifiedBooks'}">
                        <jsp:include page="../admin/Books.jsp" />
                    </c:if>

                    <c:if test="${mode == 'Mode_verificationBook'}">
                        <jsp:include page="../admin/VerificationBooks.jsp" />
                    </c:if>
                </div>
                    </div>
                </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
    </div>
</div>
</body>
</html>
