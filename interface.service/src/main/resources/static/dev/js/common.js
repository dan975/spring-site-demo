const currencyFormatter = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'});

$(document).ready(function () {
    $('#myModal').on('show.bs.modal', function(e) {
        $('#myModal').css("margin-top","0px")
    });

    $('#myModal').on('hide.bs.modal', function(e) {
        $('#myModal').css("margin-top","5000px");

        $('#productModalSpinner').show();
        $('#productModal').hide();
    });
});

function cartRemove(productId) {
    const url = 'cart?id=' + productId;

    fetch(url, {
        method: 'DELETE',
        headers: {
            'X-CSRF-TOKEN' : $("meta[name='_csrf']").attr("content")
        }
    })
    .then(e => {
        $(`#cartItem${productId}`).fadeOut({complete: function(){
                $(this).remove();
                updateCart(true);
            }
        });
    })
    .catch(err => {
        console.log(err);
    });
}

function cardAdd(productId) {
    const url = 'cart/add?id=' + productId;

    fetch(url)
      .then(response => response.json())
      .then(e => {
        $('#productModal #modalImage').attr("src", "data:image/jpeg;base64," + e.addedItem.image.imageBase64);
        $('#productModal #name').html(e.addedItem.name);
        $('#productModal #subSelection').html(e.addedItem.size);
        $('#productModal #quantity').html(e.addedItem.count);
        $('#productModal #totalCurrentProduct').html('$' + (e.addedItem.count * e.addedItem.price));

        var nextTotalCountText = e.cart.totalCount == 1 ? 'You have 1 item in your cart'
                                                   : `You have ${e.cart.totalCount} items in your cart`;
        $('#productModal #totalCountText').html(nextTotalCountText);
        $('#productModal #totalProducts').html(currencyFormatter.format(e.cart.totalCost));
        $('#productModal #totalShipping').html(currencyFormatter.format(e.cart.shippingCost));
        $('#productModal #total').html(currencyFormatter.format(e.cart.totalWithShipping));

        updateCart();

        $('#productModalSpinner').hide();
        $('#productModal').fadeIn();
      })
    .catch(err => {
        console.log(err);
    });
}

function updateCart(isExpanded) {
    var url = "cart/";

    fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => response.text())
        .then(html => {
            $('#cart').html(html);
            if (isExpanded) {
                $('#cartButton').click();
                $('#cartDropdown').removeClass('dropdown');
                $('#cartDropdown').addClass('dropup');
            }
        });

}

$(window).scroll(function() {
    let categoryNavBar = $('#categoryNavBar');

    if (categoryNavBar.length) {
        var yPos = categoryNavBar[0].getBoundingClientRect().y;

        if (yPos === 80) {
            categoryNavBar.addClass('category-nav-expand')
        } else {
            categoryNavBar.removeClass('category-nav-expand')
        }
    }
});

$(document).ready(function() {
  handleCategorySidePanel();
});

$(window).resize(function() {
  handleCategorySidePanel();
});

function handleCategorySidePanel() {
  if ($('#categorySidePanel').length) {
    if ($(window).width() > 767) {
        handleExpandedCatalog();
    } else {
        handleCollapsedCatalog();
    }
  }
}

function handleExpandedCatalog() {
    $('#categorySidePanel .categoryToggle > :not(.collapse)').each(function() {
        $(this).removeAttr('data-toggle');
    });
    $('#categorySidePanel .categoryToggle > .collapse').each(function() {
        $(this).addClass('show');
    });
}

function handleCollapsedCatalog() {
    $('#categorySidePanel .categoryToggle > :not(.collapse)').each(function() {
        $(this).attr('data-toggle', 'collapse');
    });
    $('#categorySidePanel .categoryToggle > .collapse').each(function() {
        $(this).removeClass('show');
    });
}

$(document).ready(function () {
    $( function() {
        var limits = $('#priceLimits');

        var min = parseFloat(limits.attr('min'));
        var max = parseFloat(limits.attr('max'));

        $("#priceMin").html(currencyFormatter.format(min));
        $("#priceMax").html(currencyFormatter.format(max));

        $("#priceSlider").slider({
            min : min,
            max: max,
            range: true,
            step : 0.01,
            values : [min, max],
            slide: function(event, ui) {
                if ((ui.values[0]) >= ui.values[1]) {
                    return false;
                }

                $("#priceMin").html(currencyFormatter.format(ui.values[0]));
                $("#priceMax").html(currencyFormatter.format(ui.values[1]));
            },
            stop: function(event, ui) {
                filterCategories();
            }
        });
    });
});

function retrieveFoo() {
    var url = '/foo';
    $("#fooContainer").load(url);
}

function changeEnabled(isDisabled) {
    document.querySelectorAll('#collapse1 input')
        .forEach(e => e.disabled = isDisabled);

    $('#priceSlider').slider(isDisabled ? 'disable' : 'enable');
}

function filterCategories() {
    var url = "catalog";

    changeEnabled(true);

    var sizeSelection=[];
    $('#sizeList li').map(function() {
        var size = $(this).find('span').text();
        var isSelected = $(this).find('input').is(':checked');

        sizeSelection.push([size, isSelected.toString()]);
    });

    var colorSelection=[];
    $('#colorList li').map(function() {
        var color = $(this).find('span').text();
        var isSelected = $(this).find('input').is(':checked');

        colorSelection.push([color, isSelected.toString()]);
    });

    var isInStock = $('#stockChk').is(':checked');

    var sliderValues = $('#priceSlider').slider("option", "values");
    var minPrice = sliderValues[0];
    var maxPrice = sliderValues[1];

    var body = {
        sizes: sizeSelection,
        colors: colorSelection,
        isInStock: isInStock,
        minPrice,
        maxPrice
    };

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN' : $("meta[name='_csrf']").attr("content")
        },
        body: JSON.stringify(body)
    })
    .then(response => response.text())
    .then(html => {
        changeEnabled(false);
        $('#productPanel').html(html);

        $('#myModal').on('show.bs.modal', function(e) {
            $('#myModal').css("margin-top","0px")
        });
    });
}
