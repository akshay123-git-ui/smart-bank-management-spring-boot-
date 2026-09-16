<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_header.jsp" %>

<div class="upi-pay-wrap">
    <div class="card upi-pay-card">
        <p class="eyebrow">SMARTBANK UPI</p>
        <h2>Review & Pay</h2>
        <p class="muted">Confirm the receiver before completing this demo payment.</p>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">
                <strong>${success}</strong>
                <c:if test="${not empty paidAmount}">
                    <br>Amount: ₹<fmt:formatNumber value="${paidAmount}" pattern="#,##0.00"/>
                </c:if>
            </div>
        </c:if>

        <c:if test="${not empty receiver}">
            <div class="receiver-box">
                <div class="receiver-icon">🏦</div>
                <div>
                    <small>PAYING TO</small>
                    <h3>${receiverName}</h3>
                    <p>${receiver.upiId}</p>
                    <small>A/C ending ${receiverLast4}</small>
                </div>
            </div>

            <c:if test="${empty success}">
                <form method="post" action="${pageContext.request.contextPath}/upi/pay">
                    <input type="hidden" name="upiId" value="${receiver.upiId}">

                    <label>Amount (₹)</label>
                    <input type="number" name="amount" min="1" step="0.01" placeholder="0.00" required>

                    <label>Description</label>
                    <input type="text" name="description" placeholder="e.g. Dinner payment">

                    <label>Demo UPI PIN</label>
                    <input type="password" name="pin" inputmode="numeric" maxlength="4" placeholder="Enter 4-digit PIN" required>
                    <small class="muted">For this project simulator, use <strong>1234</strong>.</small>

                    <button type="submit" class="pay-btn">Pay Now</button>
                </form>
            </c:if>
        </c:if>

        <div class="upi-pay-actions">
            <a class="btn light-btn" href="${pageContext.request.contextPath}/upi/scan">Scan Another QR</a>
            <a class="btn outline-btn" href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        </div>
    </div>
</div>

<%@ include file="_footer.jsp" %>
