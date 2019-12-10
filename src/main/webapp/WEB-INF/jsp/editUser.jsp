<%-- 
    Document   : editUser
    Created on : 9 Oct, 2015, 12:48:05 PM
    Author     : shrutika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="common/script.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="common/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit User</title>
        <script type="text/javascript">
            var rules=new Array();
            rules[0]='username|required|Please Enter username';
        </script>
    </head>
    <body onLoad="yav.init('form1',rules,'inline');document.getElementById('username').focus();">
        <%@include file="header.jsp" %>
        <%@include file="menu.jsp" %>
        <div class="in_02">
            <form:form commandName="user" method="POST" name="form1" action="editUser.htm">
                <form:hidden path="userId"/>
                <fieldset>                 
                    <legend><spring:message code="title.editUser"/></legend>

                    <div class="row">
                        <div class="col-md-9">
                            <label class="req"><spring:message code="username"/></label>
                            <form:input path="username" cssClass="form-control"/>
                            <span id="errorsDiv_username"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-9">
                            <label class="req"><spring:message code="password"/></label>
                            <form:password path="password" cssClass="form-control"/>
                            <span id="errorsDiv_password"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-9">
                            <label class="req"><spring:message code="role"/></label>
                            <form:select path="role.roleId" cssClass="form-control">
                                <form:option value="" label="-"/>
                                <form:options items="${roleList}" itemLabel="roleId" itemValue="roleId"/>
                            </form:select>
                            <span id="errorsDiv_role.roleId"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <label class="req"><spring:message code="active"/></label>&nbsp;&nbsp;&nbsp;
                            <spring:message code="yes" /> 
                            <form:radiobutton path="active" value="true"/>&nbsp;&nbsp;&nbsp;
                            <spring:message code="no"/> 
                            <form:radiobutton path="active" value="false"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <label class="req"><spring:message code="outsideAccess"/></label>&nbsp;&nbsp;&nbsp;
                            <spring:message code="yes"/> <form:radiobutton path="outsideAccess" value="true"/>&nbsp;&nbsp;&nbsp;
                            <spring:message code="no"/> <form:radiobutton path="outsideAccess" value="false"/>
                        </div>
                    </div>
                    <div class="row">
                        <spring:hasBindErrors name="user">
                            <div class="error">
                                <form:errors path="*"/>
                            </div>
                        </spring:hasBindErrors>
                    </div>
                    <div class="span3">
                        <div class="input-append">
                            <input type="submit" class="userInputFormSubmit" name="btnSubmit" value="<spring:message code="button.Submit"/>" onclick="return yav.performCheck('form1', rules, 'inline');"/>
                            <input type="submit" class="userInputFormSubmit" name="_cancel" value="<spring:message code="button.Cancel"/>"/>
                        </div>
                    </div>  
                </fieldset>
            </form:form>
        </div>
    </body>
</html>