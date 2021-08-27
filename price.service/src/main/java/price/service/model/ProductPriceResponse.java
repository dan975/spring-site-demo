package price.service.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ProductPriceResponse {
    private final List<ProductPrice> prices;
}
