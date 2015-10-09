<script type="text/javascript" language="JavaScript" src="js/liveclock.js"></script>
<sec:authentication property="principal" var="tuser" scope="request" />

<div id="toplogo_container">
    <!--    <div id="dispKayaLogo"></div>-->
    <div id="dispTitle">Report - Management 
    </div>
    <div id="poweredby"></div>
    <div id="dispUser">
        <b>User -</b> 
        <c:out value="${tuser.username}"/>
    </div>
</div>

<!-- logo container-->