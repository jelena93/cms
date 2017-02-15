<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Pocetna</title>

        <%@include file="header.jsp" %>
        <script>
            $(document).ready(function () {
                $('.dropdown-toggle').dropdown();
            });
        </script>  
    </head>
    <body>
        <%@include file="meni_pocetna.jsp" %>
        <div class="container wrapper">
            <div class="row">
                <div class="col-sm-8">
                    <h1>Sve vesti!<a href="/cms/pocetna?sort=desc"><span class="glyphicon glyphicon-sort-by-alphabet"></span></a><a href="/cms/pocetna?sort=asc"><span class="glyphicon glyphicon-sort-by-alphabet-alt"></span></a></h1>
                    <hr />
                    <div class="panel-group">
                        <c:forEach var="vest" items="${vesti}">
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
                        </c:forEach>
                    </div>
                </div>
                <div class="col-sm-4">
                    <h1>Kategorije</h1>
                    <hr/>

                    <div class="panel-group" id="accordion">
                        <c:forEach var="kat" items="${kategorije}">
                            <c:if test="${kat.prikazNaPocetnoj==true}">

                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapse${kat.kategorijaID}"><c:out  value="${kat.naziv}"/></a>
                                        </h4>
                                    </div>
                                    <div id="collapse${kat.kategorijaID}" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <div class="list-group">
                                                <c:set var="count" value="0" scope="page" />
                                                <c:forEach var="v" items="${vesti}">
                                                    <c:if test="${kat==v.kategorija && count<=2}">
                                                        <fmt:formatDate value="${v.datum}" var="datum" pattern="dd.MM.yyyy." />
                                                        <a class="list-group-item" href="<c:url value="vesti/prikaz_vesti" >
                                                               <c:param name="vestID" value="${v.vestID}"></c:param>
                                                           </c:url>"><c:out value="${datum}${': '}${v.naslov}"/></a>
                                                        <br/>
                                                        <c:set var="count" value="${count + 1}" scope="page"/>
                                                    </c:if>
                                                </c:forEach>

                                                <c:forEach var="kateg" items="${kat.kategorijaList}">
                                                    <myTags:vesti_kategorije kategorija="${kateg}" vesti="${vesti}" counter="0"/>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <%@include file="footer.jsp" %>    
    </body>
</html>
