package br.com.cast.turmaformacao.controledeestoque.model.services;

import java.util.List;

import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;
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
}
