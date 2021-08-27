package stock.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stock.service.model.stock.Color;
import stock.service.model.stock.ColorRepository;
import stock.service.model.stock.SizeRepository;
import stock.service.model.stock.Stock;
import stock.service.model.stock.StockDetails;
import stock.service.model.stock.StockRepository;
import stock.service.model.stock.responses.ColorResponse;
import stock.service.model.stock.responses.SizeResponse;
import stock.service.model.stock.responses.StockFilterResponse;
import stock.service.model.stock.responses.StockResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @GetMapping
    public StockResponse showStock(@RequestParam(required = false) List<Long> productKeys) {
        if (productKeys != null) {
            return new StockResponse(stockRepository.findByProductKeyIn(productKeys));
        } else {
            return new StockResponse(stockRepository.findAll());
        }
    }

    @GetMapping("/filter")
    public StockFilterResponse getStock(@RequestParam List<String> colors, @RequestParam List<String> sizes,
                                  @RequestParam Boolean isInStock) {
        var matchingColors = colorRepository.findByNameIn(colors);
        var matchingSizes = sizeRepository.findByNameIn(sizes);

        var productKeyIds = matchingColors.stream()
                .map(Color::getProductKey)
                .filter(colorId -> matchingSizes.stream().anyMatch(size -> size.getProductKey().equals(colorId)))
                .distinct()
                .collect(Collectors.toList());
        if (isInStock) {
            productKeyIds = stockRepository.findByProductKeyIn(productKeyIds)
                    .stream()
                    .filter(stock -> colors.contains(stock.getColor().getName()))
                    .filter(stock -> sizes.contains(stock.getSize().getName()))
                    .map(Stock::getProductKey)
                    .distinct()
                    .collect(Collectors.toList());
        }

        return new StockFilterResponse(productKeyIds);
    }

    @GetMapping("/colors")
    public ColorResponse getColors(@RequestParam List<String> colors) {
        return new ColorResponse(colorRepository.findByNameIn(colors));
    }

    @GetMapping("/sizes")
    public SizeResponse getSizes(@RequestParam List<String> sizes) {
        return new SizeResponse(sizeRepository.findByNameIn(sizes));
    }

    @GetMapping("/details")
    public StockDetails getDetails() {
        var colors = colorRepository.getDistinctFilterColors();
        var sizes = sizeRepository.getDistinctSizes();

        return new StockDetails(colors, sizes);
    }
}
