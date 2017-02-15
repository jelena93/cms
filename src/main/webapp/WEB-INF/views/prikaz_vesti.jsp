<%@page import="org.cms.domen.Fajl"%>
<%@page import="org.cms.domen.Vest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${vest.naslov}</title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>
        <script src='<c:url value="/resources/galleria/galleria-1.4.2.min.js" />'></script>
        <%@include file="header.jsp" %>
        <script>
            $(document).ready(function () {
                $('.dropdown-toggle').dropdown();
            });

        </script> 
        <style>
            .galleria{ width: 700px; height: 400px; background: #000; margin: auto }
            .user_name{
                font-size:14px;
                font-weight: bold;
            }
            .comments-list .media{
                border-bottom: 1px dotted #ccc;
            }
            textarea{
                resize: none;
            }
        </style>

    </head>
    <body>
        <%@include file="meni_pocetna.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 wrapper">
                    <h1><b>${vest.naslov}</b></h1>
                    <small class="pull-right"><fmt:formatDate value="${vest.datum}" pattern="dd.MM.yyyy."/></small><br/>
                    <hr />
                    ${vest.tekst}
                    <c:if test="${vest.fajlList.size()==0}">                 
                        <br/>
                        <small class="pull-right">Objavljeno  u <a href="/cms/vesti_kategorija?kategorijaID=${vest.kategorija.kategorijaID}">${vest.kategorija.naziv}</a>
                        </small>
                        <br/>
                    </c:if>
                    <hr />
                    <c:if test="${vest.fajlList.size()>0}">                 
                        <div class="galleria">
                            <c:forEach var="fajl" items="${vest.fajlList}">
                                <c:choose>
                                    <c:when test="${fajl.ekstenzija=='video'}">
                                        <c:set var="path" value="${fajl.putanja}" />
                                        <c:set var="slika" value="${fajl.naziv}" />
                                        <a href="${path}"></a>
                                        <img src="http://img.youtube.com/vi/${slika}/hqdefault" alt="">
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="path" value="http://localhost:8081/images/${fajl.naziv}.${fajl.ekstenzija}" />
                                        <img src="${path}" alt=""/>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                        <script>
                            Galleria.loadTheme('../resources/galleria/themes/classic/galleria.classic.min.js');
                            Galleria.run('.galleria');
                        </script>
                        <br/>
                        <small class="pull-right">Objavljeno  u <a href="/cms/vesti_kategorija?kategorijaID=${vest.kategorija.kategorijaID}">${vest.kategorija.naziv}</a>
                        </small>
                        <br/>
                        <hr/>
                    </c:if>  
                    <c:if test="${user!=null}">
                        <div class="row">
                            <c:if test="${user.tip=='twitter'}">
                                <div class="col-sm-1"> 
                                    <form method="POST" action="/cms/share/twitter">
                                        <input name="twitter-link" value="${link}" type="hidden" />
                                        <button class="btn btn-twitter"><i class="fa fa-twitter"></i> Share</button>
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${user.tip=='facebook'}">
                                <div class="col-sm-1"> 
                                    <form method="POST" action="/cms/share/facebook">
                                        <input name="facebook-link" value="${link}" type="hidden" />
                                        <button class="btn btn-facebook"><i class="fa fa-facebook"></i> Share</button>
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${user.tip=='linkedin'}">
                                <div class="col-sm-1"> 
                                    <form method="POST" action="/cms/share/linkedin">
                                        <input name="linkedin-link" value="${link}" type="hidden" />
                                        <button class="btn btn-linkedin"><i class="fa fa-linkedin"></i> Share</button>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </c:if>

                    <div class="page-header">
                        <h1><small id="brojacKomentara" class="pull-right">${fn:length(komentari)} komentara</small> Komentari </h1>
                    </div> 

                    <br/>

                    <div class="comments-list">
                        <sec:authorize access="isAuthenticated()"> 
                            <sec:authentication var="logged_user" property="principal" />
                            <div class="media">
                                <div class="media-body">
                                    <form id="forma">
                                        <div class="form-group">
                                            <label for="comment">Ostavite komentar:</label>
                                            <textarea id="IDKomentara" class="form-control" rows="5" id="comment"  name="sadrzaj" placeholder="Ostavite komentar..." required></textarea>
                                        </div>
                                        <br/>
                                        <div id="zaAjaxDodavanje"></div>
                                        <input type="hidden" name="vestID" value="${vest.vestID}" />
                                        <input type="hidden" name="korisnikID" value="${logged_user.korisnikID}" />
                                        <input type="hidden"  name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <div class="form-group pull-right">
                                            <input type="button" onclick="postaviKomentar()" value="Posalji" class="btn-info"/>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </sec:authorize>

                        <div id="anchorDiv"></div>
                        <c:forEach var="k" items="${komentari}">
                            <div class="media">
                                <p class="pull-right"><small><fmt:formatDate value="${k.komentarPK.datum}" pattern="dd.MM.yyyy. hh:mm"/>h</small></p>
                                <div class="media-body">
                                    <h4 class="media-heading user_name">${k.korisnik.username}</h4>
                                    ${k.sadrzaj}
                                </div>
                            </div> 
                        </c:forEach>

                    </div>
                </div>
            </div>
            <br/>
            <br/>
            <br/>
        </div>
        <script>
            function postaviKomentar() {
                var sadrzaj = document.getElementById('IDKomentara').value;
                if (sadrzaj == "") {
                    return;
                }

                var date = new Date();
                var day = date.getDate();
                var monthIndex = date.getMonth() + 1;
                var year = date.getFullYear();
                var sati = date.getHours();
                var minuti = date.getMinutes();

                var nulaMesec = "";
                if (monthIndex < 10) {
                    nulaMesec += "0";
                }
                var nulaDan = "";
                if (day < 10) {
                    nulaDan += "0";
                }

                var datum = "" + nulaDan +
                        "" + day + "." + nulaMesec + monthIndex + "." + year + "." + " " +
                        sati + ":" + minuti + "h";

                console.log(datum);

                var root_cvor = document.createElement('div');
                root_cvor.setAttribute("class", "media");
                var p_cvor = document.createElement('p');
                p_cvor.setAttribute("class", "pull-right");
                //root_cvor.appendChild(p_cvor);

                var small_cvor = document.createElement('small');
                var small_tekst = document.createTextNode(datum);
                small_cvor.appendChild(small_tekst);
                p_cvor.appendChild(small_cvor);
                root_cvor.appendChild(p_cvor);

                var div_cvor = document.createElement('div');
                div_cvor.setAttribute("class", "media-body");
                var h4_cvor = document.createElement('h4');
                h4_cvor.setAttribute("class", "media-heading user_name");
                var h4_tekst = document.createTextNode("${logged_user.username}");
                h4_cvor.appendChild(h4_tekst);
                div_cvor.appendChild(h4_cvor);
                var komentar_sadrzaj = document.createTextNode(sadrzaj);
                div_cvor.appendChild(komentar_sadrzaj);
                root_cvor.appendChild(div_cvor);

                var comments_list = document.getElementsByClassName('comments-list')[0];
                comments_list.insertBefore(root_cvor, document.getElementById('anchorDiv'));
                document.getElementById('IDKomentara').value = "";
                /*
                var brojKomentara = parseInt("${fn:length(komentari)}");
                brojKomentara++;
                document.getElementById('brojacKomentara').innerHTML = "" + brojKomentara + " komentara";
                */
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        document.getElementById('brojacKomentara').innerHTML = xhttp.responseText+" komentara";
                    }
                };
                xhttp.open("POST", "/cms/vesti/postavi-komentar", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("sadrzaj=" + sadrzaj + "&vestID=" + "${vest.vestID}" + "&korisnikID=${logged_user.korisnikID}");
            }
        </script>
    </body>
</html>
