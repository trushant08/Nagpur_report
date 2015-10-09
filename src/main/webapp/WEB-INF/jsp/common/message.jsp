<c:if test="${not empty param.msg}">
    <span style="color: green"><spring:message code="${param.msg}" text="${param.msg}"/></span>
</c:if>
<c:if test="${not empty msg}">
    <span style="color: green"><spring:message code="${msg}" text="${msg}"/></span>
</c:if>
<c:if test="${not empty param.error}">
    <span style="color: red"><spring:message code="${param.error}" text="${param.error}"/></span>
</c:if>