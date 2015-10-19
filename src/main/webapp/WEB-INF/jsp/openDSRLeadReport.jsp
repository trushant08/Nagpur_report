<%-- 
    Document   : openDSRLeadReport
    Created on : 17 Jul, 2014, 5:31:27 PM
    Author     : citius
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="common/script.jsp" %>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="common/head.jsp" %>
        <%@include file="header.jsp" %>
        <%@include file="menu.jsp" %>      
    </head>

    <body>
        <div class="in_02">
            <form   method="post" name="form1" id="form1" action="openDSRLeadReport.htm"   cssClass="userInputForm" >
                <fieldset style="width:400px">
                    <legend>Marico DSR Leads</legend>
                    <table class="data_table" cellpadding="1" cellspacing="1">
                        <thead>
                            <tr class="header">
                                <td>Created Date</td>
                                <td>Beat Description</td>
                                <td>Count</td>
                                <td>List Id</td>
                                <td>View Details</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${leadList}" var="maricoLeadItem">
                                <tr class="row td">
                                    <td><c:out value="${maricoLeadItem.createdDate}"/></td>
                                    <td><c:out value="${maricoLeadItem.beatDiscription}"/></td>
                                    <td><c:out value="${maricoLeadItem.count}"/></a></td>
                                    <td><c:out value="${maricoLeadItem.listId}"/></td>
                                    <td align="center">  
                                        <c:choose><c:when test="${maricoLeadItem.listId!=''}">
                                                <sec:authorize ifAnyGranted="ROLE_BF_ADMIN,ROLE_BF_OPEN_DSR_LEAD_REPORT">
                                                    <a href=javascript:void(0); onclick="window.open('insertOpenLeadIntoDialer.htm?desc=${maricoLeadItem.beatDiscription}&id=<fmt:formatDate value="${maricoLeadItem.createdDate}" pattern="yyyyMMdd"/>','_blank','status=0, toolbar=0, location=0, menubar=0, directories=0, resizable=1, scrollbars=1, width=1000, height=550');return false;">
                                                        <img src="images/edit.png">
                                                    </a>
                                                </sec:authorize> 
                                            </c:when>
                                            <c:otherwise></c:otherwise></c:choose>
                                            </td>
                                        </tr>
                            </c:forEach>
                        </tbody>
                    </table>
            </form>
        </div>
    </body>
</html>