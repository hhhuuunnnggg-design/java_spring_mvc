<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
    <meta name="author" content="Hỏi Dân IT" />
    <title>Dashboard - Hỏi Dân IT</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script
      src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
      crossorigin="anonymous"
    ></script>
  </head>

  <body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
      <jsp:include page="../layout/sidebar.jsp" />
      <div id="layoutSidenav_content">
        <main>
          <div class="container-fluid px-4">
            <h1 class="mt-4">Dashboard</h1>
            <ol class="breadcrumb mb-4">
              <li class="breadcrumb-item active">product</li>
            </ol>
          </div>
        </main>
        <!-- ---------------------------------------------- -->
        <div class="container">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <h3>Table product</h3>
              <hr />

              <form
                method="post"
                action="/admin/user/create"
                modelAttribute="detailUserId"
              >
                <div class="mb-3">
                  <label for="exampleInputEmail1" class="form-label"
                    >Email</label
                  >
                  <input
                    type="email"
                    class="form-control"
                    path="email"
                    aria-describedby="emailHelp"
                  />
                </div>
                <div class="mb-3">
                  <label for="exampleInputPassword1" class="form-label"
                    >Password</label
                  >
                  <input type="password" class="form-control" path="password" />
                </div>
                <div class="mb-3">
                  <label for="exampleInputPhone" class="form-label"
                    >PhoneNumber</label
                  >
                  <input type="text" class="form-control" path="phone" />
                </div>
                <div class="mb-3">
                  <label for="exampleInputFullName" class="form-label"
                    >FullName</label
                  >
                  <input type="text" class="form-control" path="fullname" />
                </div>
                <div class="mb-3">
                  <label for="exampleInputAddress" class="form-label"
                    >Address</label
                  >
                  <input type="text" class="form-control" path="adress" />
                </div>
                <a href=""><button class="btn btn-primary">back</button></a>
              </form>
            </div>
          </div>
        </div>
        <jsp:include page="../layout/footer.jsp" />
      </div>
    </div>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      crossorigin="anonymous"
    ></script>
    <script src="/js/scripts.js"></script>
  </body>
</html>
