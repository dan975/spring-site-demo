package price.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceRangeResponse {
    private final Double minPrice;
    private final Double maxPrice;
}
