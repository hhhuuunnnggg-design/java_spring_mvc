<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

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
  </head>
  <body>
    <div class="container mt-5">
      <div class="row">
        <div class="col-md-10 col-12 mx-auto">
          <div class="d-flex justify-content-between align-items-center">
            <h3>Table User</h3>
            <a href="/admin/user/create"
              ><button class="btn btn-primary">Create User</button></a
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
                <th scope="col">Email</th>
                <th scope="col">FullName</th>
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
                      onclick="deleteUser(${user.id})"
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
    <!-- js -->
    <script>
      function deleteUser(userId) {
        if (
          confirm(
            "Bạn có chắc muốn xóa người dùng với ID: " + userId + " không?"
          )
        ) {
          $.ajax({
            url: "/admin/detele/" + userId,
            type: "DELETE",
            success: function (result) {
              location.reload(); // Tải lại trang để cập nhật danh sách
            },
            error: function (err) {
              alert("Có lỗi xảy ra khi xóa người dùng.");
            },
          });
        }
      }
    </script>
  </body>
</html>
