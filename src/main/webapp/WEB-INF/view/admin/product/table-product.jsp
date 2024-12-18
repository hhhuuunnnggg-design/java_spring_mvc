<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="_csrf" content="${_csrf.token}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
    <meta name="author" content="Hỏi Dân IT" />
    <title>Dashboard - Hỏi Dân IT</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
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
                action="/admin/product"
              >
                <thead>
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Factory</th>
                    <th scope="col">Image</th>
                    <th scope="col">Action</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach
                    items="${tableListProduct}"
                    var="product"
                    varStatus="iteration"
                  >
                    <tr>
                      <td>${product.id}</td>
                      <td>${product.name}</td>
                      <!-- đây là format vnd -->
                      <td>
                        <fmt:formatNumber
                          type="number"
                          value="${product.price}"
                        />vnđ
                      </td>
                      <td>${product.factory}</td>
                      <td>
                        <div class="mb-3">
                          <img
                            src="/images/product/${product.image}"
                            width="100"
                            alt="Avatar Preview"
                            style="display: ${product.image != null ? 'block' : 'none'}"
                          />
                        </div>
                      </td>

                      <td>
                        <!-- đây là API -->
                        <a href="/admin/product/${product.id}"
                          ><button type="button" class="btn btn-success">
                            View
                          </button></a
                        >
                        <a href="/admin/product/update/${product.id}"
                          ><button type="button" class="btn btn-warning">
                            Update
                          </button></a
                        >

                        <button
                          type="button"
                          class="btn btn-danger"
                          onclick="deleteUser(${product.id})"
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
               <%-- start paginaiton --%>
              
               <%-- end pagination --%>

               
            </div>
          </div>
        </div>

        <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center" >
                  <li class="page-item"><a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                            href="/admin/product?page=${currentPage - 1}"
                                                            aria-label="Previous">
                                                            <span aria-hidden="true">trước</span>
                                                        </a></li>

                  <c:forEach var="counter" begin="0" end="${totalPages-1}" varStatus="loop">
                    <li class="page-item">
                      <a class="${(loop.index + 1) eq currentPage ? 'active page-link' : 'page-link'}"
                       href="product?page=${loop.index+1}">${loop.index+1}</a>
                    </li>
                  </c:forEach>

                   <li class="page-item"><a class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                            href="/admin/product?page=${currentPage + 1}"
                                                            aria-label="Previous">
                                                            <span aria-hidden="true">sau</span>
                                                        </a></li>
                </ul>
              </nav>
       
        <jsp:include page="../layout/footer.jsp" />
      </div>
    </div>
    <script>
      // Lấy CSRF token và header từ thẻ meta
      const csrfToken = $("meta[name='_csrf']").attr("content");
      const csrfHeader = $("meta[name='_csrf_header']").attr("content");

      console.log("CSRF Token:", csrfToken);
      console.log("CSRF Header:", csrfHeader);

      function deleteUser(productId) {
        if (
          confirm(
            "Bạn có chắc muốn xóa sản phẩm với ID: " + productId + " không?"
          )
        ) {
          $.ajax({
            url: "/admin/product/delete/" + productId,
            type: "DELETE",
            beforeSend: function (xhr) {
              // Thêm CSRF token vào header
              xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (result) {
              location.reload(); // Tải lại trang để cập nhật danh sách
            },
            error: function (err) {
              alert("Có lỗi xảy ra khi xóa sản phẩm.");
            },
          });
        }
      }
    </script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      crossorigin="anonymous"
    ></script>
    <script src="/js/scripts.js"></script>
  </body>
</html>
