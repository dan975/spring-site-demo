package price.service.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

    @Query("SELECT min(P.price) FROM ProductPrice P")
    Double getMinPrice();

    @Query("SELECT max(P.price) FROM ProductPrice P")
    Double getMaxPrice();

    @Query("FROM ProductPrice P WHERE P.price BETWEEN (?1) AND (?2)")
    List<ProductPrice> getInPriceRange(Double minPrice, Double maxPrice);
}
