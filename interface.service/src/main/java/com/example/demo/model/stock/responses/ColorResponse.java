package com.example.demo.model.stock.responses;

import com.example.demo.model.stock.Color;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ColorResponse {
    private final List<Color> colors;
}
