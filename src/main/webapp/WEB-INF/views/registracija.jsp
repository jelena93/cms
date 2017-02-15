<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Registracija</title>
        <%@include file="header.jsp" %>
        <link href="<c:url value="/resources/css/login-register.css" />" rel="stylesheet" />
        <script src='https://www.google.com/recaptcha/api.js'></script>
    </head>
    <body style="background-color: #2e2873">
        <div class="container">
            <div class="row text-center " style="padding-top:100px;">
                <div>
                    <h2>CMS</h2>
                </div>
            </div>
            <div class="row ">
                <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
                    <div class="panel-body">
                        <form role="form" action="registracija" method="POST"  onsubmit="return recapFunkcija();">
                            <hr />
                            <div class="form-group input-group">
                                <span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
                                <input type="text" class="form-control" placeholder="Username" name="username" required />
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
                                <input type="password" class="form-control"  placeholder="Password" name="password" required />
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon"><i class="fa fa-envelope-square"  ></i></span>
                                <input type="email" class="form-control"  placeholder="Email" name="email" required />
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon"><i class="fa fa-user"  ></i></span>
                                <input type="text" class="form-control" placeholder="Ime" name="ime" required/>
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon"><i class="fa fa-user"  ></i></span>
                                <input type="text" class="form-control" placeholder="Prezime" name="prezime" required/>
                            </div>
                            <div id="roboTest" style="color:white;font-size: 14px"></div>
                            <div class="g-recaptcha" data-sitekey="6LfityQTAAAAAIzqOC01sacRzlVxgS20ufVJn2sP" ></div>

                            <br/>
                            <div class="login">
                                <input type="submit" value="Registruj se" class="btn input-block-level form-control" />
                            </div>
                            <input  type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>

                        </form>
                    </div>

                </div>     
            </div>
        </div>
        <script>
            function recapFunkcija() {
                document.getElementById('roboTest').innerHTML = "";
                var g_r_r = document.getElementById('g-recaptcha-response').value;
                var xhttp = new XMLHttpRequest();
                var odg = "";
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState == 4 && xhttp.status == 200) {
                        var json_objekat = JSON.parse(xhttp.responseText);
                        console.log(json_objekat.success);
                        odg = json_objekat.success;
                    }
                };
                xhttp.open("POST", "https://www.google.com/recaptcha/api/siteverify", false);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("secret=6LfityQTAAAAAAQ7oxSSw8tO9BWjCQMmB3bWpy9M" + "&response=" + g_r_r);
                if (odg === false) {
                    document.getElementById('roboTest').innerHTML = "Niste prošli test za robota! Pokušajte ponovo."
                    return false;
                }
                else {
                    return true;
                }
            }
        </script>
    </body>
</html>
