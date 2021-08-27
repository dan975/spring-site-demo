package stock.service.model.stock.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import stock.service.model.stock.Stock;

import java.util.List;

@Data
@RequiredArgsConstructor
public class StockResponse {
    private final List<Stock> stockList;
}
