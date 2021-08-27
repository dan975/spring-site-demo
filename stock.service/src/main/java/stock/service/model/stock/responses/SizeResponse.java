package stock.service.model.stock.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import stock.service.model.stock.Size;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SizeResponse {
    private final List<Size> sizes;
}
