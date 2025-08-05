package com.lliteraturasearch.literasearch;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lliteraturasearch.literasearch.services.ConsumoAPI;

@SpringBootApplication
public class LiterasearchApplication {

	public static void main(String[] args) {
		var ConsumoAPI = new ConsumoAPI();
		var json = ConsumoAPI.obtenerDatos("https://gutendex.com/books/");
		System.out.println(json);
	}

}
