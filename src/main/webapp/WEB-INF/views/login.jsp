<%@ include file="_header.jsp" %>

<div class="card" style="max-width: 400px; margin: 0 auto;">
    <h2>Login</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <label>Email</label>
        <input type="email" name="email" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <button type="submit">Login</button>
    </form>

    <p style="margin-top: 16px; font-size: 14px;">
        Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a>
    </p>

    <p style="margin-top: 8px; font-size: 12px; color: #6b7280;">
        Tip: create an admin user directly in the database (see README) to access the admin dashboard.
    </p>
</div>

<%@ include file="_footer.jsp" %>
