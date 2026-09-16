<%@ include file="_header.jsp" %>

<h1>Reports</h1>

<div class="stat-row">
    <div class="stat-box">
        <div class="value">${totalCustomers}</div>
        <div class="label">Total Customers</div>
    </div>
    <div class="stat-box">
        <div class="value">${approvedCount}</div>
        <div class="label">Loans Approved</div>
    </div>
    <div class="stat-box">
        <div class="value">${rejectedCount}</div>
        <div class="label">Loans Rejected</div>
    </div>
    <div class="stat-box">
        <div class="value">${pendingCount}</div>
        <div class="label">Loans Pending</div>
    </div>
</div>

<div class="card" style="margin-top: 20px;">
    <h3>Total Approved Loan Value</h3>
    <div class="balance">&#8377; <fmt:formatNumber value="${totalApprovedAmount}" pattern="#,##0.00"/></div>
</div>

<div class="card">
    <p style="font-size: 13px; color: #6b7280;">
        This is a simplified in-memory aggregation for learning purposes.
        A production reporting feature would typically use a GROUP BY query
        (e.g. transactions grouped by month) rather than looping in Java -
        a good next step: add a native/HQL query to LoanDao or TransactionDao
        that returns monthly totals directly from MySQL.
    </p>
</div>

<%@ include file="_footer.jsp" %>
