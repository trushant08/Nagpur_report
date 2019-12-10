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
            <span style="padding-left: 1000px;">  <a href="#" onclick="$('#excelForm').submit();">
                    <img src="images/Excel.png" alt="Download excel file" border="0" /></a><br/></span>
            <table class="data_table" width="1150px">
                <thead>
                    <tr class="header">
                        <th>User Id</th>
                        <th>User Name</th>
                        <th>Active Time</th>
                        <th>Not Ready</th>  
                        <th>Idle</th>
                        <th>Wrap Time</th>
                        <th>Total</th>
                        <th>Calls</th>
                        <th>L Break Time</th>
                        <th>T Break Time</th>
                        <th>Bio Break</th>
                        <th>Quality Feed Back</th>
                        <th>TDT Break Time</th>
                        <th>BRF Break Time</th>
                        <th>Manual Calls</th>
                        <th>Login</th>
                        <th>NXDIAL</th>
                        <th>Pause</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="listItem">
                        <tr class="">
                            <td><c:out value="${listItem.user}"/></td>
                            <td><c:out value="${listItem.FullName}"/></td>
                            <td><c:out value="${listItem.ActiveTime}"/></td>
                            <td><c:out value="${listItem.NotReady}"/></td>
                            <td><c:out value="${listItem.Idle}"/></td>
                            <td><c:out value="${listItem.wrapTime}"/></td>
                            <td><c:out value="${listItem.Total}"/></td>
                            <td><c:out value="${listItem.Calls}"/></td>
                            <td><c:out value="${listItem.LbBreakTime}"/></td>
                            <td><c:out value="${listItem.TbBreakTime}"/></td>
                            <td><c:out value="${listItem.BioBreak}"/></td>
                            <td><c:out value="${listItem.QfbBreakTime}"/></td>
                            <td><c:out value="${listItem.TdtBreakTime}"/></td>
                            <td><c:out value="${listItem.BrfBreakTime}"/></td>
                            <td><c:out value="${listItem.ManualCalls}"/></td>
                            <td><c:out value="${listItem.Login}"/></td>
                            <td><c:out value="${listItem.NxDial}"/></td>
                            <td><c:out value="${listItem.pause}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
