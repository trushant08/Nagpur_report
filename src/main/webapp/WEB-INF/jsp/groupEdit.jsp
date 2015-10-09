<%-- 
    Document   : groupEdit
    Created on : 24 Sep, 2015, 12:47:54 PM
    Author     : shrutika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="common/script.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="common/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Group Edit</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <%@include file="menu.jsp" %>

        <div class="in_02">
            <form:form id="form1" name="form1" modelAttribute="group" action="groupEdit.htm" method="POST"> 
                <fieldset>
                    <legend>Edit Report Type</legend>
                    <form:hidden path="groupId" value="${groupId}"/>
                    Group Name: 
                    <form:input path="groupDesc" value="${groupDesc}"/><br><br/>
                    
                    Server Type: &nbsp;
                    <form:select path="serverType.serverTypeId" class="userInput"> 
                        <form:option value="">${serverType.serverTypeDesc}</form:option>
                        <c:forEach items="${serverTypeList}" var="serverType">
                            <form:option value="${serverType.serverTypeId}">${serverType.serverTypeDesc}</form:option>
                        </c:forEach>
                    </form:select><br><br>
                    
                    Active  : Yes <form:radiobutton path="active" value="true"/>&nbsp;&nbsp;&nbsp;No <form:radiobutton path="active" value="false"/><br><br/>
                    <input class="userInputFormSubmit" type="submit" value="Save" name="btnSubmit"/>
                </fieldset>
            </form:form>
        </div>
    </body>
</html>