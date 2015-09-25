package br.com.cast.turmaformacao.controledeestoque.controller.asynctasks;

import android.os.AsyncTask;

import java.util.List;

import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;
import br.com.cast.turmaformacao.controledeestoque.model.services.ProductBusinessService;


public class FindProductsTask extends AsyncTask<Void, Void, List<Product>> {

    @Override
    protected List<Product> doInBackground(Void... voids) {
        return ProductBusinessService.getAll();
    }

}
