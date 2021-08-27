package product.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import product.service.model.classification.StructureResponse;
import product.service.model.product.Product;
import product.service.model.product.ProductRepository;
import product.service.model.product.ProductResponse;
import product.service.model.product.grouping.GroupingRepository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GroupingRepository groupingRepository;

    @GetMapping
    public ProductResponse showProducts(@RequestParam(required = false) List<Long> ids) {
        if (ids != null) {
            return new ProductResponse(productRepository.findByIdIn(ids));
        } else {
            return new ProductResponse(productRepository.findAll());
        }
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productRepository.findById(id).get();
    }

    @GetMapping("/filter")
    public ProductResponse getByContainingInName(@RequestParam String name) {
        return new ProductResponse(productRepository.findByProductNameContaining(name));
    }

    @GetMapping("/structure")
    public StructureResponse getStructure() {
        var queryResult = groupingRepository.findUniqueCategoryAndSubcategory();

        return getResponse(queryResult);
    }

    private StructureResponse getResponse(List<String[]> queryResults) {
        Map<String, List<String>> result = new HashMap<>();

        queryResults.forEach(grouping -> {
            if (!result.containsKey(grouping[0])) {
                result.put(grouping[0], new LinkedList<>());
            }

            result.get(grouping[0]).add(grouping[1]);
        });

        return new StructureResponse(result);
    }
}
