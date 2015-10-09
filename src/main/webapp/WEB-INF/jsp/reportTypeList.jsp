<%-- 
    Document   : reportTypeList
    Created on : 22 Sep, 2015, 4:29:25 PM
    Author     : shrutika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="common/script.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="common/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report Type list</title>
        <script type="text/javascript" defer="defer">
            $(document).ready(function() {
                $('#row td').click(function(e) {e.stopPropagation();$('#reportTypeId').val($(this).parent().data("report-id"));$('#form2').prop('action','reportTypeEdit.htm');$('#form2').submit();});
            });
        </script>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <%@include file="menu.jsp" %>

        <div class="in_02">
            <h4>Report Type list</h4>
            <c:out value="${fn:length(reportTypeList)}"/> <spring:message code="rowsFound"/>
            <table id="data_table"> 
                <thead>
                    <tr>
                        <th>Report Type Id &nbsp;</th>
                        <th>Report Desc &nbsp;</th>
                        <th>Server Type &nbsp;</th>
                        <th>Active &nbsp;</th>
                        <th>Edit &nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${reportTypeList}" var="reportType">  
                        <tr id="row" data-report-id="${reportType.reportTypeId}">
                            <td><c:out value="${reportType.reportTypeId}"/></td>
                            <td><c:out value="${reportType.reportTypeDesc}"/></td>
                            <td><c:out value="${reportType.serverType.serverTypeDesc}"/></td>
                            <td><c:out value="${reportType.active}"/></td>
                            <td><a href="#"><spring:message code="edit"/></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <form name="form2" id="form2" action="" method="get">
                <input type="hidden" id="reportTypeId" name="reportTypeId"/>
            </form>
        </div>
    </body>
</html>