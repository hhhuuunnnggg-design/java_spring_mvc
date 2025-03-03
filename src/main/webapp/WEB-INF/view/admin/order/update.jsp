<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop">
    <meta name="author" content="Hỏi Dân IT">
    <title>Update Order - Hỏi Dân IT</title>
    <link href="/css/styles.css" rel="stylesheet">
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Update Order</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"><a href="/admin/orders">Orders</a></li>
                    <li class="breadcrumb-item active">Update Order</li>
                </ol>
            </div>
        </main>
        <div class="container">
            <div class="card shadow p-4">
                <h3 class="mb-4">Order Details</h3>
                <form:form method="post" action="/admin/order/update" modelAttribute="newOrder">
                    <div class="row mb-3" style="display: none;">
                        <label class="form-label">Id:</label>
                        <form:input type="text" class="form-control" path="id"/>
                    </div>

                    <div class="mb-3 d-flex justify-content-between align-items-center">
                        <label class="fw-bold">Order ID: ${newOrder.id}</label>
                        <label class="fw-bold">Total Price: <fmt:formatNumber type="number" value="${newOrder.totalPrice}"/> đ</label>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">User:</label>
                        <form:input type="text" class="form-control" disabled="true" path="user.fullName"/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Status:</label>
                        <form:select class="form-select" path="status">
                            <form:option value="PENDING">PENDING</form:option>
                            <form:option value="SHIPPING">SHIPPING</form:option>
                            <form:option value="COMPLETE">COMPLETE</form:option>
                            <form:option value="CANCEL">CANCEL</form:option>
                        </form:select>
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn btn-warning"><i class="fa fa-save"></i> Update</button>
                    </div>
                </form:form>
            </div>
        </div>
        <jsp:include page="../layout/footer.jsp" />
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
</body>
</html>