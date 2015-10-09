<%-- 
    Document   : test.jsp
    Created on : 21 Jul, 2014, 12:37:31 PM
    Author     : vipul
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
            <h2>Go Auto Dialer Inbound Call Log Report</h2>
            <c:out value="${fn:length(list)}"/> rows found.
            <span style="padding-left: 700px;">  <a href="#" onclick="$('#excelForm').submit();"><img src="images/Excel.png" alt="Download excel file" border="0" /></a><br/></span>
            <table class="data_table" width="850px">
                <thead>
                    <tr class="header">
                        <th>Call Date(<c:choose><c:when test="${zoneId==0}">IST</c:when><c:when test="${zoneId==1}">PST</c:when><c:otherwise>EST</c:otherwise></c:choose>)</th>
                                <th>Unique Id</th>
                                <th>Caller code</th>
                                <th>Did</th>
                                <th>Campaign</th>
                                <th>Term reason</th>  
                                <th>Status</th>
                                <th>Length in sec</th>
                                <th>Queue sec</th>
                                <th>Hold Time</th>
                                <th>Wrap Time</th>
                                <th>User</th>
                                <th>Call Date(EST)</th>
                            </tr>
                        </thead>
                        <tbody>
                    <c:forEach items="${list}" var="listItem">
                        <tr class="">
                            <td><fmt:formatDate value="${listItem.convertedCallStartDt}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td><c:out value="${listItem.uniqueid}"/></td>
                            <td><c:out value="${listItem.caller_id_number}"/></td>
                            <td><c:out value="${listItem.did_description}"/></td>
                            <td><c:out value="${listItem.campaign_description}"/></td>
                            <td><c:out value="${listItem.term_reason}"/></td>
                            <td><c:out value="${listItem.status}"/></td>
                            <td><c:out value="${listItem.length_in_sec}"/></td>
                            <td><c:out value="${listItem.queue_seconds}"/></td>
                            <td><c:out value="${listItem.HoldTime}"/></td>
                            <td><c:out value="${listItem.wraptime}"/></td>
                            <td><c:out value="${listItem.user}"/></td>
                            <td><fmt:formatDate value="${listItem.call_date}" pattern="yyyy-MM-dd HH:mm"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>
    </body>
</html>
