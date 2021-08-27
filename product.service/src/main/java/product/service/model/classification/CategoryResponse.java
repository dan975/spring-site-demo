package product.service.model.classification;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CategoryResponse {
    private final List<String> categories;
}
