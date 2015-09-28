package br.com.cast.turmaformacao.controledeestoque.model.services;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.List;

import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;
import br.com.cast.turmaformacao.controledeestoque.model.http.ProductService;
import br.com.cast.turmaformacao.controledeestoque.model.persistence.product.ProductRepository;

public final class ProductBusinessService {

    public ProductBusinessService() {
        super();
    }

    public static void save(Product product) {
        ProductRepository.save(product);
    }

    public static List<Product> getAll(){
        return  ProductRepository.getAll();
    }

    public static void delete(Product product){
        ProductRepository.delete(product);
    }

    public static Product getProductByWebId(long webId) {
        Product product =  ProductRepository.getProductByWebId(webId);
        if(product == null){
            return null;
        }
        else{
            return product;
        }
    }

    public static void synchronizedTasks() {
        saveWebProducts(ProductService.getAllProducts());
    }

    public static void saveWebProducts(List<Product> webProducts){
        for (Product product : webProducts) {
            Product verifyProductId = ProductBusinessService.getProductByWebId(product.getIdWeb());

            if (verifyProductId != null) { //update
                if(product.getDate() > verifyProductId.getDate()){
                    product.setId(verifyProductId.getId());
                    ProductBusinessService.save(product);
                }
            }
            else{
                ProductBusinessService.save(product);
            }

        }
    }



}
