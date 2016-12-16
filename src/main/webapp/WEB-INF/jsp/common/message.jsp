<c:if test="${not empty param.msg || not empty msg || not empty param.error || not empty error}">
    <div class="row">
        <div class="col-md-12">
            <c:if test="${not empty param.msg}">
            <span class="text-info"><spring:message code="${param.msg}" text="${param.msg}"/></span><br/><br/>
            </c:if>
            <c:if test="${not empty msg}">
                <span class="text-info"><spring:message code="${msg}" text="${msg}"/></span><br/><br/>
            </c:if>
            <c:if test="${not empty error}">
                <span class="text-danger"><spring:message code="${error}" text="${error}"/></span><br/><br/>
            </c:if>
            <c:if test="${not empty param.error}">
                <span class="text-danger"><spring:message code="${param.error}" text="${param.error}"/></span><br/><br/>
            </c:if>
        </div>
    </div>
</c:if>