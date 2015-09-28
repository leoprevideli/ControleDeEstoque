package br.com.cast.turmaformacao.controledeestoque.model.http;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;


import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;

public class ProductService {

    private static final String URL = "http://10.11.21.193:4000/api/v1/products/";

    public ProductService() {
        super();
    }

    public static List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        try {
            URL url = new URL(URL);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            int responseCode = conn.getResponseCode();
            Log.i("getAllProducts",
                    "Código de retorno da requisição da lista de produtos: " + responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error code: " + responseCode);
            }

            InputStream inputStream = conn.getInputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class);
            products = objectMapper.readValue(inputStream, collectionType);

            inputStream.close();
            conn.disconnect();
        } catch (IOException e) {
            Log.e(ProductService.class.getName(), e.getMessage());
        }

        return products;
    }

    public static void saveProduct(Product product) {

        try {
            URL url = new URL(URL);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = conn.getOutputStream();

            new ObjectMapper().writeValue(outputStream, product);

            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error code: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            Log.e(ProductService.class.getName(), e.getMessage());
        }
    }

}
