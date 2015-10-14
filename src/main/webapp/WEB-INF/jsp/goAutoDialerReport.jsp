<%-- 
    Document   : goAutoDialerReport
    Created on : 12 Sep, 2015, 12:09:10 PM
    Author     : shrutika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="common/script.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GoAuto Dialer Report</title>
        <%@include file="common/head.jsp" %>

        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
        <script> $1_7_2 = jQuery.noConflict();</script>
        <script type="text/javascript" src="js/jquery-ui-1.8.20.custom.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>

        <script>    
            $1_7_2(function() {
                $1_7_2( "#startDate" ).datetimepicker({dateFormat: 'yy-mm-dd'});
                $1_7_2( "#endDate" ).datetimepicker({dateFormat: 'yy-mm-dd'});
            });

        </script>
        <script type="text/javascript">
    
            function showGroupList(){
                var reportTypeId=document.getElementById("reportType.reportTypeId").value;
                var id = ${id};
                $.ajax({
                    data: ({'reportTypeId' : reportTypeId, 'id': id}),
                    url: "ajaxGetGroupListForReportTypeId.htm",
                    dataType: "json",
                    success:function(json){
              
                        var i=0;
                        var opt = document.getElementById("group.groupId");
                        opt.length=0;
                        var anOption1=document.createElement("OPTION");
                        opt.options.add(anOption1);
                        anOption1.text="-";
                        anOption1.value="";
                
                        for(i=0;i<json.length;i++){
                            var anOption=document.createElement("OPTION");
                            opt.options.add(anOption);
                            anOption.text=json[i].groupDesc;
                            anOption.value=json[i].groupId;
                        }
                    },
                    error: function(e){ 
                        
                    }
                });
            }
            function showServiceList(){
                var groupId=document.getElementById("group.groupId").value;
                var id = ${id};
                $.ajax({
                    data: ({'groupId' : groupId,'id': id}),
                    url: "ajaxGetServiceListForGroupId.htm",
                    dataType: "json",
                    success:function(json){
                        var opt = document.getElementById("selectedServiceIds");
                        opt.length=0;
                        for(i=0;i<json.length;i++){
                            var anOption=document.createElement("OPTION");
                            anOption.text=json[i].serviceName;
                            anOption.value=json[i].serverServiceId;
                            anOption.selected=true;
                            opt.options.add(anOption);
                        }
                    },
                    error: function(e){
                        alert("Error:  method not called ");
                    }
                });
            }


            var rules=new Array();
            rules[0]='reportType.reportTypeId|required|Select at least one report type';
            rules[1]='group.groupId|required|Select at least one group name';
       
        </script>
    </head>
    <body onLoad="yav.init('form1',rules,'inline');">
        <%@include file="header.jsp" %>
        <%@include file="menu.jsp" %>
        <div  class="in_02">
            <form:form  method="POST" id="form1" name="form1" style="margin:0; padding:0;" commandName="reportHeader">
                <fieldset>
                    <table class="formTable" style="margin:0 0 0 0; padding: 0; clear: both;">
                        <tr  style="height: 35px">
                            <td  style="height: 35px">Select Zone<br/>
                                <form:select path="zone.zoneId" class="userInput">
                                    <form:option value="0">IST</form:option>
                                    <form:option value="1">PST</form:option>
                                    <form:option value="2">EST</form:option>
                                </form:select>
                            </td><br/>&nbsp

                        <td style="height: 35px">Start Date<br/><form:input path="startDate" class="userInput"/></td>
                        <td style="height: 35px">End Date<br/><form:input path="endDate" class="userInput"/></td>

                        <td style="height: 35px" >Report <br/> 
                            <form:select path="reportType.reportTypeId" class="userInput" onchange="showGroupList();"> 
                                <form:option value="">-</form:option>
                                <c:forEach items="${reportTypeList}" var="item">
                                    <form:option value="${item.reportTypeId}">${item.reportTypeDesc}</form:option>
                                </c:forEach>
                            </form:select>
                            <span id="errorsDiv_reportType.reportTypeId"></span>
                        </td>
                        <td style="height: 35px;padding-top: 8px;" >Group <br/>
                            <form:select path="group.groupId"  class="userInput" onchange="showServiceList();">
                                <form:option value="">-</form:option>
                                <c:forEach items="${groupList}" var="item">
                                    <form:option value="${item.groupId}"> ${item.groupDesc}</form:option>
                                </c:forEach>
                            </form:select>
                            <span id="errorsDiv_group.groupId"></span>
                        </td>
                        <td style="height: 35px;padding-top: 8px;" >Services   <br/> 
                            <select id="selectedServiceIds" name="selectedServiceIds" class="userInputLong" multiple="true" >
                                <c:forEach items="${serviceList}" var="item">
                                    <c:set var="found" value="0"/>
                                    <c:forEach items="${reportHeader.service}" var="s">
                                        <c:if test="${s.serverServiceId==item.serverServiceId}"><c:set var="found" value="1"/></c:if>
                                    </c:forEach>
                                    <option <c:if test="${found==1}"> selected </c:if>value="${item.serverServiceId}">${item.serviceName}</option>
                                </c:forEach>
                            </select>
                        </td>

                        <td style="float: right; margin: 18px 0 0 0;  height: 35px; width: 100px">
                            <input class="userInputFormSubmit" type="submit" value="Go" name="Go" onclick="return yav.performCheck('form1', rules, 'inline');"/>
                            <br/>
                        </td>
                        </tr>
                    </table>
                </fieldset>
            </form:form>
            <form name="excelForm" id="excelForm" method="post" action="reportExcel.htm">
                <script type="text/javascript">
                    var groupId=document.getElementById("group.groupId").value;
                    document.getElementById("groupId").value=groupId;
                </script>
                <input type="hidden" name="zoneId" value="${zoneId}"/>
                <input type="hidden" name="startDate" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${startDate}" />"/>
                <input type="hidden" name="stopDate" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${endDate}" />"/>

                <input type="hidden" name="reportId" value="${reportTypeId}"/>
                <input type="hidden" name="groupId" value="${groupId}"/>
                <input type="hidden" name="id" value="${id}"/>
                <div id="hiddenService" style="display: none">
                    <select name="selectedServiceIds" id="selectedServiceIds" multiple>
                        <c:forEach items="${selectedServiceIds}" var="item">
                            <option selected value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                </div>
            </form>
        </div>
    </body>
</html>
