<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="_header.jsp" %>

<h1>Admin Dashboard</h1>

<div class="stat-row">

    <div class="stat-box">

        <div class="value">
            ${customerCount}
        </div>

        <div class="label">
            Total Customers
        </div>

    </div>

    <div class="stat-box">

        <div class="value">
            ${pendingCount}
        </div>

        <div class="label">
            Pending Loan Requests
        </div>

    </div>

</div>

<div class="card" style="margin-top: 20px;">

    <h3>Quick Links</h3>

    <p>
        <a href="${pageContext.request.contextPath}/admin/customers">
            Manage Customers &rarr;
        </a>
    </p>

    <p style="margin-top: 8px;">
        <a href="${pageContext.request.contextPath}/admin/loans">
            Review Loan Applications &rarr;
        </a>
    </p>

    <p style="margin-top: 8px;">
        <a href="${pageContext.request.contextPath}/admin/reports">
            View Reports &rarr;
        </a>
    </p>

</div>

<%@ include file="_footer.jsp" %>