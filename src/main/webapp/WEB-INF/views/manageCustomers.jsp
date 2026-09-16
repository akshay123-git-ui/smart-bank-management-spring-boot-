<%@ include file="_header.jsp" %>

<div class="card">
    <h2>All Customers</h2>
    <c:choose>
        <c:when test="${not empty customers}">
            <table>
                <tr><th>Name</th><th>Email</th><th>Phone</th><th>KYC Verified</th><th>Joined</th></tr>
                <c:forEach var="cust" items="${customers}">
                    <tr>
                        <td>${cust.fullName}</td>
                        <td>${cust.email}</td>
                        <td>${cust.phone}</td>
                        <td>${cust.kycVerified ? "Yes" : "No"}</td>
                        <td>${cust.createdAt}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <p>No customers yet.</p>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="_footer.jsp" %>
