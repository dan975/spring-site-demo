package product.service.model.product.grouping;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupingRepository extends CrudRepository<Grouping, Long> {

    @Query("SELECT DISTINCT G.category.title, G.subcategory.title FROM Grouping G")
    List<String[]> findUniqueCategoryAndSubcategory();
}
