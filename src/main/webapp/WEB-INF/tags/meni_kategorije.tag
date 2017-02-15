<%@ attribute name="kategorije" type="org.cms.domen.Kategorija" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" 
           prefix="fn" %>
<c:if test="${!empty kategorije.kategorijaList}">
    <c:forEach var="kat" items="${kategorije.kategorijaList}">
        <c:choose>
            <c:when test="${!empty kat.kategorijaList}">
                <li class="dropdown-submenu"><a class="dropdown-toggle" data-toggle="dropdown" href="#">${kat.naziv}</a>
                    <ul class="dropdown-menu">
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
                <li><a href="/cms/vesti_kategorija?kategorijaID=${kat.kategorijaID}">${kat.naziv}</a></li>
                </c:otherwise>

        </c:choose>
    </c:forEach>          
</c:if>