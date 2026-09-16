<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="_header.jsp" %>

<div class="card"
     style="max-width:420px; margin:50px auto; text-align:center;">

    <div style="font-size:44px; margin-bottom:8px;">
        🔐
    </div>

    <h2>Unlock SmartBank</h2>

    <p class="muted">
        Enter your 4-digit App PIN to continue.
    </p>

    <form method="post"
          action="${pageContext.request.contextPath}/app-pin">

        <label>App PIN</label>

        <input type="password"
               name="pin"
               maxlength="4"
               minlength="4"
               inputmode="numeric"
               pattern="[0-9]{4}"
               required
               autofocus>

        <button type="submit">
            Unlock
        </button>

    </form>

    <p style="margin-top:16px; font-size:12px; color:#6b7280;">
        Your App PIN is different from your UPI payment PIN.
    </p>

    <a href="${pageContext.request.contextPath}/logout">
        Use another account
    </a>

</div>

<%@ include file="_footer.jsp" %>