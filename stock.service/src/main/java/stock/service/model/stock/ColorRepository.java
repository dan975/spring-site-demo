package stock.service.model.stock;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends CrudRepository<Color, Long> {

    List<Color> findByProductKey(Long productKey);

    @Query("FROM Color C WHERE C.color.name IN (?1)")
    List<Color> findByNameIn(List<String> names);

    @Query("SELECT DISTINCT C.color.name, C.color.rgb FROM Color C")
    List<String[]> getDistinctFilterColors();
}
