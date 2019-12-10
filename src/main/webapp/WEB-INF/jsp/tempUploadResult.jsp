<%-- 
    Document   : tempUploadResult
    Created on : 9 Dec, 2016, 5:30:31 PM
    Author     : shrutika
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
        <div id="page_wrapper">
            <div class="container-fluid">
                <div class="col-md-12 margin-s"><%@include file="common/message.jsp" %></div>
                <form action="saveFeed.htm" name="form1" method="post" >

                    <div class="col-md-12 scrollable">
                        <h5>Bad data list</h5>
                        <table class="data_table">
                            <thead>
                                <tr class="header">
                                    <th>Line No</th>
                                    <th>Retailer Name</th>
                                    <th>Phone No</th>
                                    <th>Unique Id</th>
                                    <th>Beat Description</th>
                                    <th>Distributor Name</th>
                                    <th>Distributor Code</th>
                                    <th>List Id</th>
                                    <th>Dsr Status</th>
                                    <th style="width: 150px">Reason</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="showButton" value="true"/>
                                <c:set var="goodCnt" value="0"/>
                                <c:set var="badCnt" value="0"/>
                                <c:forEach items="${lst}" var="item">
                                    <c:set var="showButton" value="${showButton && item.status}"/>
                                    <c:set var="rowColor" value=""/>
                                    <c:choose>
                                        <c:when test="${item.status==false}">
                                            <c:set var="badCnt" value="${badCnt+1}"/>
                                            <tr>
                                                <td>${item.lineNo}</td>
                                                <td>${item.retailerName}</td>
                                                <td>${item.phoneNo}</td>
                                                <td>${item.uniqueId}</td>
                                                <td>${item.beatDescription}</td>
                                                <td>${item.distributorName}</td>
                                                <td>${item.distributorCode}</td>
                                                <td>${item.list_id}</td>
                                                <td>${item.dsr_status}</td>
                                                <td>${item.reason}</td>
                                            </tr>    
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="goodCnt" value="${goodCnt+1}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </tbody>
                        </table>
                        <br/><h5>Summary</h5>
                        <table class="data_table">
                            <thead>
                                <tr class="header">
                                    <td width="80px">Total data</td>
                                    <td width="80px">Good</td>
                                    <td width="80px">Bad</td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="">
                                    <td>${fn:length(lst)}</td>
                                    <td>${goodCnt}</td>
                                    <td>${badCnt}</td>
                                </tr>
                            </tbody>
                        </table>
                        <h6 style="color: red">Please note only the good data will be uploaded. Please copy the Bad data list now. You will need to fix this and upload it again later.</h6>
                        <div class="margin-s">
                            <c:if test="${goodCnt>0}"><input type="Submit" class="btn btn-primary" value="Submit" name ="submit" ></c:if>
                            <input type="Submit" value="Cancel" name="_cancel" class="btn btn-primary">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>