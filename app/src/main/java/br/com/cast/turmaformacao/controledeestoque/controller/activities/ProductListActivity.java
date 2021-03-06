package br.com.cast.turmaformacao.controledeestoque.controller.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import br.com.cast.turmaformacao.controledeestoque.R;
import br.com.cast.turmaformacao.controledeestoque.controller.adapters.ProductListAdapter;
import br.com.cast.turmaformacao.controledeestoque.controller.asynctasks.DeleteProductTask;
import br.com.cast.turmaformacao.controledeestoque.controller.asynctasks.FilterProductListTask;
import br.com.cast.turmaformacao.controledeestoque.controller.asynctasks.FindProductsTask;
import br.com.cast.turmaformacao.controledeestoque.controller.asynctasks.GetAllProductsFromWebTask;
import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;
import br.com.cast.turmaformacao.controledeestoque.model.services.ProductBusinessService;


public class ProductListActivity extends AppCompatActivity {

    private ListView listViewProducts;
    private Product selectedProduct;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        bindProductList();
        bindFab();
    }

    @Override
    protected void onResume() {
        //TODO Tirar coment�rio daqui... refreshList(); //get from web
        new UpdateProductList().execute(); // get from database

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

    private void updateFilteredList(List<Product> products) {
        ProductListAdapter adapter = (ProductListAdapter) listViewProducts.getAdapter();
        if (adapter == null) {
            adapter = new ProductListAdapter(this);
            listViewProducts.setAdapter(adapter);
        }
        adapter.setDataValues(products);
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

        switch (item.getItemId()){
            case R.id.menu_filter:
                createFilterDialog();
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

    private void bindFab(){
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProductFormActivity = new Intent(ProductListActivity.this, ProductFormActivity.class);
                startActivity(goToProductFormActivity);
                    }
            });
    }

    private void createFilterDialog(){
        LayoutInflater li = ProductListActivity.this.getLayoutInflater();

        View view = li.inflate(R.layout.filter_layout, null);
        final EditText filter_et_name = (EditText) view.findViewById(R.id.filter_et_name);
        final EditText filter_et_description = (EditText) view.findViewById(R.id.filter_et_description);
        final EditText filter_et_quantity = (EditText) view.findViewById(R.id.filter_et_quantity);
        final EditText filter_et_min_quantity = (EditText) view.findViewById(R.id.filter_et_min_quantity);
        final EditText filter_et_price = (EditText) view.findViewById(R.id.filter_et_price);

        new AlertDialog.Builder(ProductListActivity.this)
            .setView(view)
            .setTitle(R.string.lbl_filter)
            .setPositiveButton("Search",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Product filterProduct = new Product();
                            filterProduct.setName(filter_et_name.getText() == null ? "" : filter_et_name.getText().toString());
                            filterProduct.setDescription(filter_et_description.getText() == null ? "" : filter_et_description.getText().toString());
                            filterProduct.setQuantity(filter_et_quantity.getText().toString().equals("") ? null : Integer.parseInt(filter_et_quantity.getText().toString()));
                            filterProduct.setMinQuantity(filter_et_min_quantity.getText().toString().equals("") ? null : Integer.parseInt(filter_et_min_quantity.getText().toString()));
                            filterProduct.setPrice(filter_et_price.getText().toString().equals("") ? null : Double.parseDouble(filter_et_price.getText().toString()));

                            new FilterProductListTask(){
                                private ProgressDialog progressDialog;

                                @Override
                                protected void onPreExecute() {
                                    super.onPreExecute();
                                    progressDialog = new ProgressDialog(ProductListActivity.this);
                                    progressDialog.setMessage(getString(R.string.filtering_products));
                                    progressDialog.show();
                                }

                                @Override
                                protected void onPostExecute(List<Product> products) {
                                    super.onPostExecute(products);
                                    updateFilteredList(products);                                    progressDialog.dismiss();
                                }
                            }.execute(filterProduct);
                        }
                }).setNeutralButton("Cancel", null)
                .create()
            .show();

    }

}
