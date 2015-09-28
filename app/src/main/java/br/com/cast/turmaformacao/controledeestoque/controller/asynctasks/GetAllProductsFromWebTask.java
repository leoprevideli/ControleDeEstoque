package br.com.cast.turmaformacao.controledeestoque.controller.asynctasks;

import android.os.AsyncTask;

import br.com.cast.turmaformacao.controledeestoque.model.services.ProductBusinessService;

public class GetAllProductsFromWebTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        ProductBusinessService.synchronizedTasks();
        return null;
    }
}
