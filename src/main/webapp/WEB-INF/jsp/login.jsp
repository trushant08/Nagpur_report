<%-- 
    Document   : login
    Created on : 7 Sep, 2015, 3:59:56 PM
    Author     : shrutika
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="common/script.jsp" %>
        <%@include file="common/head.jsp" %>
        <%@include file="common/title.jsp"%>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <meta http-equiv="Expires" content="Tue, 01 Jan 2000 12:12:12 GMT"/>

        <link rel="stylesheet" type="text/css" href="css/login.css" media="screen">
        <script type="text/javascript">
            var rules=new Array();
            rules[0]='j_username|required|Enter User Name';
            rules[1]='j_password|required|Enter Password';
        </script>
    </head>
    <body onLoad="yav.init('user-login',rules);document.getElementById('j_username').focus();" >
        <!-- START WRAPPER -->
        <div id="wrapper">
            <!-- START PAGE-01 -->
            <div  id="page_01">   
                <!--<h1><spring:message code="title"/></h1>-->
                <!-- START LOGIN FORM HERE -->
                <form id="user-login" class="loginform"  action="<c:url value="j_spring_security_check" />" method="post" accept-charset="UTF-8" >
                    <!-- START LOGIN LOGO TOP -->
                    <div class="login_logo_top">
                        <div class="altius_logo"><a href="#" target="_self"><img src="images/altius_logo_login.jpg" alt="" width="112" height="80" /></a></div>
                        <div class="client_logo" style="padding: 20px; height: 40px;"><span style="font:normal  34px/normal Comic-san;  color: #333; ">REPORT</span></div>
                    </div>
                    <!-- END LOGIN LOGO TOP -->
                    <!-- START LOGIN BOX -->
                    <div class="loginbox">    
                        <fieldset>
                            <p>
                                <label for="j_username"><spring:message code="username"/></label>
                                <input id="j_username" name="j_username" value="${sessionScope[SPRING_SECURITY_LAST_USERNAME]}" type="text" class="userInputLarge"/>
                                <br /><span id="errorsDiv_j_username"></span>
                            </p>
                            <p class="password">
                                <label for="j_password"><spring:message code="password"/></label>
                                <input id="j_password" name="j_password" type="password"  class="userInputLarge"/>
                                <br/>  <span id="errorsDiv_j_password" ></span>
                            </p>
                            <input type="submit" value="" class="submit" onclick="return yav.performCheck('user-login', rules, 'inline');"/>
                        </fieldset>
                        <div class="row">
                            <!-- start powered by -->
                            <div class="designby" style="float: left; clear: both; margin-top: 291px;">
                                <span style="float:left;">Designed and built by Altius Customer Services Pvt Ltd</span>
                            </div>
                            <div class="" style="float: left; clear: both; margin-top: 32px; margin-left: 128px;">
                                <!-- start error message -->
                                <c:if test="${param.login_error=='true'}">
                                    <p id="baderror">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
                                </c:if>
                                <%--<c:if test="${param.errorMsg=='locked'}">
                                    <span id="baderror">Your account has been locked. Please contact an Administrator.</span>
                                </c:if>
                                <c:if test="${param.errorMsg=='credentials'}">
                                    <span id="baderror">Incorrect username or password entered.</span>
                                </c:if> --%>
                                <!-- end error message -->
                            </div>
                        </div>
                    </div>
                    <!-- end powered by -->  
                    <div class="browser_wrapper">
                        <div class="browser"> <span style="float:left; position:relative; margin:0;  padding:10px 0; text-align:left;">Best viewed with : </span> <span class="firefox">Firefox</span> <span class="chrome">Chrome</span> <span class="safari">Safari</span>
                            <span  style="float:left; position:relative; margin:0;  padding:10px 0; text-align:left;">Report v.1 ${majorVersion}.${minorVersion} </span></div>
                    </div> 

                </form>
            </div>
    </body>
</html>