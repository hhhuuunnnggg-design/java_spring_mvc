<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Detail User</title>

    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link href="/css/styles.css" rel="stylesheet" />
    
    <script>
      $(document).ready(() => {
        const orgImage = "${viewOrderDetail[0].product.image}";
        if (orgImage) {
          const urlImage = "/images/product/" + orgImage;

          $("#avatarPreview").attr("src", urlImage);
          $("#avatarPreview").css("display", "block");
        }

        /* ---------khúc trên sẽ tự động load ảnh ra giao diện-------- */
        $("#avatarFile").change(function (e) {
          const imgURL = URL.createObjectURL(e.target.files[0]);
          $("#avatarPreview").attr("src", imgURL).css("display", "block");
        });
      });
    </script>
  </head>

  <body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
      <jsp:include page="../layout/sidebar.jsp" />
      <div id="layoutSidenav_content">
        <main>
          <div class="container-fluid px-4">
            <h1 class="mt-4">Dashboard</h1>
            <ol class="breadcrumb mb-4" style="text-decoration: none">
              <li class="breadcrumb-item active">
                <a href="/admin/product">product</a>
              </li>
              <li class="breadcrumb-item active">
                <a href="#">Detail product</a>
              </li>
            </ol>
          </div>
        </main>

        <div class="container">
          <div class="row">
            <div class="col-md-9 col-12 mx-auto">
              <h3>view product with id: ${urlImage}</h3>
              <hr />
              <table class="table">
                <thead>
               
                  <tr>
                    <th scope="col">STT</th>
                    <th scope="col">Tên sản phẩm</th>
                    <th scope="col">Hình ảnh</th>
                    <th scope="col">tiền</th>
                    <th scope="col">Số lượng</th>
                    <th scope="col">Tổng tiền</th>
                  </tr>
                </thead>
                <tbody>
                
                  <c:forEach
                    var="detail"
                    items="${viewOrderDetail}"
                    varStatus="status"
                  >
                    <tr>
                      <td>${status.index + 1}</td>
                      <td>${detail.product.name}</td>
                      <td>
                        <div class="mb-3">
                          <img
                            src="/images/product/${detail.product.image}"
                            width="220"
                            alt="Avatar Preview"
                          />
                        </div>
                      </td>
                      <td>
                      
                        <fmt:formatNumber
                          type="number"
                          value="${detail.product.price}"
                        />
                        vnđ
                      </td>

                      <td>${detail.quantity}</td>
                      <td>
                        <fmt:formatNumber
                          type="number"
                          value="${detail.price * detail.quantity}"
                        />
                        vnđ
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
