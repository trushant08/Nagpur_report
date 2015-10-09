<%-- 
    Document   : serviceList
    Created on : 24 Sep, 2015, 3:19:44 PM
    Author     : shrutika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="common/script.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="common/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Service list</title>
        <script type="text/javascript" defer="defer">
            $(document).ready(function() {
                $('#row td').click(function(e) {e.stopPropagation();$('#serviceId').val($(this).parent().data("service-id"));$('#form2').prop('action','serviceEdit.htm');$('#form2').submit();});
            });
        </script>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <%@include file="menu.jsp" %>
        <div class="in_02">
            <h4>Service list</h4>
            <c:out value="${fn:length(serviceList)}"/> <spring:message code="rowsFound"/>
            <table id="data_table"> 
                <thead>
                    <tr>
                        <th>Service Id &nbsp;</th>
                        <th>Server Service Id &nbsp;</th>
                        <th>Service Name &nbsp;</th>
                        <th>Server Type &nbsp;</th>
                        <th>Active &nbsp;</th>
                        <th>Edit &nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${serviceList}" var="service">
                        <tr id="row" data-service-id="${service.serviceId}">
                            <td><c:out value="${service.serviceId}"/></td>
                            <td><c:out value="${service.serverServiceId}"/></td>
                            <td><c:out value="${service.serviceName}"/></td>
                            <td><c:out value="${service.serverType.serverTypeDesc}"/></td>
                            <td><c:out value="${service.active}"/></td>
                            <td><a href="#"/><spring:message code="edit"/></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <form name="form2" id="form2" action="" method="get">
                <input type="hidden" id="serviceId" name="serviceId"/>
            </form>
        </div>
    </body>
</html>