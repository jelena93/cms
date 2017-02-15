<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Sve vesti</title>
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
                            <a class="active-menu-top" href="#"><i class="fa fa-sitemap"></i>Vesti <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse in">
                                <li>
                                    <a class="active-menu" href="/cms/admin/vesti/sve-vesti">Sve vesti</a>
                                </li>
                                <li>
                                    <a href="/cms/admin/vesti/nova-vest">Dodaj vest</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-sitemap "></i>Kategorije<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="/cms/admin/kategorije/nova-kategorija">Dodaj kategoriju</a>
                                </li>
                                <li>
                                    <a href="/cms/admin/kategorije/sve-kategorije">Sve kategorije</a>
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
                            <h1 class="page-head-line">Sve vesti</h1>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="table-responsive">
                                <form action="sve-vesti" method="POST">
                                    <table class="table table-striped table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Datum</th>
                                                <th>Naslov</th>
                                                <th>Tekst</th>
                                                <th>Autor</th>
                                                <th>Obljavljena</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="vest" items="${vesti}">
                                                <c:set var="skraceni_tekst" value="${fn:substring(vest.tekst, 0, 50)}" />
                                                <tr>
                                                    <td>${vest.vestID}</td>
                                                    <td>${vest.datum}</td>
                                                    <td>${vest.naslov}</td>
                                                    <td>${skraceni_tekst}<a href="/cms/vesti/prikaz_vesti?vestID=${vest.vestID}"> <small>Procitaj vise</small></a></td>
                                                    <td>${vest.korisnikID.ime} ${vest.korisnikID.prezime}</td>
                                                    <td><input type="checkbox" class="single-checkbox" name="objavljena" value="${vest.vestID}"  <c:if test="${vest.objavljena}">checked="checked"</c:if> /></td>
                                                    </tr>                                            
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
                                    <button type="submit" class="btn btn-info">Sacuvaj</button>
                                </form>
                            </div>
                        </div>

                    </div>

                </div>
                <!-- /. PAGE INNER  -->
            </div>
            <!-- /. PAGE WRAPPER  -->
        </div>
        <!-- /. WRAPPER  -->

        <%@include file="footer.jsp" %>
    </body>
</html>

