<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="_header.jsp" %>

<div class="page-head">
    <h2>My Card</h2>
    <p class="muted">
        Demo debit card representation. Never use real card details in this project.
    </p>
</div>

<div class="bank-card">

    <div class="card-brand">
        SmartBank
    </div>

    <div class="chip">
        ◆
    </div>

    <div class="card-number">
        ${card.maskedNumber}
    </div>

    <div class="card-bottom">

        <span>
            DEBIT CARD<br>
            <b>**** ${card.lastFour}</b>
        </span>

        <span>
            VALID THRU<br>
            <b>${card.expiryDate}</b>
        </span>

        <span>
            STATUS<br>
            <b>${card.status}</b>
        </span>

    </div>

</div>

<div class="card">

    <h3>Card safety</h3>

    <p class="muted">
        This portfolio feature uses masked/demo card data.
        It does not connect to a payment network.
    </p>

</div>

<%@ include file="_footer.jsp" %>