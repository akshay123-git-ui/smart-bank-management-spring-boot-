<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="_header.jsp" %>

<div class="page-head">
    <h2>EMI Calculator</h2>
    <p class="muted">
        Estimate your monthly loan repayment.
    </p>
</div>

<div class="card calculator">

    <label>Loan amount (₹)</label>

    <input
        id="principal"
        type="number"
        value="500000"
        min="1"
        step="0.01"
    >

    <label>Annual interest rate (%)</label>

    <input
        id="rate"
        type="number"
        value="8.5"
        min="0"
        step="0.1"
    >

    <label>Tenure (months)</label>

    <input
        id="months"
        type="number"
        value="60"
        min="1"
    >

    <button type="button" onclick="calculate()">
        Calculate EMI
    </button>

    <div class="calc-result">

        <div>
            <small>MONTHLY EMI</small>
            <strong id="emi">₹0</strong>
        </div>

        <div>
            <small>TOTAL INTEREST</small>
            <strong id="interest">₹0</strong>
        </div>

        <div>
            <small>TOTAL PAYMENT</small>
            <strong id="total">₹0</strong>
        </div>

    </div>

</div>

<script>

function calculate() {

    const p = Number(
        document.getElementById("principal").value
    );

    const annual = Number(
        document.getElementById("rate").value
    );

    const n = Number(
        document.getElementById("months").value
    );

    const r = annual / 12 / 100;

    let emi;

    if (r === 0) {
        emi = p / n;
    } else {
        emi = p * r * Math.pow(1 + r, n)
                / (Math.pow(1 + r, n) - 1);
    }

    const total = emi * n;

    const money = function(x) {
        return "₹" + x.toLocaleString("en-IN", {
            maximumFractionDigits: 2
        });
    };

    document.getElementById("emi").textContent = money(emi);

    document.getElementById("interest").textContent =
        money(total - p);

    document.getElementById("total").textContent =
        money(total);
}

calculate();

</script>

<%@ include file="_footer.jsp" %>