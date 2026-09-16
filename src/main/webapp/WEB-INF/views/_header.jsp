<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>SmartBank | Digital Banking</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/style.css">

</head>

<body>

<nav>

    <div class="brand">

        &#127963; <span>SmartBank</span>

    </div>

    <div class="nav-links">

        <c:if test="${not empty sessionScope.userId && sessionScope.role == 'CUSTOMER'}">

            <a href="${pageContext.request.contextPath}/dashboard">
                Home
            </a>

            <a href="${pageContext.request.contextPath}/transfer">
                Send Money
            </a>

            <a href="${pageContext.request.contextPath}/transactions">
                Transactions
            </a>

            <a href="${pageContext.request.contextPath}/beneficiaries">
                Beneficiaries
            </a>

            <a href="${pageContext.request.contextPath}/savings">
                Savings
            </a>

            <a href="${pageContext.request.contextPath}/loans/apply">
                Loans
            </a>

            <a href="${pageContext.request.contextPath}/emi">
                EMI
            </a>

            <a href="${pageContext.request.contextPath}/notifications">
                &#128276;
            </a>

            <a href="${pageContext.request.contextPath}/logout">
                Logout
            </a>

        </c:if>


        <c:if test="${not empty sessionScope.userId && sessionScope.role == 'ADMIN'}">

            <a href="${pageContext.request.contextPath}/admin/dashboard">
                Admin
            </a>

            <a href="${pageContext.request.contextPath}/admin/customers">
                Customers
            </a>

            <a href="${pageContext.request.contextPath}/admin/loans">
                Loans
            </a>

            <a href="${pageContext.request.contextPath}/admin/transactions">
                Transactions
            </a>

            <a href="${pageContext.request.contextPath}/admin/reports">
                Reports
            </a>

            <a href="${pageContext.request.contextPath}/logout">
                Logout
            </a>

        </c:if>


        <c:if test="${empty sessionScope.userId}">

            <a href="${pageContext.request.contextPath}/login">
                Login
            </a>

            <a href="${pageContext.request.contextPath}/register">
                Register
            </a>

        </c:if>

    </div>

</nav>

<div class="container">