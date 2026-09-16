<%@ include file="_header.jsp" %>

<div class="card" style="max-width: 450px; margin: 0 auto;">
    <h2>Transfer Funds</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/transfer">
        <label>Recipient Account Number or UPI ID</label>
        <input type="text" name="recipientIdentifier" required>

        <label>Amount (&#8377;)</label>
        <input type="number" name="amount" step="0.01" min="1" required>

        <label>Description (optional)</label>
        <input type="text" name="description" placeholder="e.g. Rent payment">

        <button type="submit">Send Money</button>
    </form>
</div>

<%@ include file="_footer.jsp" %>
