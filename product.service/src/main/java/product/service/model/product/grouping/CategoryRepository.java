package product.service.model.product.grouping;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query("SELECT DISTINCT C.title FROM Category C")
    List<String> findDistinctCategories();
}
