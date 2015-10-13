<%-- 
    Document   : getAgentPerformanceReport
    Created on : Sep 10, 2014, 11:05:54 AM
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
            
            <h2>Go Auto Dialer Agent Performance log Report</h2>
            <c:out value="${fn:length(list)}"/> rows found.
            <span style="padding-left: 700px;">  <a href="#" onclick="$('#excelForm').submit();">
                    <img src="images/Excel.png" alt="Download excel file" border="0" /></a><br/></span>
            <table class="data_table" width="850px">
                <thead>
                    <tr class="header">
                        <th>User Id</th>
                        <th>User Name</th>
                        <th>Active Time</th>
                        <th>Not Ready</th>  
                        <th>Idle</th>
                        <th>Wrap Time</th>
                        <c:if test="${reportTypeId == 25 || reportTypeId == 26 ||reportTypeId == 27 || reportTypeId == 28 }">
                            <th>Hold Time</th>
                            <th>Calls On Hold</th>
                        </c:if>
                        <th>Total</th>
                        <th>Calls</th>
                        <th>L Break Time</th>
                        <th>T Break Time</th>
                        <th>Quality Feed Back</th>
                        <th>TDT Break Time</th>
                        <th>BRF Break Time</th>
                        <th>Manual Calls</th>
                        <th>Login</th>
                        <th>Pause</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="listItem">
                        <tr class="">

                            <td><c:out value="${listItem.user}"/></td>
                            <td><c:out value="${listItem.full_name}"/></td>
                            <td><c:out value="${listItem.ActiveTime}"/></td>
                            <td><c:out value="${listItem.notReady}"/></td>
                            <td><c:out value="${listItem.idle}"/></td>
                            <td><c:out value="${listItem.wrapTime   }"/></td>
                            <c:if test="${reportTypeId == 25 || reportTypeId == 26 ||reportTypeId == 27 || reportTypeId == 28 }">
                                <td><c:out value="${listItem.HoldTime}"/></td>    
                                <td><c:out value="${listItem.CALLSONHOLD}"/></td>    
                            </c:if>
                            <td><c:out value="${listItem.total}"/></td>
                            <td><c:out value="${listItem.calls}"/></td>
                            <td><c:out value="${listItem.lBreakTime}"/></td>
                            <td><c:out value="${listItem.TBreakTime}"/></td>
                            <td><c:out value="${listItem.qualityFeedback}"/></td>
                            <td><c:out value="${listItem.TDTBEAKTIME}"/></td>
                            <td><c:out value="${listItem.BRFBEAKTIME}"/></td>
                            <td><c:out value="${listItem.manualCalls}"/></td>
                            <td><c:out value="${listItem.login}"/></td>
                            <td><c:out value="${listItem.pause}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
