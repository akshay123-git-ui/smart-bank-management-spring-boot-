<%@ include file="_header.jsp" %>

<div class="card">
    <h2>Loan Applications</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <c:choose>
        <c:when test="${not empty loans}">
            <table>
                <tr><th>Applicant</th><th>Type</th><th>Amount</th><th>Tenure</th><th>Status</th><th>Action</th></tr>
                <c:forEach var="loan" items="${loans}">
                    <tr>
                        <td>${loan.user.fullName}</td>
                        <td>${loan.loanType}</td>
                        <td>&#8377; <fmt:formatNumber value="${loan.amount}" pattern="#,##0.00"/></td>
                        <td>${loan.tenureMonths} mo</td>
                        <td><span class="badge badge-${loan.status.toLowerCase()}">${loan.status}</span></td>
                        <td>
                            <c:if test="${loan.status == 'PENDING'}">
                                <form method="post" action="${pageContext.request.contextPath}/admin/loans/decide" style="display:inline;">
                                    <input type="hidden" name="loanId" value="${loan.id}">
                                    <input type="hidden" name="decision" value="APPROVE">
                                    <button type="submit" style="padding:4px 10px; margin:0;">Approve</button>
                                </form>
                                <form method="post" action="${pageContext.request.contextPath}/admin/loans/decide" style="display:inline;">
                                    <input type="hidden" name="loanId" value="${loan.id}">
                                    <input type="hidden" name="decision" value="REJECT">
                                    <button type="submit" style="padding:4px 10px; margin:0; background:#991b1b;">Reject</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <p>No loan applications.</p>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="_footer.jsp" %>
