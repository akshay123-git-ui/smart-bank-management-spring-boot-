<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="_header.jsp" %>

<div class="page-head">
    <h2>Beneficiaries</h2>
    <p class="muted">
        Save trusted recipients for faster transfers.
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

    <!-- Add Beneficiary -->

    <div class="card">

        <h3>Add Beneficiary</h3>

        <form method="post"
              action="${pageContext.request.contextPath}/beneficiaries/add">

            <label>Name</label>

            <input type="text"
                   name="name"
                   required>


            <label>Account Number</label>

            <input type="text"
                   name="accountNumber"
                   required>


            <label>IFSC</label>

            <input type="text"
                   name="ifsc"
                   placeholder="SBIN0000001">


            <label>UPI ID (optional)</label>

            <input type="text"
                   name="upiId"
                   placeholder="name@smartbank">


            <button type="submit">
                Add Beneficiary
            </button>

        </form>

    </div>


    <!-- Saved Beneficiaries -->

    <div class="card">

        <h3>Saved Payees</h3>

        <c:choose>

            <c:when test="${not empty beneficiaries}">

                <c:forEach var="b" items="${beneficiaries}">

                    <div class="list-row">

                        <div>

                            <b>${b.name}</b>

                            <small>
                                ${b.accountNumber}

                                <c:if test="${not empty b.ifsc}">
                                    • ${b.ifsc}
                                </c:if>

                            </small>

                        </div>


                        <form method="post"
                              action="${pageContext.request.contextPath}/beneficiaries/remove">

                            <input type="hidden"
                                   name="id"
                                   value="${b.id}">

                            <button class="danger-btn"
                                    type="submit">
                                Remove
                            </button>

                        </form>

                    </div>

                </c:forEach>

            </c:when>


            <c:otherwise>

                <p class="muted">
                    No beneficiaries saved.
                </p>

            </c:otherwise>

        </c:choose>

    </div>

</div>


<%@ include file="_footer.jsp" %>