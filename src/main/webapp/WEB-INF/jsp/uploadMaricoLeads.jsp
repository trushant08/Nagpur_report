<%-- 
    Document   : uploadMaricoLeads
    Created on : 9 Dec, 2016, 1:30:28 PM
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

        <script type="text/javascript">
            var rules=new Array();
            rules[0]='multipartFile|custom|check()';
            
            function check(){
                var fileName = document.getElementById("multipartFile").value.split(".");
               
                if((fileName[fileName.length-1] != "csv")){
                    return "Please select 'csv' file."
                }else{
                    return null;
                }
            }
            
        </script>
    </head>

    <body>
        <div  class="in_02">
            <fieldset>
                <legend>Update Status</legend>
                <form:form modelAttribute="fileUpload" method="post" id="form1" name="form1" cssClass="userInputForm" enctype="multipart/form-data">
                    <spring:hasBindErrors name="fileUpload">
                        <div class="error">
                            <form:errors path="*"/>
                        </div>
                    </spring:hasBindErrors>
                    <fieldset>

                        <table class="formTable">
                            <tr>
                                <td>Select File*:<input type="file" name="multipartFile" id="multipartFile" /><br/><br/>
                                    <b>.csv file format with the following fields - </b>
                                    <ul>
                                        <li><b>Retailer Name</b></li>
                                        <li><b>Phone No</b></li>
                                        <li><b>Unique Id</b></li>
                                        <li><b>Beat Description</b></li>
                                        <li><b>Distributor Name</b></li>
                                        <li><b>Distributor Code</b></li>
                                        <li><b>List Id</b> (can not be blank,should be 656 only)</li>
                                        <li><b>Dsr Status</b></li>
                                    </ul>
                                    <br /><span id="errorsDiv_multipartFile"></span></td>
                            </tr>
                            <tr>
                                <td><input type="button" name="cancel" value="cancel" class="userInputFormSubmit" onclick="location.href='index.htm?msg=msg.actionCancelled'">
                                    <input class="userInputFormSubmit" type="submit" name="Submit" value="submit" onclick="return yav.performCheck('form1', rules, 'inline');"/></td>
                            </tr>
                        </table>
                    </fieldset>
                </form:form>
            </fieldset>
        </div>
    </body>
</html>
