package product.service.model.classification;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class StructureResponse {
    private final Map<String, List<String>> categorySubCategoryMap;
}
