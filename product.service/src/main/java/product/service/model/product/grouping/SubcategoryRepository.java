package product.service.model.product.grouping;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends CrudRepository<Subcategory, Long> {

    @Query("SELECT DISTINCT S.title FROM Subcategory S")
    List<String> findDistinctSubcategories();
}
