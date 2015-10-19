<%-- 
    Document   : maricoDialerReport
    Created on : 12 Jun, 2014, 12:26:01 PM
    Author     : citius
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="common/script.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="common/head.jsp" %>
        <title>Marico Dialer Report</title>

        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
        <script> $1_7_2 = jQuery.noConflict();</script>
        <script type="text/javascript" src="js/jquery-ui-1.8.20.custom.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>

        <script>    
            $1_7_2(function() {
                $1_7_2( "#startDate" ).datepicker({dateFormat: 'yy-mm-dd'});
                $1_7_2( "#endDate" ).datepicker({dateFormat: 'yy-mm-dd'});
            });
        </script>
    </head>

    <body>
        <%@include file="header.jsp" %>
        <%@include file="menu.jsp" %>


        <div class="in_02">
            <form   method="post" name="form1" id="form1" action="maricoDialerReport.htm" >
                <fieldset style="width:400px">
                    <legend>Marico DSR Leads</legend>
                    <table>
                        <tr  style="height: 15px">
                            <td >Start Date<br/><input type="text" id="startDate" name="startDate" value="${startDate}" class="userInput"></td>
                            <td>&nbsp;</td>
                            <td >End Date<br/><input type="text" id="endDate" name="endDate" value="${endDate}" class="userInput"/></td><td>&nbsp;</td>
                            <td style="float: right; margin: 18px 0 0 0;  height: 15px">
                                <input class="userInputFormSubmit" type="submit" value="Go" name="Go"/>
                            </td>
                        </tr>
                    </table>
                </fieldset>
                <br/><br/>
                <c:if test="${param.msg != null}">
                    <div class="error">
                        ${param.msg}<br/><br/>
                    </div>
                </c:if>
                <table class="data_table" width="850px">
                    <thead>
                        <tr class="header">
                            <td>Id</td>
                            <td>Retailer Name</td>
                            <td>Beat Description</td>
                            <td>Distributor Name</td>
                            <td>Distributor code</td>
                            <td>DSR Status</td>  
                            <td>Phone No</td> 
                            <td>Status</td>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${maricoLeadList}" var="maricoLeadItem">
                            <tr class=" ">
                                <td><c:out value="${maricoLeadItem.maricoLeadId}"/></td>
                                <td><c:out value="${maricoLeadItem.retailerName}"/></td>
                                <td><c:out value="${maricoLeadItem.beatDiscription}"/></td>
                                <td><c:out value="${maricoLeadItem.distributorName}"/></td>
                                <td><c:out value="${maricoLeadItem.distributorCode}"/></td>
                                <td><c:out value="${maricoLeadItem.dsrStatus}"/></td>
                                <td><c:out value="${maricoLeadItem.phoneNo}"/></td>
                                <td>  <c:choose>
                                        <c:when test="${maricoLeadItem.leadStatus == 0}">
                                            New Lead
                                        </c:when>
                                        <c:when test="${maricoLeadItem.leadStatus == 1}">
                                            Pushed to Dialer</c:when>
                                        <c:otherwise>
                                            Rejected
                                        </c:otherwise>
                                    </c:choose></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
    </body>
</html>
