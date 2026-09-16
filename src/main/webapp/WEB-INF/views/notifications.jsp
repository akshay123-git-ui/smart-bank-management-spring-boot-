<%@ include file="_header.jsp" %>
<div class="page-head"><h2>Notifications</h2><form method="post" action="${pageContext.request.contextPath}/notifications/read-all"><button type="submit" class="outline-btn">Mark all read</button></form></div>
<div class="card"><c:choose><c:when test="${not empty notifications}">
<c:forEach var="n" items="${notifications}">
<div class="notification ${n.readFlag ? '' : 'unread'}"><div class="notif-dot"></div><div><b>${n.type}</b><p>${n.message}</p><small>${n.createdAt}</small></div></div>
</c:forEach></c:when><c:otherwise><p class="muted">You're all caught up.</p></c:otherwise></c:choose></div>
<%@ include file="_footer.jsp" %>
