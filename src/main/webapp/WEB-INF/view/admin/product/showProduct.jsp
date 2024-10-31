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
        const orgImage="${detailproductId.image}"
        if(orgImage){
          const urlImage="/images/product/"+orgImage;
          $("#avatarPreview").attr("src",urlImage);
          $("#avatarPreview").css("display","block");
        }
        <!-- ---------khúc trên sẽ tự động load ảnh ra giao diện-------- -->
        $("#avatarFile").change(function (e) {
          const imgURL = URL.createObjectURL(e.target.files[0]);
          $("#avatarPreview").attr("src", imgURL).css("display", "block");
        });
      });
    </script>
  </head>

  <!-- ---------------------------------------------- -->
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

        <!-- ------------------------ -->
        <div class="container">
          <div class="row">
            <div class="col-md-9 col-12 mx-auto">
              <h3>view prodcut with id: ${productId}</h3>
              <hr />
              <!-- ---------start table -->
              <table class="table">
                <thead>
                  <tr>
                    <th scope="col">stt</th>
                    <th scope="col">name</th>
                    <th scope="col">detaildesc</th>
                    <th scope="col">quantity</th>
                    <th scope="col">price</th>
                    <th scope="col">image</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th scope="row">${productId}</th>
                    <td>${detailproductId.name}</td>
                    <td>${detailproductId.detaildesc}</td>
                    <td>${detailproductId.quantity}</td>
                    <td>
                      <fmt:formatNumber
                        type="number"
                        value="${detailproductId.price}"
                      />vnđ
                    </td>
                    <td>
                      <div class="mb-3">
                        <img
                          src=""
                          id="avatarPreview"
                          width="220"
                          style="display: none"
                          alt="Avatar Preview"
                        />
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>

              <!-- ---------end table------- -->
            </div>
          </div>
        </div>
        <!-- ----------end -------- -->
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
