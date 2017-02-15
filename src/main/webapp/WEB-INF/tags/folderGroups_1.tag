<%@ attribute name="kategorije" type="org.cms.domen.Kategorija" required="true" %>
<%@ attribute name="razmak" type="java.lang.String" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" 
           prefix="fn" %>
<c:if test="${!empty kategorije.kategorijaList}">
    <c:set var="count" value="${razmak}" scope="page" />
    <c:set var="count" value="${'&nbsp;&nbsp;&nbsp;'}${count}"/> 
    <c:forEach var="kat" items="${kategorije.kategorijaList}">
        <c:choose>
            <c:when test="${!empty kat.kategorijaList}">
                <option name="kategorija" value="${kat.kategorijaID}">${count}${kat.naziv}</option>
                <myTags:folderGroups_1 kategorije="${kat}" razmak="${count}"/>
            </c:when>
            <c:otherwise>
                <option name="kategorija" value="${kat.kategorijaID}">${count}${kat.naziv}</option>
            </c:otherwise>
        </c:choose>

    </c:forEach>
</c:if>
