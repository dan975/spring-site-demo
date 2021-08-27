package product.service.model.product;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ProductResponse {
    private final List<Product> products;
}
