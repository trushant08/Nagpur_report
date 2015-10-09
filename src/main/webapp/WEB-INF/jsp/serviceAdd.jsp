<%-- 
    Document   : serviceAdd
    Created on : 24 Sep, 2015, 3:18:28 PM
    Author     : shrutika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="common/script.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="common/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Service</title>
        <script type="text/javascript">
            var rules=new Array();
            rules[0]='serviceName|required|Please Enter service name';
            rules[1]='serverServiceId|required|Please Enter server service id';
            rules[2]='serverType.serverTypeId|required|Select at least one server type';
        </script>
    </head>
    <body onLoad="yav.init('form1',rules,'inline');document.getElementById('serviceName').focus();">>
        <%@include file="header.jsp" %>
        <%@include file="menu.jsp" %>
        <div class="in_02">

            <form:form id="form1" name="form1" commandName="service" action="serviceAdd.htm" method="POST">
                <fieldset style="width: 300px">
                    <legend>Add Service</legend>

                    <div class="row">
                        <div class="col-md-9">
                            <label class="req">Service Name:</label>
                            <form:input path="serviceName" cssClass="form-control"/><br>
                            <span id="errorsDiv_serviceName"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-9">
                            <label class="req">Server Service Id:</label>
                            <form:input path="serverServiceId" cssClass="form-control"/><br>
                            <span id="errorsDiv_serverServiceId"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-9">
                            <label>Server Type:</label>
                            <form:select path="serverType.serverTypeId" cssClass="form-control"> 
                                <form:option value="">-</form:option>
                                <c:forEach items="${serverTypeList}" var="serverType">
                                    <form:option value="${serverType.serverTypeId}">${serverType.serverTypeDesc}</form:option>
                                </c:forEach>
                            </form:select>
                            <span id="errorsDiv_serverType.serverTypeId"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <label>Status</label>&nbsp;&nbsp;
                            <form:radiobutton path="active" value="true"/>Active &nbsp;&nbsp;
                            <form:radiobutton path="active" value="false"/>Inactive &nbsp;&nbsp;
                        </div>
                    </div><br>
                    <div class="input-append">
                        <input class="userInputFormSubmit" type="submit" value="Save" name="btnSubmit"/>
                    </div>
                </fieldset>
            </form:form>
        </div>
    </body>
</html>