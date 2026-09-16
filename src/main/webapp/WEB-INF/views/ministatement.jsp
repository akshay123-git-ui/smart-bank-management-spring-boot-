<%@ include file="_header.jsp" %>

<div class="card">
    <h2>Mini Statement — Last 10 Transactions</h2>

    <c:choose>
        <c:when test="${not empty transactions}">
            <table>
                <tr><th>Date</th><th>Type</th><th>Amount</th><th>Balance After</th><th>Counterparty</th><th>Description</th></tr>
                <c:forEach var="tx" items="${transactions}">
                    <tr>
                        <td>${tx.timestamp}</td>
                        <td><span class="badge badge-${tx.type == 'CREDIT' ? 'credit' : 'debit'}">${tx.type}</span></td>
                        <td>&#8377; <fmt:formatNumber value="${tx.amount}" pattern="#,##0.00"/></td>
                        <td>&#8377; <fmt:formatNumber value="${tx.balanceAfter}" pattern="#,##0.00"/></td>
                        <td>${tx.counterpartyAccountNumber}</td>
                        <td>${tx.description}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <p>No transactions found.</p>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="_footer.jsp" %>
