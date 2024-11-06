(function ($) {
  "use strict";

  // Spinner
  var spinner = function () {
    setTimeout(function () {
      if ($("#spinner").length > 0) {
        $("#spinner").removeClass("show");
      }
    }, 1);
  };
  spinner(0);

  // Fixed Navbar
  $(window).scroll(function () {
    if ($(window).width() < 992) {
      if ($(this).scrollTop() > 55) {
        $(".fixed-top").addClass("shadow");
      } else {
        $(".fixed-top").removeClass("shadow");
      }
    } else {
      if ($(this).scrollTop() > 55) {
        $(".fixed-top").addClass("shadow").css("top", 0);
      } else {
        $(".fixed-top").removeClass("shadow").css("top", 0);
      }
    }
  });

  // Back to top button
  $(window).scroll(function () {
    if ($(this).scrollTop() > 300) {
      $(".back-to-top").fadeIn("slow");
    } else {
      $(".back-to-top").fadeOut("slow");
    }
  });
  $(".back-to-top").click(function () {
    $("html, body").animate({ scrollTop: 0 }, 1500, "easeInOutExpo");
    return false;
  });

  // Testimonial carousel
  $(".testimonial-carousel").owlCarousel({
    autoplay: true,
    smartSpeed: 2000,
    center: false,
    dots: true,
    loop: true,
    margin: 25,
    nav: true,
    navText: [
      '<i class="bi bi-arrow-left"></i>',
      '<i class="bi bi-arrow-right"></i>',
    ],
    responsiveClass: true,
    responsive: {
      0: { items: 1 },
      576: { items: 1 },
      768: { items: 1 },
      992: { items: 2 },
      1200: { items: 2 },
    },
  });

  // Vegetable carousel
  $(".vegetable-carousel").owlCarousel({
    autoplay: true,
    smartSpeed: 1500,
    center: false,
    dots: true,
    loop: true,
    margin: 25,
    nav: true,
    navText: [
      '<i class="bi bi-arrow-left"></i>',
      '<i class="bi bi-arrow-right"></i>',
    ],
    responsiveClass: true,
    responsive: {
      0: { items: 1 },
      576: { items: 1 },
      768: { items: 2 },
      992: { items: 3 },
      1200: { items: 4 },
    },
  });

  // Format currency function for Vietnamese Dong
  function formatCurrency(value) {
    const formatter = new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
    });
    return formatter.format(value);
  }

  // Update total cart price function
  function updateTotalCartPrice() {
    let total = 0;
    $(".quantity input").each(function () {
      const quantity = parseInt($(this).val());
      const price = parseFloat($(this).data("cart-detail-price"));
      total += quantity * price;
    });

    // Update the total price in the UI
    $(".cart-total-price").text(formatCurrency(total));
    $(".cart-total-price").attr("data-cart-total-price", total);
  }

  // Product Quantity Change Event
  $(".quantity button").on("click", function () {
    const button = $(this);
    const input = button.closest(".quantity").find("input");
    let oldValue = parseInt(input.val());

    // Determine if the button is increment or decrement
    if (button.hasClass("btn-plus")) {
      oldValue += 1;
    } else {
      oldValue = oldValue > 1 ? oldValue - 1 : 1; // Decrement but not below 1
    }

    input.val(oldValue); // Set new quantity in input

    // Update individual product total price
    const price = parseFloat(input.data("cart-detail-price"));
    const productId = input.data("cart-detail-id");
    const newPrice = price * oldValue;
    $(`p[data-cart-detail-id='${productId}']`).text(formatCurrency(newPrice));

    // Update total cart price
    updateTotalCartPrice();
  });
})(jQuery);
