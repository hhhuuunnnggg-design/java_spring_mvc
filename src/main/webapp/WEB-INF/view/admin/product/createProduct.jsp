<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Create Product</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <!--start load ảnh ra giai diện -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
      $(document).ready(() => {
        $("#avatarFile").change(function (e) {
          const imgURL = URL.createObjectURL(e.target.files[0]);
          $("#avatarPreview").attr("src", imgURL).css("display", "block");
        });
      });
    </script>
    <!--end load ảnh ra giai diện -->
    <link href="/css/styles.css" rel="stylesheet" />
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
              <li class="breadcrumb-item active">
                <a href="/admin/product">product</a>
              </li>
              <li class="breadcrumb-item active">
                <a href="#">create product</a>
              </li>
            </ol>
          </div>
        </main>
        <div class="container">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <h3>Create a user</h3>
              <hr />
              <form
                method="post"
                action="/admin/user/create"
                modelAttribute="newUser"
                enctype="multipart/form-data"
              >
                <div class="row">
                  <div class="col">
                    <div class="mb-3">
                      <label for="email" class="form-label">Name</label>
                      <input type="email" class="form-control" path="email" />
                    </div>
                  </div>
                  <div class="col">
                    <div class="mb-3">
                      <label for="price" class="form-label">Price</label>
                      <input
                        type="number"
                        class="form-control"
                        path="password"
                      />
                    </div>
                  </div>
                </div>
                <div class="mb-3">
                  <label for="address" class="form-label"
                    >Detail description:</label
                  >
                  <input type="text" class="form-control" path="adress" />
                </div>
                <div class="row">
                  <div class="col">
                    <div class="mb-3">
                      <label for="phone" class="form-label"
                        >Short description</label
                      >
                      <input type="number" class="form-control" path="phone" />
                    </div>
                  </div>
                  <div class="col">
                    <div class="mb-3">
                      <label for="fullname" class="form-label">Quantity</label>
                      <input type="text" class="form-control" path="fullname" />
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col">
                    <div class="mb-3">
                      <label for="roleSelect" class="form-label"
                        >Factory:</label
                      >
                      <br />
                      <select
                        path="role"
                        cssClass="form-select"
                        id="roleSelect"
                        aria-label="Default select example"
                      >
                        <option value="" label="Chọn Role"></option>
                        <option value="1" label="Admin"></option>
                        <option value="2" label="User"></option>
                      </select>
                    </div>
                  </div>
                  <div class="col">
                    <div class="mb-3">
                      <label for="roleSelect" class="form-label">Target:</label>
                      <br />
                      <select
                        path="role"
                        cssClass="form-select"
                        id="roleSelect"
                        aria-label="Default select example"
                      >
                        <option value="" label="Chọn Role"></option>
                        <option value="1" label="Admin"></option>
                        <option value="2" label="User"></option>
                      </select>
                    </div>
                  </div>
                </div>
                <div class="col">
                  <div class="mb-3">
                    <label for="avatarFile" class="form-label">Avatar:</label>
                    <input
                      class="form-control"
                      type="file"
                      id="avatarFile"
                      accept=".png, .jpg, .jpeg"
                      name="hoidanitFile"
                    />
                  </div>
                </div>
                <div class="mb-3">
                  <img
                    src=""
                    id="avatarPreview"
                    width="120"
                    style="display: none"
                    alt="Avatar Preview"
                  />
                </div>
                <div class="d-grid">
                  <button type="submit" class="btn btn-primary">
                    Create product
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <jsp:include page="../layout/footer.jsp" />
  </body>
</html>