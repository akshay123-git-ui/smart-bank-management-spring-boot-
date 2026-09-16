<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="_header.jsp" %>

<div class="page-head">

    <h2>Account Services</h2>

    <p class="muted">
        Demo banking operations for your portfolio application.
    </p>

</div>

<c:if test="${not empty error}">

    <div class="alert alert-error">
        ${error}
    </div>

</c:if>

<c:if test="${not empty success}">

    <div class="alert alert-success">
        ${success}
    </div>

</c:if>


<div class="two-col">

    <!-- Deposit -->

    <div class="card">

        <h3>Deposit Money</h3>

        <p class="muted">
            Adds demo funds to your account and records a credit transaction.
        </p>

        <form method="post"
              action="${pageContext.request.contextPath}/banking/deposit">

            <label>Amount (₹)</label>

            <input type="number"
                   name="amount"
                   min="1"
                   step="0.01"
                   required>

            <label>Description</label>

            <input type="text"
                   name="description"
                   placeholder="Salary / cash deposit">

            <button type="submit">
                Deposit
            </button>

        </form>

    </div>


    <!-- Withdraw -->

    <div class="card">

        <h3>Withdraw Money</h3>

        <p class="muted">
            Withdraw demo funds if sufficient balance is available.
        </p>

        <form method="post"
              action="${pageContext.request.contextPath}/banking/withdraw">

            <label>Amount (₹)</label>

            <input type="number"
                   name="amount"
                   min="1"
                   step="0.01"
                   required>

            <label>Description</label>

            <input type="text"
                   name="description"
                   placeholder="ATM withdrawal">

            <button type="submit">
                Withdraw
            </button>

        </form>

    </div>

</div>


<!-- Current Balance -->

<div class="card">

    <h3>Current balance</h3>

    <div class="balance">

        ₹

        <fmt:formatNumber
            value="${account.balance}"
            pattern="#,##0.00"/>

    </div>

</div>


<%@ include file="_footer.jsp" %>