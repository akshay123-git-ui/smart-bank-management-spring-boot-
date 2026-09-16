<%@ include file="_header.jsp" %>

<div class="card" style="max-width: 450px; margin: 0 auto;">
    <h2>Apply for a Loan</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/loans/apply">
        <label>Loan Type</label>
        <select name="loanType" required>
            <option value="PERSONAL">Personal Loan</option>
            <option value="HOME">Home Loan</option>
            <option value="EDUCATION">Education Loan</option>
            <option value="VEHICLE">Vehicle Loan</option>
        </select>

        <label>Amount (&#8377;)</label>
        <input type="number" name="amount" step="0.01" min="1000" required>

        <label>Tenure (months)</label>
        <input type="number" name="tenureMonths" min="1" max="360" required>

        <button type="submit">Submit Application</button>
    </form>

    <p style="margin-top: 12px; font-size: 14px;">
        <a href="${pageContext.request.contextPath}/dashboard">&larr; Back to Dashboard to track status</a>
    </p>
</div>

<%@ include file="_footer.jsp" %>
