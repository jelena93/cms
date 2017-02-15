<%@ attribute name="kategorije" type="org.cms.domen.Kategorija" required="true" %>
<%@ attribute name="razmak" type="java.lang.String" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" 
           prefix="fn" %>
<c:if test="${!empty kategorije.kategorijaList}">
    <!--<ul>-->
    <c:set var="count" value="${razmak}" scope="page" />
    <c:forEach var="kat" items="${kategorije.kategorijaList}">
        <c:choose>
            <c:when test="${!empty kat.kategorijaList}">
                <c:set var="count" value="${'&nbsp;&nbsp;&nbsp;'}${count}"/> 
               <optgroup label=" ${count}${kat.naziv}">
                    <myTags:folderGroups kategorije="${kat}" razmak="${count}"/>
                </optgroup>
               
            </c:when>
            <c:otherwise>
               <option name="kategorija" value="${kat.kategorijaID}">${count}${kat.naziv}</option>
            </c:otherwise>
        </c:choose>

    </c:forEach>
    <!--</ul>-->
</c:if>
    