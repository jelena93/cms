<%@ attribute name="kategorija" type="org.cms.domen.Kategorija" required="true" %>
<%@ attribute name="vesti" type="java.util.List" required="true" %>
<%@ attribute name="counter" type="java.lang.Integer" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="count" value="${counter}" scope="page" />
<c:choose>
    <c:when test="${empty kategorija.kategorijaList}">
        <c:forEach var="v" items="${vesti}">
            <c:if test="${kategorija==v.kategorija && count<=2}">
                <fmt:formatDate value="${v.datum}" var="datum" pattern="dd.MM.yyyy." />
                <a class="list-group-item" href="<c:url value="vesti/prikaz_vesti" >
                       <c:param name="vestID" value="${v.vestID}"></c:param>
                   </c:url>"><c:out value="${datum}${': '}${v.naslov}"/></a>
                <br/>
                <c:set var="count" value="${count + 1}" scope="page"/>
            </c:if>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:forEach var="v" items="${vesti}">
            <c:if test="${kategorija==v.kategorija && count<=2}">
                <fmt:formatDate value="${v.datum}" var="datum" pattern="dd.MM.yyyy." />
                <a class="list-group-item" href="<c:url value="vesti/prikaz_vesti" >
                       <c:param name="vestID" value="${v.vestID}"></c:param>
                   </c:url>"><c:out value="${datum}${': '}${v.naslov}"/></a>
                <br/>
                <c:set var="count" value="${count + 1}" scope="page"/>
            </c:if>
        </c:forEach>
        <c:forEach var="kat" items="${kategorija.kategorijaList}">
            <myTags:vesti_kategorije kategorija="${kat}" vesti="${vesti}" counter="0"/>
        </c:forEach>
    </c:otherwise>
</c:choose>  