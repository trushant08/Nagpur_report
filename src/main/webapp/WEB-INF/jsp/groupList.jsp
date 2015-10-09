<%-- 
    Document   : serviceGroupList
    Created on : 13 Apr, 2013, 10:47:36 AM
    Author     : sagar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="common/script.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="common/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Group List</title>
        <script type="text/javascript" defer="defer">
            $(document).ready(function() {
                $('#row td').click(function(e) {e.stopPropagation();$('#groupId').val($(this).parent().data("group-id"));$('#form2').prop('action','groupEdit.htm');$('#form2').submit();});
            });
        </script>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <%@include file="menu.jsp" %>
        <div class="in_02">
            <h4>Group list</h4>
            <c:out value="${fn:length(groupList)}"/> <spring:message code="rowsFound"/>
            <table id="data_table">
                <thead>
                    <tr>
                        <th>Group Id &nbsp;</th>
                        <th>Group Desc &nbsp;</th>
                        <th>Server Type &nbsp;</th>
                        <th>Active &nbsp;</th>
                        <th>Edit &nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${groupList}" var="group">
                        <tr id="row" data-group-id="${group.groupId}">
                            <td><c:out value="${group.groupId}"/></td>
                            <td><c:out value="${group.groupDesc}"/></td>
                            <td><c:out value="${group.serverType.serverTypeDesc}"/></td>
                            <td><c:out value="${group.active}"/></td>
                            <td><a href="#"><spring:message code="edit"/></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <form name="form2" id="form2" action="" method="get">
                <input type="hidden" id="groupId" name="groupId"/>
            </form>
        </div>
    </body>
</html>

