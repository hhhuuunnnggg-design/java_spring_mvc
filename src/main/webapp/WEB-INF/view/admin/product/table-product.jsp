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
            <div class="col-md-10 col-12 mx-auto">
              <div class="d-flex justify-content-between align-items-center">
                <h3>Table product</h3>
                <a href="/admin/product/create"
                  ><button class="btn btn-primary">Create product</button></a
                >
              </div>
              <hr />

              <table
                class="table table-striped table-hover table-bordered"
                action="/admin/user"
              >
                <thead>
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Factory</th>
                    <th scope="col">Action</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach
                    items="${tableListUser}"
                    var="user"
                    varStatus="iteration"
                  >
                    <tr>
                      <td>${user.id}</td>
                      <td>${user.email}</td>
                      <td>${user.fullname}</td>
                      <td>${user.role.name}</td>
                      <td>
                        <!-- đây là API -->
                        <a href="/admin/user/${user.id}"
                          ><button type="button" class="btn btn-success">
                            View
                          </button></a
                        >
                        <a href="/admin/update/${user.id}"
                          ><button type="button" class="btn btn-warning">
                            Update
                          </button></a
                        >

                        <button
                          type="button"
                          class="btn btn-danger"
                          onclick="deleteUser()"
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
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