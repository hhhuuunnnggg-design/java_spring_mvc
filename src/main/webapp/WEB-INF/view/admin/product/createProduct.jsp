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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
      $(document).ready(() => {
        $("#avatarFile").change(function (e) {
          const imgURL = URL.createObjectURL(e.target.files[0]);
          $("#avatarPreview").attr("src", imgURL).css("display", "block");
        });
      });
    </script>
    <link href="/css/styles.css" rel="stylesheet" />
  </head>
  <body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
      <jsp:include page="../layout/sidebar.jsp" />
      <div id="layoutSidenav_content">
        <main>
          <div class="container-fluid px-4">
            <h1 class="mt-4">Create Product</h1>
            <ol class="breadcrumb mb-4">
              <li class="breadcrumb-item active">
                <a href="/admin/product">Product</a>
              </li>
              <li class="breadcrumb-item active">Create Product</li>
            </ol>
          </div>
        </main>
        <div class="container">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <h3>Create a Product</h3>
              <hr />
              <form:form
                method="post"
                action="/admin/product/create"
                modelAttribute="newOneProduct"
                enctype="multipart/form-data"
              >
                <!-- ----------------- -->
                <div class="row">
                  <div class="col">
                    <div class="mb-3">
                      <label for="name" class="form-label">Name</label>
                      <form:input path="name" class="form-control" />
                    </div>
                  </div>
                  <div class="col">
                    <div class="mb-3">
                      <label for="price" class="form-label">Price</label>
                      <form:input
                        path="price"
                        type="number"
                        class="form-control"
                      />
                    </div>
                  </div>
                </div>

                <!-- ---------------------- -->

                <div class="mb-3">
                  <label for="detaildesc" class="form-label"
                    >Detail Description:</label
                  >
                  <form:input path="detaildesc" class="form-control" />
                </div>
                <!-- ------------------- -->
                <div class="row">
                  <div class="col">
                    <div class="mb-3">
                      <label for="shortdesc" class="form-label"
                        >Short Description</label
                      >
                      <form:input path="shortdesc" class="form-control" />
                    </div>
                  </div>
                  <div class="col">
                    <div class="mb-3">
                      <label for="quantity" class="form-label">Quantity</label>
                      <form:input
                        path="quantity"
                        type="number"
                        class="form-control"
                      />
                    </div>
                  </div>
                </div>

                <!-- ----------------------- -->
                <div class="row">
                  <div class="col">
                    <div class="mb-3">
                      <label for="factory" class="form-label">Factory:</label>
                      <form:select path="factory" cssClass="form-select">
                        <form:option value="" label="Select Factory" />
                        <form:option value="Tân Châu" label="Tân Châu" />
                        <form:option value="Tân Hiệp" label="Tân Hiệp" />
                      </form:select>
                    </div>
                  </div>
                  <div class="col">
                    <div class="mb-3">
                      <label for="target" class="form-label">Target:</label>
                      <form:select path="target" cssClass="form-select">
                        <form:option value="" label="Select Target" />
                        <form:option value="100" label="100" />
                        <form:option value="200" label="200" />
                      </form:select>
                    </div>
                  </div>
                </div>

                <!-- ----------------------------- -->

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
                    Create Product
                  </button>
                </div>
              </form:form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <jsp:include page="../layout/footer.jsp" />
  </body>
</html>
