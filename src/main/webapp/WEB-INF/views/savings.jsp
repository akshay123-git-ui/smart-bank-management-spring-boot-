<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="_header.jsp" %>

<div class="page-head">
    <h2>Savings Goals</h2>
    <p class="muted">
        Plan and track goals such as education, travel or an emergency fund.
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

<div class="card">

    <h3>Create a Goal</h3>

    <form method="post"
          action="${pageContext.request.contextPath}/savings/create"
          class="inline-form">

        <div>
            <label>Goal name</label>
            <input type="text"
                   name="name"
                   required>
        </div>

        <div>
            <label>Target amount (₹)</label>
            <input type="number"
                   name="targetAmount"
                   min="1"
                   step="0.01"
                   required>
        </div>

        <div>
            <label>Target date</label>
            <input type="date"
                   name="targetDate">
        </div>

        <div>
            <button type="submit">Create</button>
        </div>

    </form>

</div>


<div class="goal-grid">

    <c:forEach var="goal" items="${goals}">

        <div class="card">

            <div class="goal-line">

                <h3>${goal.name}</h3>

                <b>${goal.progressPercent}%</b>

            </div>


            <div class="progress">

                <div style="width:${goal.progressPercent}%">
                </div>

            </div>


            <p>
                Saved ₹
                <fmt:formatNumber
                    value="${goal.savedAmount}"
                    pattern="#,##0.00"/>
                of ₹
                <fmt:formatNumber
                    value="${goal.targetAmount}"
                    pattern="#,##0.00"/>
            </p>


            <c:if test="${not empty goal.targetDate}">

                <small>
                    Target: ${goal.targetDate}
                </small>

            </c:if>


            <form method="post"
                  action="${pageContext.request.contextPath}/savings/add">

                <input type="hidden"
                       name="goalId"
                       value="${goal.id}">

                <label>Add savings (₹)</label>

                <input type="number"
                       name="amount"
                       min="1"
                       step="0.01"
                       required>

                <button type="submit">
                    Add
                </button>

            </form>

        </div>

    </c:forEach>

</div>


<%@ include file="_footer.jsp" %>