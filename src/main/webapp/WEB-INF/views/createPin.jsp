<%@ include file="_header.jsp" %>

<div class="card" style="max-width: 420px; margin: 50px auto; text-align: center;">
    <h2>Create App PIN</h2>
    <p class="muted">Create a 4-digit PIN to unlock SmartBank after login.</p>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/app-pin/create">
        <label>4-Digit App PIN</label>
        <input type="password" name="pin" inputmode="numeric" pattern="[0-9]{4}"
               maxlength="4" minlength="4" autocomplete="new-password" required>

        <label>Confirm App PIN</label>
        <input type="password" name="confirmPin" inputmode="numeric" pattern="[0-9]{4}"
               maxlength="4" minlength="4" autocomplete="new-password" required>

        <button type="submit">Create PIN &amp; Continue</button>
    </form>

    <p style="margin-top: 16px; font-size: 12px; color: #6b7280;">
        This is your SmartBank app-unlock PIN. It is separate from your UPI payment PIN.
    </p>
</div>

<%@ include file="_footer.jsp" %>
