<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Sve kategorije</title>
        <%@include file="header.jsp" %>

    </head>
    <body>
        <sec:authentication var="admin" property="principal"/>
        <div id="wrapper">
            <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/cms/pocetna">CMS</a>
                </div>

                <div class="header-right">
                    <form role="form" action="/cms/logout" method="POST">
                        <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
                        <label for="mySubmit" class="btn"><i class="fa fa-sign-out fa-2x"></i></label>
                        <input id="mySubmit" type="submit" value="" class="hidden" />
                    </form>
                </div>
            </nav>

            <nav class="navbar-default navbar-side" role="navigation">
                <div class="sidebar-collapse">
                    <ul class="nav" id="main-menu">
                        <li>
                            <div class="user-img-div">
                                <div class="inner-text">
                                    ${admin.ime} ${admin.prezime}
                                    <br />
                                </div>
                            </div>
                        </li>
                        <li>
                            <a href="/cms/admin/home"><i class="fa fa-dashboard "></i>Dashboard</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-sitemap"></i>Vesti <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="/cms/admin/vesti/sve-vesti">Sve vesti</a>
                                </li>
                                <li>
                                    <a href="/cms/admin/vesti/nova-vest">Dodaj vest</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a class="active-menu-top" href="#"><i class="fa fa-sitemap "></i>Kategorije<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse in">
                                <li>
                                    <a href="/cms/admin/kategorije/nova-kategorija">Dodaj kategoriju</a>
                                </li>
                                <li>
                                    <a class="active-menu" href="/cms/admin/kategorije/sve-kategorije">Sve kategorije</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="/cms/admin/statistika"><i class="fa fa-bar-chart "></i>Statistika<span class="fa arrow"></span></a>
                        </li>
                    </ul>
                </div>
            </nav>
            <div id="page-wrapper">
                <div id="page-inner">
                    <div class="row">
                        <div class="col-md-12">
                            <h1 class="page-head-line">Sve kategorije</h1>
                        </div>
                    </div>

                    <!-- /. ROW  -->
                    <div class="col-md-12">
                        <div id="upozorenje">  
                        </div>
                        <div class="table-responsive">
                            <form action="sve-kategorije" method="POST">
                                <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Naziv</th>
                                            <th>Prikazi na pocetnoj</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="kat" items="${kategorije}">
                                            <tr>
                                                <td>${kat.kategorijaID}</td>
                                                <td>${kat.naziv}</td>
                                                <td><input type="checkbox" class="single-checkbox" name="prikazNaPocetnoj" value="${kat.kategorijaID}"  <c:if test="${kat.prikazNaPocetnoj == true}">checked="checked"</c:if> /></td>     
                                                </tr>                                            
                                        </c:forEach>

                                    </tbody>
                                </table>
                                <button type="submit" class="btn btn-info">Sacuvaj</button>

                            </form>
                        </div>
                    </div>

                </div>
                <!-- /. PAGE INNER  -->
            </div>
            <!-- /. PAGE WRAPPER  -->
        </div>
        <!-- /. WRAPPER  -->
        <%@include file="footer.jsp" %>
        <script>
            $(document).ready(function () {
                $("input[name='prikazNaPocetnoj']").change(function () {
                    var maxAllowed = 3;
                    var cnt = $("input[name='prikazNaPocetnoj']:checked").length;
                    if (cnt > maxAllowed) {
                        $(this).prop("checked", "");
                        $("#upozorenje").html("<div class='alert alert-danger fade in'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>Na pocetnoj mogu biti prikazane najvise 3 kategorije</div>");
                        $("#upozorenje").show();
                    }
                });
            });

        </script>
    </body>
</html>
