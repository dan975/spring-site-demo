package stock.service.model.stock.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import stock.service.model.stock.Color;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ColorResponse {
    private final List<Color> colors;
}
