package stock.service.model.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByProductKey(Long productKey);

    List<Stock> findByProductKeyIn(List<Long> productKeys);
}
