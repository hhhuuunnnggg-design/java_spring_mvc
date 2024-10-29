<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>

    <!-- Latest compiled and minified CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />

    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <!-- <link href="/css/demo.css" rel="stylesheet"> -->
    <!-- <link rel="stylesheet" href="/css/demo.css" /> -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
      $(document).ready(() => {
        const avatarFile = $("#avatarFile");
        avatarFile.change(function (e) {
          const imgURL = URL.createObjectURL(e.target.files[0]);
          $("#avatarPreview").attr("src", imgURL);
          $("#avatarPreview").css({ display: "block" });
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
            <h1 class="mt-4">Dashboard</h1>
            <ol class="breadcrumb mb-4" style="text-decoration: none">
              <li class="breadcrumb-item active">
                <a href="/admin/user">user</a>
              </li>
              <li class="breadcrumb-item active">
                <a href="#">create user</a>
              </li>
            </ol>
          </div>
        </main>

        <div class="container">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <h3>create a user</h3>
              <hr />

              <form:form
                method="post"
                action="/admin/user/create"
                modelAttribute="newUser"
              >
                <div class="row">
                  <div class="col">
                    <div class="mb-3">
                      <label for="exampleInputEmail1" class="form-label"
                        >Email
                      </label>
                      <form:input
                        type="email"
                        class="form-control"
                        path="email"
                        aria-describedby="emailHelp"
                      />
                    </div>
                  </div>
                  <div class="col">
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
                  </div>
                </div>
                <!-- ---------------------------------------------- -->
                <div class="row">
                  <div class="col">
                    <div class="mb-3">
                      <label for="exampleInputPassword1" class="form-label"
                        >PhoneNumber</label
                      >
                      <form:input
                        type="number"
                        class="form-control"
                        path="phone"
                      />
                    </div>
                  </div>
                  <div class="col">
                    <div class="mb-3">
                      <label for="exampleInputPassword1" class="form-label"
                        >FullName</label
                      >
                      <form:input
                        type="text"
                        class="form-control"
                        path="fullname"
                      />
                    </div>
                  </div>
                </div>

                <div class="mb-3">
                  <label for="exampleInputPassword1" class="form-label"
                    >Address</label
                  >
                  <form:input type="text" class="form-control" path="adress" />
                </div>

                <!-- ---------------------------------------------- -->
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
                <!-- ------------------------------ -->

                <div class="col-12 mb-3">
                  <img
                    style="max-height: 250px; display: none"
                    alt="avatar preview"
                    id="avatarPreview"
                  />
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
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
