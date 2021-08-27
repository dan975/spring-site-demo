package stock.service.model.stock;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends CrudRepository<Size, Long> {
    List<Size> findByProductKey(Long productKey);

    @Query("FROM Size S WHERE S.size.name IN (?1)")
    List<Size> findByNameIn(List<String> names);

    @Query("SELECT DISTINCT S.size.name FROM Size S")
    List<String> getDistinctSizes();


}
