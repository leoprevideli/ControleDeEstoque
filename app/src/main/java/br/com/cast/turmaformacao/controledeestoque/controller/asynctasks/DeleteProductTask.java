package br.com.cast.turmaformacao.controledeestoque.controller.asynctasks;

import android.os.AsyncTask;

import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;
import br.com.cast.turmaformacao.controledeestoque.model.services.ProductBusinessService;

public class DeleteProductTask extends AsyncTask<Product, Void, Void> {

    @Override
    protected Void doInBackground(Product...product) {
        ProductBusinessService.delete(product[0]);
        return null;
    }

}
