


<div id="navbar">
    <div id="dispIcons">
        <a title="Home Page" href="index.htm" target="_self"><img src="images/icon_home.png" width="16" height="19" border="0" style="float:left" alt="Home Page" /></a>
        <a title="Change Password" href="changePassword.htm" target="_self"><img src="images/icon_password.png" border="0" width="16" height="19" style="float:left" alt="Change Password" /></a>
        <a title="Logout" href="j_spring_security_logout" target="_self"><img src="images/icon_logout.png" width="20" border="0" height="19" style="float:left" alt="" /></a>
    </div>
    <div id="dispClock">
        <script type="text/javascript" language="JavaScript">show_clock();</script>
    </div>
    <div id='menubar'>
        <ul class='sf-menu'>
            <sec:authorize ifAnyGranted="ROLE_BF_ADMIN">
                <li class='current'>   
                    <a href='#'>Admin</a>
                    <ul> 
                        <li>
                            <a href='#'>Report Type</a>
                            <ul>
                                <li> <a href='reportTypeAdd.htm'>Add Report Type</a></li> 
                                <li> <a href='reportTypeList.htm'>List Report Type</a></li>
                                <li> <a href='mapReportTypeGroup.htm'>Add mapping between report type and group</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href='#'>Group</a>
                            <ul>
                                <li> <a href='groupAdd.htm'>Add Group</a></li>
                                <li> <a href='groupList.htm'>List Group</a></li>
                                <li> <a href='mapGroupService.htm'>Add mapping between group and service</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href='#'>Service</a>
                            <ul>
                                <li> <a href='serviceAdd.htm'>Add Service</a></li>
                                <li> <a href='serviceList.htm'>List Service</a></li>

                            </ul>
                        </li>
                        <li>
                            <a href='#'>User</a>
                            <ul>
                                <li> <a href="addUser.htm">Add User</a></li>
                                <li> <a href="userList.htm">List User</a></li>

                            </ul>
                        </li>       
                    </ul>
                </li>
            </sec:authorize>

            <sec:authorize ifAnyGranted="ROLE_BF_ADMIN,ROLE_BF_SHOW_REPORT,ROLE_BF_UPLOAD_MARICO_LEAD">
                <li class='current'>
                    <a href='#'>Report</a>
                    <ul> 
                        <sec:authorize ifAnyGranted="ROLE_BF_ADMIN,ROLE_BF_ASTERISK5">
                            <li> <a href='goAutoDialerReport.htm?id=1'>Nagpur GoAuto Dialer Report (5)</a></li>
                        </sec:authorize>
                        <sec:authorize ifAnyGranted="ROLE_BF_ADMIN,ROLE_BF_ASTERISK14">
                            <li> <a href='goAutoDialerReport.htm?id=2'>Nagpur GoAuto Dialer Report (14)</a></li>
                        </sec:authorize>
                        <sec:authorize ifAnyGranted="ROLE_BF_ADMIN,ROLE_BF_ASTERISK6">
                            <li> <a href='goAutoDialerReport.htm?id=3'>Nagpur GoAuto Dialer Report (6)</a></li>
                        </sec:authorize>
                        <sec:authorize ifAnyGranted="ROLE_BF_ADMIN,ROLE_BF_ASTERISK15">
                            <li> <a href='goAutoDialerReport.htm?id=4'>Nagpur GoAuto Dialer Report (15)</a></li>
                        </sec:authorize>
                        <sec:authorize ifAnyGranted="ROLE_BF_ADMIN,ROLE_BF_ASTERISK16">
                            <li> <a href='goAutoDialerReport.htm?id=5'>Nagpur GoAuto Dialer Report (16)</a></li>
                        </sec:authorize>
                        <sec:authorize ifAnyGranted="ROLE_BF_ADMIN,ROLE_BF_ASTERISK_ECOM">
                            <li> <a href='goAutoDialerReport.htm?id=6'>Nagpur GoAuto Dialer Report (Ecom)</a></li>
                        </sec:authorize>
                        <sec:authorize ifAnyGranted="ROLE_BF_ADMIN,ROLE_BF_MARICO_DSR_REPORT">
                            <li> <a href='maricoDialerReport.htm'>Marico Dialer Lead Report</a></li>
                        </sec:authorize>
                        <sec:authorize ifAnyGranted="ROLE_BF_ADMIN,ROLE_BF_OPEN_DSR_LEAD_REPORT">
                            <li> <a href='openDSRLeadReport.htm'>Open DSR Leads</a></li>
                        </sec:authorize>
                        <sec:authorize ifAnyGranted="ROLE_BF_UPLOAD_MARICO_LEAD">
                            <li> <a href='uploadMaricoLeads.htm'>Upload Marico DSR Leads</a></li>
                        </sec:authorize>
                    </ul>
                </li>
            </sec:authorize>
        </ul>
    </div>
    <div style="padding-top: 10px;padding-left: 20px"><%@include file="common/message.jsp" %></div>
</div>
