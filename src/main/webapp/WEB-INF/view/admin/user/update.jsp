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
  </head>
  <body>
    <div class="container mt-5">
      <div class="row">
        <div class="col-md-6 col-12 mx-auto">
          <h3>Update User with id = ${userId}</h3>
          <hr />

          <form:form
            method="post"
            action="/admin/user/create"
            modelAttribute="UpdateUserId"
          >
            <div class="mb-3" style="display: none">
              <label for="exampleInputPassword1" class="form-label">Id</label>
              <form:input type="Id" class="form-control" path="id" />
            </div>
            <div class="mb-3">
              <label for="exampleInputEmail1" class="form-label">Email</label>
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
              <form:input type="text" class="form-control" path="fullname" />
            </div>
            <div class="mb-3">
              <label for="exampleInputAddress" class="form-label"
                >Address</label
              >
              <form:input type="text" class="form-control" path="adress" />
            </div>
            <button type="submit" class="btn btn-warning">Update</button>
          </form:form>
        </div>
      </div>
    </div>
  </body>
</html>
