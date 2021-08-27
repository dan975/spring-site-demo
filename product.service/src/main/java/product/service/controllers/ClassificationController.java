package product.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import product.service.model.classification.CategoryResponse;
import product.service.model.classification.ClassificationResponse;
import product.service.model.classification.SubcategoryResponse;
import product.service.model.product.grouping.CategoryRepository;
import product.service.model.product.grouping.SubcategoryRepository;

@RestController
public class ClassificationController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @GetMapping("/categories")
    public CategoryResponse getCategories() {
        return new CategoryResponse(categoryRepository.findDistinctCategories());
    }

    @GetMapping("/subcategories")
    public SubcategoryResponse getSubcategories() {
        return new SubcategoryResponse(subcategoryRepository.findDistinctSubcategories());
    }

    @GetMapping("/classification")
    public ClassificationResponse getClassification(@RequestParam Long categoryId, @RequestParam(required = false) Long subcategoryId) {
        var category = categoryRepository.findById(categoryId)
                .get()
                .getTitle();
        if (subcategoryId != null) {
            var subcategory = subcategoryRepository.findById(subcategoryId)
                    .get()
                    .getTitle();
            return new ClassificationResponse(category, subcategory);
        } else {
            return new ClassificationResponse(category);
        }
    }
}
