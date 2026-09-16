<%@ include file="_header.jsp" %>

<div class="card" style="max-width: 450px; margin: 0 auto;">
    <h2>Create an Account</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/register">
        <label>Full Name</label>
        <input type="text" name="fullName" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Phone</label>
        <input type="text" name="phone" required>

        <label>Password</label>
        <input type="password" name="password" required minlength="6">

        <button type="submit">Register</button>
    </form>

    <p style="margin-top: 16px; font-size: 14px;">
        Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a>
    </p>
</div>

<%@ include file="_footer.jsp" %>
