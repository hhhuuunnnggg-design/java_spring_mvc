<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <!-- start tmplate -->
    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
      rel="stylesheet"
    />

    <!-- Icon Font Stylesheet -->
    <link
      rel="stylesheet"
      href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
    />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
      rel="stylesheet"
    />

    <!-- Libraries Stylesheet -->
    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet" />
    <link
      href="/client/lib/owlcarousel/assets/owl.carousel.min.css"
      rel="stylesheet"
    />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/client/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Template Stylesheet -->
    <link href="/client/css/style.css" rel="stylesheet" />
    <!-- end template -->
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
  </head>
  <body>
   
    <!-- Spinner Start -->
    <div
      id="spinner"
      class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center"
    >
      <div class="spinner-grow text-primary" role="status"></div>
    </div>
    <!-- Spinner End -->

    <jsp:include page="../layout/header.jsp" />

    <!-- Hero Start -->
    <jsp:include page="../layout/banner.jsp" />
    <!-- Hero End -->

    <!-- Fruits Shop Start-->
    <div class="container-fluid fruite py-5">
      <div class="container py-5">
        <div class="tab-class text-center">
          <div class="row g-4">
            <div class="col-lg-4 text-start">
              <h1>Sản Phẩm nổi bật</h1>
            </div>
            <div class="col-lg-8 text-end">
              <ul class="nav nav-pills d-inline-flex text-center mb-5">
                <li class="nav-item">
                  <a
                    class="d-flex m-2 py-2 bg-light rounded-pill active"
                    href="/product"
                  >
                    <span class="text-dark" style="width: 130px">All Products</span>
                  </a>
                </li>
              </ul>

            </div>
          </div>
          <div class="tab-content">
            <div id="tab-1" class="tab-pane fade show p-0 active">
              <div class="row g-4">
                <div class="col-lg-12">
                  <div class="row g-4">
                    <!-- start product-->
                    <c:forEach items="${listProduct}" var="product">
                      <div class="col-md-6 col-lg-4 col-xl-3">
                        <div class="rounded position-relative fruite-item">
                          <div class="fruite-img">
                            <a href="/product/${product.id}">
                              <img
                                src="/images/product/${product.image}"
                                class="img-fluid w-100 rounded-top"
                                alt="${product.name}"
                              />
                            </a>
                          </div>
                          <div
                            class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                            style="top: 10px; left: 10px"
                          >
                            ${product.factory}
                          </div>
                          <div
                            class="p-4 border border-secondary border-top-0 rounded-bottom"
                          >
                            <h4>${product.name}</h4>
                            <p>${product.shortdesc}</p>
                            <div
                              class="d-flex justify-content-center flex-lg-wrap"
                            >
                              <p class="text-dark fs-5 fw-bold mb-0">
                                <fmt:formatNumber
                                  type="number"
                                  value="${product.price}"
                                />vnđ
                              </p>
                              <form
                                action="/add-product-to-cart/${product.id}"
                                method="post"
                              >
                                <!-- tạo token -->
                                <input
                                 style="display:none;"
                                  type="text"
                                  name="${_csrf.parameterName}"
                                  value="${_csrf.token}"
                                  readonly
                                />

                                <button
                                  class="mx-auto btn border border-secondary rounded-pill px-3 text-primary"
                                >
                                  <i
                                    class="fa fa-shopping-bag me-2 text-primary"
                                  ></i>
                                  Add to cart
                                </button>
                              </form>
                            </div>
                          </div>
                        </div>
                      </div>
                    </c:forEach>

                    <!-- end -->
                  </div>
                </div>
              </div>
            </div>
            <div id="tab-2" class="tab-pane fade show p-0">
              <div class="row g-4">
                <div class="col-lg-12">
                  <div class="row g-4">
                    <div class="col-md-6 col-lg-4 col-xl-3">
                      <div class="rounded position-relative fruite-item">
                        <div class="fruite-img">
                          <img
                            src="/client/img/fruite-item-5.jpg"
                            class="img-fluid w-100 rounded-top"
                            alt=""
                          />
                        </div>
                        <div
                          class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                          style="top: 10px; left: 10px"
                        >
                          Fruits
                        </div>
                        <div
                          class="p-4 border border-secondary border-top-0 rounded-bottom"
                        >
                          <h4>Grapes</h4>
                          <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing
                            elit sed do eiusmod te incididunt
                          </p>
                          <div
                            class="d-flex justify-content-between flex-lg-wrap"
                          >
                            <p class="text-dark fs-5 fw-bold mb-0">
                              $4.99 / kg
                            </p>
                            <a
                              href="#"
                              class="btn border border-secondary rounded-pill px-3 text-primary"
                              ><i
                                class="fa fa-shopping-bag me-2 text-primary"
                              ></i>
                              Add to cart</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6 col-lg-4 col-xl-3">
                      <div class="rounded position-relative fruite-item">
                        <div class="fruite-img">
                          <img
                            src="/client/img/fruite-item-2.jpg"
                            class="img-fluid w-100 rounded-top"
                            alt=""
                          />
                        </div>
                        <div
                          class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                          style="top: 10px; left: 10px"
                        >
                          Fruits
                        </div>
                        <div
                          class="p-4 border border-secondary border-top-0 rounded-bottom"
                        >
                          <h4>Raspberries</h4>
                          <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing
                            elit sed do eiusmod te incididunt
                          </p>
                          <div
                            class="d-flex justify-content-between flex-lg-wrap"
                          >
                            <p class="text-dark fs-5 fw-bold mb-0">
                              $4.99 / kg
                            </p>
                            <a
                              href="#"
                              class="btn border border-secondary rounded-pill px-3 text-primary"
                              ><i
                                class="fa fa-shopping-bag me-2 text-primary"
                              ></i>
                              Add to cart</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div id="tab-3" class="tab-pane fade show p-0">
              <div class="row g-4">
                <div class="col-lg-12">
                  <div class="row g-4">
                    <div class="col-md-6 col-lg-4 col-xl-3">
                      <div class="rounded position-relative fruite-item">
                        <div class="fruite-img">
                          <img
                            src="/client/img/fruite-item-1.jpg"
                            class="img-fluid w-100 rounded-top"
                            alt=""
                          />
                        </div>
                        <div
                          class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                          style="top: 10px; left: 10px"
                        >
                          Fruits
                        </div>
                        <div
                          class="p-4 border border-secondary border-top-0 rounded-bottom"
                        >
                          <h4>Oranges</h4>
                          <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing
                            elit sed do eiusmod te incididunt
                          </p>
                          <div
                            class="d-flex justify-content-between flex-lg-wrap"
                          >
                            <p class="text-dark fs-5 fw-bold mb-0">
                              $4.99 / kg
                            </p>
                            <a
                              href="#"
                              class="btn border border-secondary rounded-pill px-3 text-primary"
                              ><i
                                class="fa fa-shopping-bag me-2 text-primary"
                              ></i>
                              Add to cart</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6 col-lg-4 col-xl-3">
                      <div class="rounded position-relative fruite-item">
                        <div class="fruite-img">
                          <img
                            src="/client/img/fruite-item-6.jpg"
                            class="img-fluid w-100 rounded-top"
                            alt=""
                          />
                        </div>
                        <div
                          class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                          style="top: 10px; left: 10px"
                        >
                          Fruits
                        </div>
                        <div
                          class="p-4 border border-secondary border-top-0 rounded-bottom"
                        >
                          <h4>Apple</h4>
                          <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing
                            elit sed do eiusmod te incididunt
                          </p>
                          <div
                            class="d-flex justify-content-between flex-lg-wrap"
                          >
                            <p class="text-dark fs-5 fw-bold mb-0">
                              $4.99 / kg
                            </p>
                            <a
                              href="#"
                              class="btn border border-secondary rounded-pill px-3 text-primary"
                              ><i
                                class="fa fa-shopping-bag me-2 text-primary"
                              ></i>
                              Add to cart</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div id="tab-4" class="tab-pane fade show p-0">
              <div class="row g-4">
                <div class="col-lg-12">
                  <div class="row g-4">
                    <div class="col-md-6 col-lg-4 col-xl-3">
                      <div class="rounded position-relative fruite-item">
                        <div class="fruite-img">
                          <img
                            src="/client/img/fruite-item-5.jpg"
                            class="img-fluid w-100 rounded-top"
                            alt=""
                          />
                        </div>
                        <div
                          class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                          style="top: 10px; left: 10px"
                        >
                          Fruits
                        </div>
                        <div
                          class="p-4 border border-secondary border-top-0 rounded-bottom"
                        >
                          <h4>Grapes</h4>
                          <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing
                            elit sed do eiusmod te incididunt
                          </p>
                          <div
                            class="d-flex justify-content-between flex-lg-wrap"
                          >
                            <p class="text-dark fs-5 fw-bold mb-0">
                              $4.99 / kg
                            </p>
                            <a
                              href="#"
                              class="btn border border-secondary rounded-pill px-3 text-primary"
                              ><i
                                class="fa fa-shopping-bag me-2 text-primary"
                              ></i>
                              Add to cart</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6 col-lg-4 col-xl-3">
                      <div class="rounded position-relative fruite-item">
                        <div class="fruite-img">
                          <img
                            src="/client/img/fruite-item-4.jpg"
                            class="img-fluid w-100 rounded-top"
                            alt=""
                          />
                        </div>
                        <div
                          class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                          style="top: 10px; left: 10px"
                        >
                          Fruits
                        </div>
                        <div
                          class="p-4 border border-secondary border-top-0 rounded-bottom"
                        >
                          <h4>Apricots</h4>
                          <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing
                            elit sed do eiusmod te incididunt
                          </p>
                          <div
                            class="d-flex justify-content-between flex-lg-wrap"
                          >
                            <p class="text-dark fs-5 fw-bold mb-0">
                              $4.99 / kg
                            </p>
                            <a
                              href="#"
                              class="btn border border-secondary rounded-pill px-3 text-primary"
                              ><i
                                class="fa fa-shopping-bag me-2 text-primary"
                              ></i>
                              Add to cart</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div id="tab-5" class="tab-pane fade show p-0">
              <div class="row g-4">
                <div class="col-lg-12">
                  <div class="row g-4">
                    <div class="col-md-6 col-lg-4 col-xl-3">
                      <div class="rounded position-relative fruite-item">
                        <div class="fruite-img">
                          <img
                            src="/client/img/fruite-item-3.jpg"
                            class="img-fluid w-100 rounded-top"
                            alt=""
                          />
                        </div>
                        <div
                          class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                          style="top: 10px; left: 10px"
                        >
                          Fruits
                        </div>
                        <div
                          class="p-4 border border-secondary border-top-0 rounded-bottom"
                        >
                          <h4>Banana</h4>
                          <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing
                            elit sed do eiusmod te incididunt
                          </p>
                          <div
                            class="d-flex justify-content-between flex-lg-wrap"
                          >
                            <p class="text-dark fs-5 fw-bold mb-0">
                              $4.99 / kg
                            </p>
                            <a
                              href="#"
                              class="btn border border-secondary rounded-pill px-3 text-primary"
                              ><i
                                class="fa fa-shopping-bag me-2 text-primary"
                              ></i>
                              Add to cart</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6 col-lg-4 col-xl-3">
                      <div class="rounded position-relative fruite-item">
                        <div class="fruite-img">
                          <img
                            src="/client/img/fruite-item-2.jpg"
                            class="img-fluid w-100 rounded-top"
                            alt=""
                          />
                        </div>
                        <div
                          class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                          style="top: 10px; left: 10px"
                        >
                          Fruits
                        </div>
                        <div
                          class="p-4 border border-secondary border-top-0 rounded-bottom"
                        >
                          <h4>Raspberries</h4>
                          <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing
                            elit sed do eiusmod te incididunt
                          </p>
                          <div
                            class="d-flex justify-content-between flex-lg-wrap"
                          >
                            <p class="text-dark fs-5 fw-bold mb-0">
                              $4.99 / kg
                            </p>
                            <a
                              href="#"
                              class="btn border border-secondary rounded-pill px-3 text-primary"
                              ><i
                                class="fa fa-shopping-bag me-2 text-primary"
                              ></i>
                              Add to cart</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6 col-lg-4 col-xl-3">
                      <div class="rounded position-relative fruite-item">
                        <div class="fruite-img">
                          <img
                            src="/client/img/fruite-item-1.jpg"
                            class="img-fluid w-100 rounded-top"
                            alt=""
                          />
                        </div>
                        <div
                          class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                          style="top: 10px; left: 10px"
                        >
                          Fruits
                        </div>
                        <div
                          class="p-4 border border-secondary border-top-0 rounded-bottom"
                        >
                          <h4>Oranges</h4>
                          <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing
                            elit sed do eiusmod te incididunt
                          </p>
                          <div
                            class="d-flex justify-content-between flex-lg-wrap"
                          >
                            <p class="text-dark fs-5 fw-bold mb-0">
                              $4.99 / kg
                            </p>
                            <a
                              href="#"
                              class="btn border border-secondary rounded-pill px-3 text-primary"
                              ><i
                                class="fa fa-shopping-bag me-2 text-primary"
                              ></i>
                              Add to cart</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <%-- start pagination --%>
      <nav aria-label="Page navigation example">
      <ul class="pagination justify-content-center" style="display:flex">
       <li class="page-item"><a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                            href="/admin/product?page=${currentPage - 1}"
                                                            aria-label="Previous">
                                                            <span aria-hidden="true">trước</span>
                                                        </a></li>

                  <c:forEach var="counter" begin="0" end="${totalPages-1}" varStatus="loop">
                    <li class="page-item">
                      <a class="${(loop.index + 1) eq currentPage ? 'active page-link' : 'page-link'}"
                       href="?page=${loop.index+1}">${loop.index+1}</a>
                    </li>
                  </c:forEach>

                   <li class="page-item"><a class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                            href="/admin/product?page=${currentPage + 1}"
                                                            aria-label="Previous">
                                                            <span aria-hidden="true">sau</span>
                                                        </a></li>
      </ul>
      <%-- end pagination --%>
    </nav>
    </div>
     
    <!-- Fruits Shop End-->
    <!-- Featurs Section Start -->
    <jsp:include page="../layout/feature.jsp" />
    <!-- Featurs Section End -->
 

    <!-- Footer Start -->
    <jsp:include page="../layout/footer.jsp" />

   
    <!-- Copyright End -->

    <!-- Back to Top -->
    <a
      href="#"
      class="btn btn-primary border-3 border-primary rounded-circle back-to-top"
      ><i class="fa fa-arrow-up"></i
    ></a>

    <!-- JavaScript Libraries -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/client/lib/waypoints/waypoints.min.js"></script>
    <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
    <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="/client/lib/easing/easing.min.js"></script>

    <!-- Template Javascript -->
    <script src="/client/js/main.js"></script>
  </body>
</html>
