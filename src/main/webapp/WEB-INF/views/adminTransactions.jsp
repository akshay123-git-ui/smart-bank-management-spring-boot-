<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="_header.jsp" %>

<div class="page-head">
    <h2>Transaction Monitoring</h2>
    <p class="muted">
        Latest 100 transactions across the demo bank.
    </p>
</div>

<div class="card">

    <table>

        <tr>
            <th>Date</th>
            <th>Account</th>
            <th>Type</th>
            <th>Amount</th>
            <th>Counterparty</th>
            <th>Description</th>
        </tr>

        <c:forEach var="tx" items="${transactions}">

            <tr>

                <td>
                    ${tx.timestamp}
                </td>

                <td>
                    ${tx.account.accountNumber}
                </td>

                <td>
                    ${tx.type}
                </td>

                <td>
                    ₹<fmt:formatNumber
                        value="${tx.amount}"
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