package com.luiscm.literasearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResponse(
    int count,
    String next,
    String previous,
    List<DatosLibros> results
) {}
