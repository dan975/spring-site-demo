package stock.service.model.stock.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class StockFilterResponse {
    private final List<Long> productKeys;
}
