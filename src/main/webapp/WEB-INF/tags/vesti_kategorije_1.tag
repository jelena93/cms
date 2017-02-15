<%@ attribute name="kategorija" type="org.cms.domen.Kategorija" required="true" %>
<%@ attribute name="vesti" type="java.util.List" required="true" %>
<%@ attribute name="counter" type="java.lang.Integer" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="count" value="${counter}" scope="page" />
<c:choose>
    <c:when test="${empty kategorija.kategorijaList}">
        <c:forEach var="v" items="${vesti}">
            <c:if test="${kategorija==v.kategorija }">
                <c:set var="kratakOpis" value="${fn:substring(v.tekst, 0, 350)}" />
                <div class="panel panel-default">
                    <div class="panel-body" >
                        <small class="pull-right"><fmt:formatDate value="${v.datum}" pattern="dd.MM.yyyy."/></small>
                        <h4><b>${v.naslov}</b></h4>
                        <hr/>
                        ${kratakOpis}
                        <small><a href="<c:url value="vesti/prikaz_vesti" >
                                      <c:param name="vestID" value="${v.vestID}"></c:param>
                                  </c:url>">Procitaj vise</a></small>
                        <br/>
                        <small class="pull-right">Objavljeno  u <a href="/cms/vesti_kategorija?kategorijaID=${v.kategorija.kategorijaID}">${v.kategorija.naziv}</a>
                        </small>
                    </div>
                </div>
                <c:set var="count" value="${count + 1}" scope="page"/>
            </c:if>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:forEach var="v" items="${vesti}">
            <c:if test="${kategorija==v.kategorija}">
                <c:set var="kratakOpis" value="${fn:substring(v.tekst, 0, 350)}" />
                <div class="panel panel-default">
                    <div class="panel-body" >
                        <small class="pull-right"><fmt:formatDate value="${v.datum}" pattern="dd.MM.yyyy."/></small>
                        <h4><b>${v.naslov}</b></h4>
                        <hr/>
                        ${kratakOpis}
                        <small><a href="<c:url value="vesti/prikaz_vesti" >
                                      <c:param name="vestID" value="${v.vestID}"></c:param>
                                  </c:url>">Procitaj vise</a></small>
                        <br/>
                        <small class="pull-right">Objavljeno  u <a href="/cms/vesti_kategorija?kategorijaID=${v.kategorija.kategorijaID}">${v.kategorija.naziv}</a>
                        </small>
                    </div>
                </div>
                <c:set var="count" value="${count + 1}" scope="page"/>
            </c:if>
        </c:forEach>
        <c:forEach var="kat" items="${kategorija.kategorijaList}">
            <myTags:vesti_kategorije_1 kategorija="${kat}" vesti="${vesti}" counter="${count}"/>
        </c:forEach>
    </c:otherwise>
</c:choose>  