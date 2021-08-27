package price.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import price.service.model.PriceRangeResponse;
import price.service.model.ProductPriceResponse;
import price.service.model.ProductPrice;
import price.service.model.ProductPriceRepository;

@RestController
@RequestMapping("/product-prices")
public class ProductPriceService {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @GetMapping
    public ProductPriceResponse getProductPrices(@RequestParam(required = false) Double minPrice,
                                                 @RequestParam(required = false) Double maxPrice) {
        if (minPrice != null || maxPrice != null) {
            return new ProductPriceResponse(productPriceRepository.getInPriceRange(minPrice, maxPrice));
        } else {
            return new ProductPriceResponse(productPriceRepository.findAll());
        }
    }

    @GetMapping("/{id}")
    public ProductPrice getProductPrice(@PathVariable Long id) {
        return productPriceRepository.findById(id).get();
    }

    @GetMapping("/price-range")
    public PriceRangeResponse getPriceRange() {
        var minPrice = productPriceRepository.getMinPrice();
        var maxPrice = productPriceRepository.getMaxPrice();

        return new PriceRangeResponse(minPrice, maxPrice);
    }
}
