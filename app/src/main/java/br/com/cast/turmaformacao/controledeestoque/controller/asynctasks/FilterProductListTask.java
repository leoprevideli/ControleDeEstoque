package br.com.cast.turmaformacao.controledeestoque.controller.asynctasks;


import android.os.AsyncTask;

import java.util.List;

import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;
import br.com.cast.turmaformacao.controledeestoque.model.services.ProductBusinessService;

public class FilterProductListTask extends AsyncTask<Product, Void, List<Product>>{
    @Override
    protected List<Product> doInBackground(Product... products) {
        return ProductBusinessService.getAllFiltered(products[0]);
    }
}
