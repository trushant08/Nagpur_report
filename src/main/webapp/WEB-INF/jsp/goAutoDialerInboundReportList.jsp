<%-- 
    Document   : goAutoDialerInboundReportList
    Created on : 16 Oct, 2015, 11:48:40 AM
    Author     : shrutika
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
            <span style="padding-left: 1000px;">  <a href="#" onclick="$('#excelForm').submit();"><img src="images/Excel.png" alt="Download excel file" border="0" /></a><br/></span>
            <table class="data_table" width="1150px">
                <thead>
                    <tr class="header">
                        <th>Call Date(<c:choose><c:when test="${zoneId==0}">IST</c:when><c:when test="${zoneId==1}">PST</c:when><c:otherwise>EST</c:otherwise></c:choose>)</th>
                                <th>Unique-Id</th>
                                <th>Caller-Id</th>
                                <th>Did Description </th>
                                <th>Campaign</th>
                                <th>Term Reason </th>
                                <th>Status</th>                    
                                <th>Length (sec)</th>                                    
                                <th>Queue (sec)</th>  
                                <th>User</th>
                                <th>Call Date(EST)</th>
                            </tr>
                        </thead>
                        <tbody>
                    <c:forEach var="userItem" items="${list}">
                        <tr class="">
                            <td><fmt:formatDate value="${userItem.call_date}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td><c:out value="${userItem.uniqueid}"/></td>
                            <td><c:out value="${userItem.caller_id_number}"/></td>
                            <td><c:out value="${userItem.did_description}"/></td>
                            <td><c:out value="${userItem.campaign_description}"/></td>                                        
                            <td><c:out value="${userItem.term_reason}"/></td>    
                            <td><c:out value="${userItem.status}"/></td>  
                            <c:choose>
                                <c:when test="${userItem.length_in_sec eq null}">
                                    <td><c:out value="0"/></td>  
                                </c:when>
                                <c:otherwise>
                                    <td><c:out value="${userItem.length_in_sec}"/></td>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${userItem.queue_seconds eq null}">
                                    <td><c:out value="0"/></td>  
                                </c:when>
                                <c:otherwise>
                                    <td><c:out value="${userItem.queue_seconds}"/></td>
                                </c:otherwise>
                            </c:choose>                                        

                            <td><c:out value="${userItem.user}"/></td>
                            <td><fmt:formatDate value="${userItem.convertedCallStartDt}" pattern="yyyy-MM-dd HH:mm"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>            
        </div>
    </body>
</html>