<%-- 
    Document   : goAutoDialerOutboundReport
    Created on : Aug 28, 2014, 11:20:30 AM
    Author     : jitendra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%@include file="goAutoDialerReport.jsp" %>
        <div class="in_02">


            <h2>Go Auto Dialer Outbound Call Log Report</h2>
            <c:out value="${fn:length(list)}"/> rows found.
            <span style="padding-left: 735px;">  <a href="#" onclick="$('#excelForm').submit();"><img src="images/Excel.png" alt="Download excel file" border="0" /></a><br/></span>
            <table class="data_table" width="850px">
                <thead>
                    <tr class="header">
                        <th>Call Date(<c:choose><c:when test="${zoneId==0}">IST</c:when><c:when test="${zoneId==1}">PST</c:when><c:otherwise>EST</c:otherwise></c:choose>)</th>
                        <th>Phone Number</th>
                        <th>Length in sec</th>
                        <th>Status</th>
                        <th>Term reason</th>  
                        <th>User Id</th>
                        <th>Comments</th>
                        <th>Call Date(EST)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="listItem">
                        <tr class="">
                            <td><fmt:formatDate value="${listItem.convertedCallStartDt}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td><c:out value="${listItem.phone_number}"/></td>
                            <td><c:out value="${listItem.length_in_sec}"/></td>
                            <td><c:out value="${listItem.status}"/></td>
                            <td><c:out value="${listItem.term_reason}"/></td>
                            <td><c:out value="${listItem.user}"/></td>
                            <td><c:out value="${listItem.comments}"/></td>
                            <td><fmt:formatDate value="${listItem.call_date}" pattern="yyyy-MM-dd HH:mm"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>
    </body>
</html>
