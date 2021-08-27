package product.service.model.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByIdIn(List<Long> ids);

    List<Product> findByProductNameContaining(String productName);

    @Query("SELECT P FROM Product P WHERE P.grouping.subcategory.title IN (?1)")
    List<Product> findBySubcategories(List<String> subcategories, double minPrice, double maxPrice);
}