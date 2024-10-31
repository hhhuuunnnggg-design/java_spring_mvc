<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>

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
    <!--start load ảnh ra giai diện -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
      $(document).ready(() => {


        const orgImage="${UpdateProductId.image}"
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
    <!--end load ảnh ra giai diện -->
    <link href="/css/styles.css" rel="stylesheet" />
  </head>

  <!-- ------------------------------- -->
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
                <a href="/admin/user">user</a>
              </li>
              <li class="breadcrumb-item active">
                <a href="#">update user</a>
              </li>
            </ol>
          </div>
        </main>
        <div class="container">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <h3>Update User with id = ${userId}</h3>
              <hr />

              <form:form
                method="post"
                modelAttribute="UpdateUserId"
                enctype="multipart/form-data"
              >
                <div class="mb-3" style="display: none">
                  <label for="exampleInputPassword1" class="form-label"
                    >Id</label
                  >
                  <form:input type="Id" class="form-control" path="id" />
                </div>
                <div class="mb-3">
                  <label for="exampleInputEmail1" class="form-label"
                    >Email</label
                  >
                  <form:input
                    type="email"
                    class="form-control"
                    path="email"
                    readonly="true"
                  />
                </div>
                <div class="mb-3">
                  <label for="exampleInputPassword1" class="form-label"
                    >Password</label
                  >
                  <form:input
                    type="password"
                    class="form-control"
                    path="password"
                  />
                </div>
                <div class="mb-3">
                  <label for="exampleInputPhone" class="form-label"
                    >PhoneNumber</label
                  >
                  <form:input type="text" class="form-control" path="phone" />
                </div>
                <div class="mb-3">
                  <label for="exampleInputFullName" class="form-label"
                    >FullName</label
                  >
                  <form:input
                    type="text"
                    class="form-control"
                    path="fullname"
                  />
                </div>
                <div class="mb-3">
                  <label for="exampleInputAddress" class="form-label"
                    >Address</label
                  >
                  <form:input type="text" class="form-control" path="adress" />
                </div>
                <!-- --------satrt----------- -->
                <div class="row">
                  <div class="col">
                    <div class="mb-3">
                      <label for="roleSelect" class="form-label">Role:</label>
                      <form:select
                        path="role"
                        cssClass="form-select"
                        id="roleSelect"
                        aria-label="Default select example"
                      >
                        <form:option value="" label="Chọn Role" />
                        <form:option value="1" label="Admin" />
                        <form:option value="2" label="User" />
                      </form:select>
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
                </div>
                <div class="mb-3">
                  <img
                    src=""
                    id="avatarPreview"
                    width="420"
                    style="display: none"
                    alt="Avatar Preview"
                  />
                </div>
                <!-- end -->
                <button type="submit" class="btn btn-warning">Update</button>
              </form:form>
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
