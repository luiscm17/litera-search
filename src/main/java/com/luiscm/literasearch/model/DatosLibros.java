package com.luiscm.literasearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
    int id,
    String title,
        List<DatosAutor> authors,
    List<String> languages,
    @JsonProperty("download_count") int downloadCount,
    List<String> subjects,
    List<String> bookshelves
) {
    public String getAuthorName() {
        if (authors != null && !authors.isEmpty()) {
                        return authors.get(0).nombre();
        }
        return "Desconocido";
    }
    
    public String getFirstLanguage() {
        if (languages != null && !languages.isEmpty()) {
            return languages.get(0);
        }
        return "Desconocido";
    }
}
