<%-- 
    Document   : mapGroupService
    Created on : 29 Sep, 2015, 5:30:25 PM
    Author     : shrutika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="common/script.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="common/head.jsp" %>
        <!--autocomplete multiselect-->
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.11.4.css" />
        <script type="text/javascript" src="js/jquery-1.11.3.js"></script>
        <script> $1_11_3 = jQuery.noConflict();</script>
        <script src="js/jquery-ui.js"></script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add mapping between group and service</title>

        <style>
            .custom-combobox {
                position: relative;
                display: inline-block;
            }
            .custom-combobox-toggle {
                position: absolute;
                top: 0;
                bottom: 0;
                margin-left: -1px;
                padding: 0;
            }
            .custom-combobox-input {
                margin: 0;
                padding: 5px 10px;
            }

        </style>

        <script>
            (function($1_11_3) {
                $1_11_3.widget( "custom.combobox", {
                    _create: function() {
                        this.wrapper = $1_11_3( "<span>" )
                        .addClass( "custom-combobox" )
                        .insertAfter( this.element );
                        this.element.hide();
                        this._createAutocomplete();
                        this._createShowAllButton();
                    },
                    _createAutocomplete: function() {
                        var selected = this.element.children( ":selected" ),
                        value = selected.val() ? selected.text() : "";
                        this.input = $1_11_3( "<input>" )
                        .appendTo( this.wrapper )
                        .val( value )
                        .attr( "title", "" )
                        .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
                        .autocomplete({
                            delay: 0,
                            minLength: 0,
                            source: $1_11_3.proxy( this, "_source" )
                        })
                        .tooltip({
                            tooltipClass: "ui-state-highlight"
                        });
                        this._on( this.input, {
                            autocompleteselect: function( event, ui ) {
                                ui.item.option.selected = true;
                                this._trigger( "select", event, {
                                    item: ui.item.option
                                });
                            },
                            autocompletechange: "_removeIfInvalid"
                        });
                    },
                    _createShowAllButton: function() {
                        var input = this.input,
                        wasOpen = false;
                        $1_11_3( "<a>" )
                        .attr( "tabIndex", -1 )
                        .attr( "title", "Show All Items" )
                        .tooltip()
                        .appendTo( this.wrapper )
                        .button({
                            icons: {
                                primary: "ui-icon-triangle-1-s"
                            },
                            text: false
                        })
                        .removeClass( "ui-corner-all" )
                        .addClass( "custom-combobox-toggle ui-corner-right" )
                        .mousedown(function() {
                            wasOpen = input.autocomplete( "widget" ).is( ":visible" );
                        })
                        .click(function() {
                            input.focus();
                            // Close if already visible
                            if ( wasOpen ) {
                                return;
                            }
                            // Pass empty string as value to search for, displaying all results
                            input.autocomplete( "search", "" );
                        });
                    },
                    _source: function( request, response ) {
                        var matcher = new RegExp( $1_11_3.ui.autocomplete.escapeRegex(request.term), "i" );
                        response( this.element.children( "option" ).map(function() {
                            var text = $1_11_3( this ).text();
                            if ( this.value && ( !request.term || matcher.test(text) ) )
                                return {
                                    label: text,
                                    value: text,
                                    option: this
                                };
                        }) );
                    },
                    _removeIfInvalid: function( event, ui ) {
                        // Selected an item, nothing to do
                        if ( ui.item ) {
                            return;
                        }
                        // Search for a match (case-insensitive)
                        var value = this.input.val(),
                        valueLowerCase = value.toLowerCase(),
                        valid = false;
                        this.element.children( "option" ).each(function() {
                            if ( $1_11_3( this ).text().toLowerCase() === valueLowerCase ) {
                                this.selected = valid = true;
                                return false;
                            }
                        });
                        // Found a match, nothing to do
                        if ( valid ) {
                            return;
                        }
                        // Remove invalid value
                        this.input
                        .val( "" )
                        .attr( "title", value + " didn't match any item" )
                        .tooltip( "open" );
                        this.element.val( "" );
                        this._delay(function() {
                            this.input.tooltip( "close" ).attr( "title", "" );
                        }, 2500 );
                        this.input.autocomplete( "instance" ).term = "";
                    },
                    _destroy: function() {
                        this.wrapper.remove();
                        this.element.show();
                    }
                });
            })( jQuery );
            $1_11_3(function() {
                $1_11_3( "#combobox" ).combobox({
                    select:function(event, ui){
                        showServiceList();
                    }
                });
            });
        </script>
        <script type="text/javascript">
            function addOption(selectBox,text,value){
                var option=document.createElement('OPTION');
                option.text=text;
                option.value=value;
                selectBox.options.add(option);
            }
            function moveLeft(leftBox, rightBox){
                var removeElement=0;
                for(var index=0;index<rightBox.length+removeElement;index++){
                    if(rightBox.options[index-removeElement].selected==true){
                        addOption(leftBox, rightBox.options[index-removeElement].text, rightBox.options[index-removeElement].value);
                        rightBox.remove(index-removeElement);
                        removeElement++;
                    }
                }
            }
            function moveRight(leftBox, rightBox){
                var removeElement=0;
                for(var index=0;index<leftBox.length+removeElement;index++){
                    if(leftBox.options[index-removeElement].selected==true){
                        addOption(rightBox, leftBox.options[index-removeElement].text, leftBox.options[index-removeElement].value);
                        leftBox.remove(index-removeElement);
                        removeElement++;
                    }
                }
            }
            function addAssign(){
                var assignFrom=document.getElementById("assignFrom");
                var assignTo=document.getElementById("assignTo");
                moveRight(assignFrom,assignTo);
            }
            function removeAssign(){
                var assignFrom=document.getElementById("assignFrom");
                var assignTo=document.getElementById("assignTo");
                moveLeft(assignFrom,assignTo);
            }
            function submitUsers(){
                document.getElementById("finalAssignServices").value='';
                var assignUser='';
                
                var assignTo=document.getElementById("assignTo");
                //
                if(assignTo.length==0 ){
                    
                    return 'Please select service list';
                }
                
                for(var i=0;i<assignTo.length;i++){
                    if(assignUser==''){
                        assignUser+=assignTo.options[i].value;
                    }else{
                        assignUser+=','+assignTo.options[i].value;
                    }
                }
                document.getElementById("finalAssignServices").value=assignUser;
               
                return null;
            }
                        
            var rules =new Array();
            rules[0]='assignTo|custom|submitUsers()';
            rules[1]='serverTypeId|required|Select at least one server type';
            
            //Ajax:
            function showServiceList(){
                var groupId=$( "#combobox" ).val();
                var serverTypeId=document.getElementById("serverTypeId").value;
                $.ajax({
                    data: ({'groupId' : groupId, 'serverTypeId' : serverTypeId}),
                    url: "ajaxGetServiceListByGroupId.htm",
                    dataType: "json",
                    success:function(json){
                        var i=0;
                        var opt = document.getElementById("assignFrom");                       
                        opt.length=0;
                                                                
                        for(i=0;i<json.nonAssignedList.length;i++){
                            var anOption=document.createElement("OPTION");
                            opt.options.add(anOption);
                            anOption.text=json.nonAssignedList[i].serviceName;
                            anOption.value=json.nonAssignedList[i].serviceId;
                        }
                        
                        var j=0;
                        var opt1 = document.getElementById("assignTo");
                        opt1.length=0;
                                        
                        for(j=0;j<json.assignedList.length;j++){
                            var anOption=document.createElement("OPTION");
                            opt1.options.add(anOption);
                            anOption.text=json.assignedList[j].serviceName;
                            anOption.value=json.assignedList[j].serviceId;
                        }
                    },
                    error: function(e){ 
                        alert("Error:  method not called ");
                    }
                });
            }
            
            function showGroupList(){
                var serverTypeId=document.getElementById("serverTypeId").value;
                $.ajax({
                    data: ({'serverTypeId' : serverTypeId}),
                    url: "ajaxGetGroupListForServerTypeId.htm",
                    dataType: "json",
                    success:function(json){
                        var opt = document.getElementById("combobox");
 
                        opt.length=0;
                        for(i=0;i<json.length;i++){
                            var anOption=document.createElement("OPTION");
                            opt.options.add(anOption);
                            anOption.text=json[i].groupDesc;
                            anOption.value=json[i].groupId;
                        }
                    },
                    error: function(e){ 
                        alert("Error:  method not called ");
                    }
                });
            }
        </script>

    </head>
    <body onLoad="yav.init('form1',rules,'inline');">
        <%@include file="header.jsp" %>
        <%@include file="menu.jsp" %>

        <div class="in_02" >

            <form id="form1" name="form1" method="POST">
                <fieldset>
                    <legend>Add mapping between group and service</legend>

                    <div class="span3" style="float: left">
                        <label>Server Type:</label>
                        <div class="input-append">
                            <select id ="serverTypeId" name="serverTypeId" class="userInput" onchange="showGroupList();"> 
                                <option value="">-</option>
                                <c:forEach items="${serverTypeList}" var="serverType">
                                    <option value="${serverType.serverTypeId}">${serverType.serverTypeDesc}</option>
                                </c:forEach>
                            </select>
                            <span id="errorsDiv_serverTypeId"></span>
                        </div>
                    </div>

                    <div class="ui-widget">
                        <%@include file="common/message.jsp" %>
                        <label>Group Name</label><br>
                        <select id="combobox" name="groupId"> 
                            <option value="">-</option>
                            <c:forEach items="${groupList}" var="group">
                                <option value="${group.groupId}">${group.groupDesc}</option>
                            </c:forEach>
                        </select>
                    </div><br>

                    <div class="span3" style="float:left">
                        <label>Non Selected Service List:</label>
                        <div class="input-append">
                            <select id="assignFrom" name="assignFrom" multiple size="8" style="width: 220px;">
                                <c:forEach items="${serviceList.nonAssignedList}" var="nonAssignedService">
                                    <option value="${nonAssignedService.serviceId}">${nonAssignedService.serviceName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="span1"  style="float:left; padding-left: 20px;padding-top: 15px">
                        <label>&nbsp;</label>
                        <label>&nbsp;</label>
                        <div class="input-append">
                            <input type="button" id="gtAssign" name="gtAssign" value=">" class="span2" style="width: 40px;background-color: #02565b;color: #fff;" onclick="addAssign();" onclick="return yav.performCheck('form1', rules, 'inline');"/>
                            <label>&nbsp;</label>
                            <input type="button" id="ltAssign" name="ltAssign" value="<" class="span2" style="width: 40px;background-color: #02565b;color: #fff;" onclick="removeAssign();"/>
                        </div>
                    </div>


                    <div class="span4" style="float:left; padding-left: 20px">
                        <label>Selected Service List:</label>
                        <div class="input-append">
                            <select id="assignTo" name="assignTo" multiple size="8" style="width: 220px;">
                                <c:forEach items="${serviceList.assignedList}" var="assignedService">
                                    <option value="${assignedService.serviceId}">${assignedService.serviceName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <span>&nbsp;</span><span id="errorsDiv_assignTo">&nbsp;</span>
                    </div>
                    <input type="hidden" id="finalAssignServices" name="finalAssignServices"/>

                    <div class="span3">
                        <label>&nbsp;</label>
                        <div class="input-append">
                            <input class="userInputFormSubmit" type="submit" value="Save" name="btnSubmit" onclick="yav.performCheck('form1', rules, 'inline');return (submitUsers()==null); " />
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </body>
</html>