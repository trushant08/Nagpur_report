<%-- 
    Document   : insertOpenLeadIntoDialer
    Created on : 18 Jul, 2014, 1:40:09 PM
    Author     : citius
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="common/script.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report - Management</title>
        <%@include file="common/head.jsp" %>

        <script type="text/javascript">
            function toggleCheckForAll() {
                var field = document.getElementsByName("check");
                if (document.getElementById("toggleCheck").checked) {
                    for (i = 0; i < field.length; i++) {
                        field[i].checked=true;
                    }
                } else {
                    for (i = 0; i < field.length; i++) {
                        field[i].checked = false ;
                    }
                }
            }
                
            function CloseWindow() {
                window.close();
                //        window.opener.location.reload(true);
            }
        </script>
</head>

<body onunload="opener.location.reload(true);">
    <div class="in_02">
        <form id="form2"  name="form2" method="post" action="insertOpenLeadIntoDialer.htm" cssClass="userInputForm">
            <fieldset>
                <legend>Marico DSR Leads</legend>

                <input type="hidden" value="${id}" name="id">
                <input type="hidden" value="${desc}" name="desc">
                <table class="data_table" width="850px">
                    <thead>
                        <tr class="header">
                            <td align="center" width="30px"><input type="checkbox" id="toggleCheck" name="toggleCheck" onchange="toggleCheckForAll();"/></td>
                            <td>Id</td>
                            <td>Retailer Name</td>
                            <td>Beat Description</td>
                            <td>Distributor Name</td>
                            <td>Distributor code</td>
                            <td>DSR Status</td>  
                            <td>Phone No</td> 
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${openLeadList}" var="maricoLeadItem">
                            <tr class="">
                                <td align="center"><input type="checkbox" id="check" name="check" value="${maricoLeadItem.maricoLeadId}"/></td>
                                <td><c:out value="${maricoLeadItem.maricoLeadId}"/></td>
                                <td><c:out value="${maricoLeadItem.retailerName}"/></td>
                                <td><c:out value="${maricoLeadItem.beatDiscription}"/></td>
                                <td><c:out value="${maricoLeadItem.distributorName}"/></td>
                                <td><c:out value="${maricoLeadItem.distributorCode}"/></td>
                                <td><c:out value="${maricoLeadItem.dsrStatus}"/></td>
                                <td><c:out value="${maricoLeadItem.phoneNo}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <br/><br/><br/>
                <input class="userInputFormSubmit" type="submit" value="Push to Dialer" name="submit" /> 
                <!--                <input  type="submit" name="submit" value="Export lead for Nagpur" class="userInputFormSubmit"> 
                                <input  type="submit" name="submit" value="Dialed for Nagpur" class="userInputFormSubmit">-->
                <input class="userInputFormSubmit" type="submit" value="Reject" name="submit" /> 
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td><input  type="button" name="cancel" value="Cancel" class="userInputFormSubmit" onclick="javascript: return CloseWindow();"></td>
            </fieldset>
        </form>
    </div>
    <form name="formExcel" method="post" action="leadsforNagpurExcel.htm">
        ${check}
        <input type="hidden" value="${id}" name="id"/>
        <input type="hidden" value="${desc}" name="desc"/>
        <input type="hidden" value="${check}" name="check"/>
    </form> 
</body>
</html>
