<%@ include file="_header.jsp" %>
<c:if test="${not empty sessionScope.success}">
    <div class="alert alert-success">
        ${sessionScope.success}
    </div>
    <c:remove var="success" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.error}">
    <div class="alert alert-error">
        ${sessionScope.error}
    </div>
    <c:remove var="error" scope="session"/>
</c:if>

<div class="hero">

    <div>

        <p class="eyebrow">DIGITAL BANKING</p>

        <h1>Good day, ${sessionScope.userName} &#128075;</h1>

        <p class="muted">
            Manage your money securely from one place.
        </p>

    </div>

    <a class="btn light-btn"
       href="${pageContext.request.contextPath}/banking">
        Deposit / Withdraw
    </a>

</div>


<div class="balance-card">

    <div>

        <span class="small-label">AVAILABLE BALANCE</span>

        <div class="big-balance">
            &#8377;
            <fmt:formatNumber
                value="${account.balance}"
                pattern="#,##0.00"/>
        </div>

        <div class="account-meta">
            A/C ${account.accountNumber}
            &nbsp; &bull; &nbsp;
            UPI ${account.upiId}
        </div>

    </div>

    <div class="balance-actions">

        <a class="btn white-btn"
           href="${pageContext.request.contextPath}/transfer">
            Send Money
        </a>

        <a class="btn outline-btn"
           href="${pageContext.request.contextPath}/transactions">
            View Statement
        </a>

    </div>

</div>


<div class="card qr-card">

    <div>

        <h3>My UPI QR</h3>

        <p class="muted">
            Scan this demo QR to identify this SmartBank UPI ID.
        </p>

        <b>${account.upiId}</b>

        <div style="margin-top:12px">

            <a class="btn light-btn"
               href="${pageContext.request.contextPath}/upi/scan">
                Scan &amp; Pay
            </a>

        </div>

    </div>

    <img class="qr"
         src="${pageContext.request.contextPath}/upi/qr?upiId=${account.upiId}"
         alt="UPI QR">

</div>


<div class="quick-grid">

    <a class="quick-card"
       href="${pageContext.request.contextPath}/transfer">

        <span>&#128184;</span>
        <b>Send Money</b>
        <small>Account / UPI</small>

    </a>


    <a class="quick-card"
       href="${pageContext.request.contextPath}/upi/scan">

        <span>&#128247;</span>
        <b>Scan &amp; Pay</b>
        <small>Scan SmartBank QR</small>

    </a>


    <a class="quick-card"
       href="${pageContext.request.contextPath}/beneficiaries">

        <span>&#128101;</span>
        <b>Beneficiaries</b>
        <small>Manage payees</small>

    </a>


    <a class="quick-card"
       href="${pageContext.request.contextPath}/card">

        <span>&#128179;</span>
        <b>My Card</b>
        <small>View debit card</small>

    </a>


    <a class="quick-card"
       href="${pageContext.request.contextPath}/notifications">

        <span>&#128276;</span>
        <b>Notifications</b>
        <small>${unreadNotifications} unread</small>

    </a>


    <a class="quick-card"
       href="${pageContext.request.contextPath}/loans/apply">

        <span>&#127974;</span>
        <b>Loans</b>
        <small>Apply &amp; track</small>

    </a>


    <a class="quick-card"
       href="${pageContext.request.contextPath}/emi">

        <span>&#129518;</span>
        <b>EMI Calculator</b>
        <small>Plan repayments</small>

    </a>

</div>


<div class="two-col">

    <div class="card">

        <div class="section-title">

            <h3>Recent Transactions</h3>

            <a href="${pageContext.request.contextPath}/transactions">
                View all
            </a>

        </div>


        <c:choose>

            <c:when test="${not empty recentTransactions}">

                <c:forEach var="tx" items="${recentTransactions}">

                    <div class="transaction-row">

                        <div class="tx-icon">
                            ${tx.type == 'CREDIT' ? '&#8595;' : '&#8593;'}
                        </div>

                        <div class="tx-main">

                            <b>
                                ${empty tx.description ? 'Bank transaction' : tx.description}
                            </b>

                            <small>${tx.timestamp}</small>

                        </div>

                        <div class="${tx.type == 'CREDIT' ? 'credit' : 'debit'}">

                            ${tx.type == 'CREDIT' ? '+' : '-'}
                            &#8377;
                            <fmt:formatNumber
                                value="${tx.amount}"
                                pattern="#,##0.00"/>

                        </div>

                    </div>

                </c:forEach>

            </c:when>

            <c:otherwise>

                <p class="muted">
                    No transactions yet.
                </p>

            </c:otherwise>

        </c:choose>

    </div>


    <div class="card">

        <div class="section-title">

            <h3>Savings Goals</h3>

            <a href="${pageContext.request.contextPath}/savings">
                Manage
            </a>

        </div>


        <c:choose>

            <c:when test="${not empty goals}">

                <c:forEach var="goal" items="${goals}">

                    <div class="goal">

                        <div class="goal-line">

                            <b>${goal.name}</b>

                            <span>

                                &#8377;
                                <fmt:formatNumber
                                    value="${goal.savedAmount}"
                                    pattern="#,##0"/>

                                /

                                &#8377;
                                <fmt:formatNumber
                                    value="${goal.targetAmount}"
                                    pattern="#,##0"/>

                            </span>

                        </div>

                        <div class="progress">

                            <div style="width:${goal.progressPercent}%">
                            </div>

                        </div>

                    </div>

                </c:forEach>

            </c:when>

            <c:otherwise>

                <p class="muted">
                    Create your first savings goal.
                </p>

            </c:otherwise>

        </c:choose>

    </div>

</div>


<div class="card">

    <div class="section-title">
        <h3>Loan Applications</h3>

        <a href="${pageContext.request.contextPath}/loans/apply">
            Apply
        </a>
    </div>

    <c:choose>

        <c:when test="${not empty loans}">

            <table>

                <tr>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Tenure</th>
                    <th>EMI</th>
                    <th>Outstanding</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="loan" items="${loans}">

                    <tr>

                        <td>
                            ${loan.loanType}
                        </td>

                        <td>
                            &#8377;
                            <fmt:formatNumber
                                value="${loan.amount}"
                                pattern="#,##0.00"/>
                        </td>

                        <td>
                            ${loan.tenureMonths} months
                        </td>

                        <td>
                            &#8377;
                            <fmt:formatNumber
                                value="${loan.emiAmount}"
                                pattern="#,##0.00"/>
                        </td>

                        <td>
                            &#8377;
                            <fmt:formatNumber
                                value="${loan.outstandingAmount}"
                                pattern="#,##0.00"/>
                        </td>

                        <td>
                            <span class="badge badge-${loan.status.toLowerCase()}">
                                ${loan.status}
                            </span>
                        </td>

                        <td>

                            <c:if test="${loan.status == 'APPROVED' && loan.outstandingAmount > 0}">

                                <form method="post"
                                      action="${pageContext.request.contextPath}/loans/pay-emi">

                                    <input type="hidden"
                                           name="loanId"
                                           value="${loan.id}">

                                    <button type="submit">
                                        Pay EMI
                                    </button>

                                </form>

                            </c:if>

                            <c:if test="${loan.status == 'CLOSED'}">
    <span class="muted">
        Fully Paid
    </span>
</c:if>

                        </td>

                    </tr>

                </c:forEach>

            </table>

        </c:when>

        <c:otherwise>

            <p class="muted">
                No loan applications yet.
            </p>

        </c:otherwise>

    </c:choose>

</div>


<%@ include file="_footer.jsp" %>