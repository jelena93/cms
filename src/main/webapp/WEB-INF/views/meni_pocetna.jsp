<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link href="<c:url value="/resources/css/pocetna.css" />" rel="stylesheet" />

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/cms/pocetna">Najnovije vesti</a>
        </div>
        <ul class="nav navbar-nav">

            <c:forEach var="kat" items="${kategorije}">
                <c:choose>
                    <c:when test="${!empty kat.kategorijaList}">
                        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">${kat.naziv}<span class="caret"></span></a>
                            <ul class="dropdown-menu multi-level">
                                <li><a href="/cms/vesti_kategorija?kategorijaID=${kat.kategorijaID}">Vesti</a></li>
                                <li class="dropdown-submenu"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Podkategorije</a>
                                    <ul class="dropdown-menu">
                                        <myTags:meni_kategorije kategorije="${kat}"/>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">${kat.naziv}<span class="caret"></span></a>
                            <ul class="dropdown-menu multi-level">
                                <li><a href="/cms/vesti_kategorija?kategorijaID=${kat.kategorijaID}">Vesti</a></li>
                            </ul>
                        </li>
                    </c:otherwise>

                </c:choose>
            </c:forEach>

        </ul>
        <ul class="nav navbar-nav navbar-right">


            <sec:authorize access="!isAuthenticated()"> 
                <c:if test="${user ==null}">
                    <li><a href="/cms/login"><span class="glyphicon glyphicon-log-in"></span> Login</a>
                    </li>
                    <li>${user}</li>
                    </c:if>
                </sec:authorize>

            <sec:authorize access="hasRole('ROLE_ADMIN')"> 
                <sec:authentication var="logged_user" property="principal" />
                <li><a href="/cms/admin/home"><span class="glyphicon glyphicon-user"></span> ${logged_user.ime} ${logged_user.prezime}</a></li>
                <li><form role="form" action="/cms/logout" method="POST">
                        <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
                        <label style="color: #777;" for="mySubmit1" class="odjava btn"><span class="glyphicon glyphicon-log-out"></span> Odjavi se</label>
                        <input id="mySubmit1" type="submit" value="" class="hidden" />
                    </form>
                </li>  
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_USER')"> 
                <sec:authentication var="logged_user" property="principal" />
                <li><a href="/cms/admin/home"><span class="glyphicon glyphicon-user"></span> ${logged_user.ime} ${logged_user.prezime}</a></li>
                <li><form role="form" action="/cms/logout" method="POST">
                        <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
                        <label style="color: #777;" for="mySubmit2" class="odjava btn"><span class="glyphicon glyphicon-log-out"></span> Odjavi se</label>
                        <input id="mySubmit2" type="submit" value="" class="hidden" />
                    </form>
                </li>     
            </sec:authorize>
            <c:if test="${user !=null}">
                <c:choose>
                    <c:when test="${user.slika!=null}">
                        <li><a href="${user.link}"><img src="${user.slika}" width="20" height="20"/> ${user.ime} ${user.prezime}</a></li>
                            </c:when>
                            <c:otherwise>
                        <li><a href="${user.link}"><span class="glyphicon glyphicon-user"></span> ${user.ime} ${user.prezime}</a></li>

                    </c:otherwise>
                </c:choose>
                <li><a href="/cms/social-logout/${user.tip}"><span class="glyphicon glyphicon-log-out"></span> Odjavi se</a></li>
                </c:if>
        </ul>
    </div>
</nav>
</html>
