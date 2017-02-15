<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<!DOCTYPE html>
<html>
    <head>
        <title>${kat.naziv}</title>

        <%@include file="header.jsp" %>
        <script>
            $(document).ready(function () {
                $('.dropdown-toggle').dropdown();
            });
        </script>  

    </head>
    <body>
        <%@include file="meni_pocetna.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 wrapper">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="row">
                                <div class="col-sm-4">
                                    <ul class="nav pull-left" style="border: 1px solid #2e2873;">
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
                                    </ul>
                                </div>
                                <div class="col-sm-7 col-sm-offset-1">
                                    <h3>${kat.naziv}</h3>
                                </div>
                            </div>
                            <hr />
                            <c:set var="count" value="0" scope="page" />
                            <div class="panel-group">
                                <c:forEach var="vest" items="${vesti}">
                                    <c:if test="${kat==vest.kategorija}">
                                        <c:set var="kratakOpis" value="${fn:substring(vest.tekst, 0, 350)}" />
                                        <div class="panel panel-default">
                                            <div class="panel-body" >
                                                <small class="pull-right"><fmt:formatDate value="${vest.datum}" pattern="dd.MM.yyyy."/></small>
                                                <h4><b>${vest.naslov}</b></h4>
                                                <hr/>
                                                ${kratakOpis}
                                                <small><a href="<c:url value="vesti/prikaz_vesti" >
                                                              <c:param name="vestID" value="${vest.vestID}"></c:param>
                                                          </c:url>">Procitaj vise</a></small>
                                                <br/>
                                                <small class="pull-right">Objavljeno  u <a href="/cms/vesti_kategorija?kategorijaID=${vest.kategorija.kategorijaID}">${vest.kategorija.naziv}</a>
                                                </small>
                                            </div>
                                        </div>
                                        <c:set var="count" value="${count + 1}" scope="page"/>
                                    </c:if>
                                </c:forEach>
                            </div>


                            <c:forEach var="kateg" items="${kat.kategorijaList}">
                                <myTags:vesti_kategorije_1 kategorija="${kateg}" vesti="${vesti}" counter="0"/>
                            </c:forEach>
                        </div>
                    </div>
                </div>

            </div>
            <br/>
        </div>
        <%@include file="footer.jsp" %>    
    </body>
</html>
