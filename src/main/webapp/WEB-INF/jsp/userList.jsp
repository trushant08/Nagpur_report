<%-- 
    Document   : userList
    Created on : 9 Oct, 2015, 10:53:11 AM
    Author     : shrutika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="common/script.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="common/head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List User</title>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#row td').click(function() {$('#userId').val($(this).parent().data("user-id"));$('#form2').prop('action','showEditUser.htm');$('#form2').submit();});
                $('a.resetLink').click(function(event){event.stopPropagation();$('#userId').val($(this).parent().parent().data('user-id')); $('#form2').prop('action','userFailedAttemptsReset.htm');$('#form2').submit();});
                $('a.editLink').click(function(event){event.stopPropagation();$('#userId').val($(this).parent().parent().data('user-id')); $('#form2').prop('action','showEditUser.htm');$('#form2').submit();});
            });
        </script>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <%@include file="menu.jsp" %>
        <div class="in_02">
            <h4>User list</h4>
            <c:out value="${fn:length(userList)}"/> <spring:message code="rowsFound"/>
            <table id="data_table">
                <thead>
                    <tr>
                        <th><spring:message code="userId"/> &nbsp;</th>
                        <th><spring:message code="username"/> &nbsp;</th>
                        <th><spring:message code="role"/> &nbsp;</th>
                        <th><spring:message code="active"/> &nbsp;</th>
                        <th><spring:message code="outsideAccess"/> &nbsp;</th>
                        <th><spring:message code="expiresOn"/> &nbsp;</th>
                        <th><spring:message code="failedAttempts"/> &nbsp;</th>
                        <th><spring:message code="lastLoginDate"/> &nbsp;</th>
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${userList}" var="userItem">
                        <tr id="row" data-user-id="${userItem.userId}">
                            <td><c:out value="${userItem.userId}"/></td>
                            <td><c:out value="${userItem.username}"/></td>
                            <td><c:out value="${userItem.role.roleName}"/></td>
                            <td><c:out value="${userItem.active}"/></td>
                            <td><c:choose><c:when test="${userItem.outsideAccess}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td>
                            <td><fmt:formatDate value="${userItem.expiresOn}" pattern="dd-MM-yyyy"/></td>
                            <td><c:out value="${userItem.failedAttempts}"/></td>
                            <td><fmt:formatDate value="${userItem.lastLoginDate}" pattern="dd-MM-yyyy"/></td>
                            <td style="text-align: center;">
                                <a href="#" class="resetLink" title="Reset failed attempts"><img src="../images/reset.png" style="border: 0;"/></a>
                                <a href="#" class="editLink"  title="Edit User"><img src="../images/edit.png" style="border: 0;"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <form name="form2" id="form2" action="" method="post">
                <input type="hidden" id="userId" name="userId"/>
            </form>
        </div>

    </body>
</html>