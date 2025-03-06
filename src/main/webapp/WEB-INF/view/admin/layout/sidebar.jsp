<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
  </head>
  <body>
    <div id="layoutSidenav_nav">
      <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">
          <c:forEach items="${menuSession}" var="menu">
            <li id="${menu.idMenu}"><a> ${menu.name} <span
                class="fa fa-chevron-down"></span></a>
              <ul class="nav child_menu">
                <c:forEach items="${menu.child}" var="child">
                  <li id="${child.idMenu}"><a href="<c:url value="${child.url}"/>">${child.name}</a></li>
                </c:forEach>
              </ul></li>
          </c:forEach>
        </div>
        <div class="sb-sidenav-footer">
          <div class="small">Logged in as:</div>
          Nguyễn Đình Hùng IT
        </div>
      </nav>
    </div>
  </body>
</html>
