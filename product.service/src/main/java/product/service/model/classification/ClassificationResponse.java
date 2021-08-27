package product.service.model.classification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ClassificationResponse {
    private final String category;
    private String subcategory;
}
