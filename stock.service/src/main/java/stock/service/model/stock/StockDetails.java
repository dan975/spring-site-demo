package stock.service.model.stock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StockDetails {
    private final List<String[]> colors;
    private final List<String> sizes;
}
