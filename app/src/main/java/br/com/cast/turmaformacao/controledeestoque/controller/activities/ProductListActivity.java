package br.com.cast.turmaformacao.controledeestoque.controller.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.cast.turmaformacao.controledeestoque.R;
import br.com.cast.turmaformacao.controledeestoque.controller.adapters.ProductListAdapter;
import br.com.cast.turmaformacao.controledeestoque.controller.asynctasks.DeleteProductTask;
import br.com.cast.turmaformacao.controledeestoque.controller.asynctasks.FindProductsTask;
import br.com.cast.turmaformacao.controledeestoque.controller.asynctasks.GetAllProductsFromWebTask;
import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;
import br.com.cast.turmaformacao.controledeestoque.model.services.ProductBusinessService;


public class ProductListActivity extends AppCompatActivity {

    private ListView listViewProducts;
    private Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        bindProductList();
    }

    @Override
    protected void onResume() {
        new UpdateProductList().execute();
        super.onResume();
    }

    private void updateList() {
        ProductListAdapter adapter = (ProductListAdapter) listViewProducts.getAdapter();
        if (adapter == null) {
            adapter = new ProductListAdapter(this);
            listViewProducts.setAdapter(adapter);
        }
        adapter.setDataValues(ProductBusinessService.getAll());
        adapter.notifyDataSetChanged();
    }

    private void bindProductList() {
        listViewProducts = (ListView) findViewById(R.id.listViewProductsList);
        registerForContextMenu(listViewProducts);
        listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ProductListAdapter adapter = (ProductListAdapter) listViewProducts.getAdapter();
                selectedProduct = adapter.getItem(position);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add:
                onMenuAddClick();
                break;
            case R.id.menu_get_products:
                refreshList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshList() {

        new GetAllProductsFromWebTask() {
            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(ProductListActivity.this);
                progressDialog.setMessage("Searching products...");
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                updateList();
                progressDialog.dismiss();
            }
        }.execute();
    }

    private void onMenuAddClick() {
        Intent goToProductFormActivity = new Intent(ProductListActivity.this, ProductFormActivity.class);
        startActivity(goToProductFormActivity);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context_product_list, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                onMenuDeleteClick();
                break;
            case R.id.menu_edit:
                onMenuEditClick();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void onMenuEditClick() {
        Intent goToProductForm = new Intent(ProductListActivity.this, ProductFormActivity.class);
        goToProductForm.putExtra(ProductFormActivity.PARAM_PRODUCT, selectedProduct);
        startActivity(goToProductForm);
    }

    private void onMenuDeleteClick() {
        new AlertDialog.Builder(ProductListActivity.this)
                .setTitle(getString(R.string.confirm_message))
                .setMessage(getString(R.string.confirm_delete))
                .setPositiveButton(getString(R.string.lbl_yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                new DeleteProductTask(){
                                    private ProgressDialog progressDialog;
                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();
                                        progressDialog = new ProgressDialog(ProductListActivity.this);
                                        progressDialog.setMessage(getString(R.string.msg_deletting_product));
                                        progressDialog.show();
                                    }

                                    @Override
                                    protected void onPostExecute(Void param) {
                                        super.onPostExecute(param);
                                        progressDialog.dismiss();
                                    }
                                }.execute(selectedProduct);
                                selectedProduct = null;
                                String message = getString(R.string.succesfully_deleted);
                                new UpdateProductList().execute();
                                Toast.makeText(ProductListActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        })
                .setNeutralButton(getString(R.string.lbl_no), null)
                .create()
                .show();
    }

    private class UpdateProductList extends FindProductsTask {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProductListActivity.this);
            progressDialog.setMessage(getText(R.string.update_product_list));
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(List<Product> tasks) {
            super.onPostExecute(tasks);
            updateList();
            progressDialog.dismiss();
        }
    }

}
