<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="_header.jsp" %>

<div class="page-head">
    <h2>Transaction History</h2>
    <p class="muted">Your latest 100 transactions.</p>
</div>

<div class="card">

    <table>

        <tr>
            <th>Date</th>
            <th>Type</th>
            <th>Amount</th>
            <th>Balance</th>
            <th>Counterparty</th>
            <th>Description</th>
        </tr>

        <c:forEach var="tx" items="${transactions}">

            <tr>

                <td>
                    ${tx.timestamp}
                </td>

                <td>
                    <span class="badge badge-${tx.type == 'CREDIT' ? 'credit' : 'debit'}">
                        ${tx.type}
                    </span>
                </td>

                <td class="${tx.type == 'CREDIT' ? 'credit' : 'debit'}">

                    ${tx.type == 'CREDIT' ? '+' : '-'}₹<fmt:formatNumber
                        value="${tx.amount}"
                        pattern="#,##0.00"/>

                </td>

                <td>

                    ₹<fmt:formatNumber
                        value="${tx.balanceAfter}"
                        pattern="#,##0.00"/>

                </td>

                <td>
                    ${tx.counterpartyAccountNumber}
                </td>

                <td>
                    ${tx.description}
                </td>

            </tr>

        </c:forEach>

    </table>

</div>

<%@ include file="_footer.jsp" %>