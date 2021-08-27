package com.example.demo.model.stock.responses;

import com.example.demo.model.stock.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SizeResponse {
    private final List<Size> sizes;
}
