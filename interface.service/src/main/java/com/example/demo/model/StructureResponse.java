package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class StructureResponse {
    private Map<String, List<String>> categorySubCategoryMap;
}
