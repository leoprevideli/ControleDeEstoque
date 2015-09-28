package br.com.cast.turmaformacao.controledeestoque.controller.asynctasks;

import android.os.AsyncTask;

import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;
import br.com.cast.turmaformacao.controledeestoque.model.http.ProductService;

public class SaveProductOnWebTask extends AsyncTask<Product, Void, Void> {
    @Override
    protected Void doInBackground(Product... products) {
        ProductService.saveProduct(products[0]);
        return null;
    }
}
