<%--
    Document   : changePassword
    Created on : 11 Mar, 2012, 11:07:38 PM
    Author     : Akil Mahimwala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="common/script.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="common/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="title.changePassword"/></title>
        <script>
            function checkPassword() {
                if (document.getElementById("newPassword").value!=document.getElementById("confirmPassword").value || document.getElementById("newPassword").value=="") {
                    alert('<spring:message code="msg.passwordMatch"/>');
                    return false;
                } else {
                    return true;
                }
            }
        </script>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="in_02">
            <%@include file="common/message.jsp" %>
            <form:form commandName="password" method="POST">
                <form:hidden path="userId"/>
                <form:hidden path="username"/>
                <fieldset>
                    <legend><spring:message code="title.changePassword"/></legend>
                    <table class="formTable">
                        <tr><td><spring:message code="changePasswordForUser"/> - ${password.username}(${password.userId})</td></tr>
                        <tr><td><spring:message code="oldPassword"/><br/><form:password path="oldPassword"/></td></tr>
                        <tr><td><spring:message code="newPassword"/><br/><form:password path="newPassword"/></td></tr>
                        <tr><td><spring:message code="confirmNewPassword"/><br/><input type="password" id="confirmPassword"/></td></tr>
                        <tr>
                            <td>
                                <spring:hasBindErrors name="password">
                                    <div class="error">
                                        <form:errors path="*"/>
                                    </div>
                                </spring:hasBindErrors> 
                            </td>
                        </tr>
                        <tr><td style="text-align: right"><input type="submit" class="userInputFormSubmit" name="btnSubmit" value="<spring:message code="button.Update"/>" onclick="return checkPassword();"/><input type="submit" class="userInputFormSubmit" name="_cancel" value="<spring:message code="button.Cancel"/>"/></td></tr>
                    </table>
                </fieldset>
            </form:form>
        </div>
    </body>
</html>
