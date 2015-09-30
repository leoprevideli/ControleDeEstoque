package br.com.cast.turmaformacao.controledeestoque.controller.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import br.com.cast.turmaformacao.controledeestoque.R;
import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;
import br.com.cast.turmaformacao.controledeestoque.model.services.ProductBusinessService;
import br.com.cast.turmaformacao.controledeestoque.util.FormHelper;

public class ProductFormActivity extends AppCompatActivity {

    public static final String PARAM_PRODUCT = "PARAM_PRODUCT";
    private Product product;
    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextQuantity;
    private EditText editTextMinQuantity;
    private EditText editTextPrice;
    private View imageViewColor;
    private EditText editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);
        initProduct();

        bindEditTextName();
        bindEditTextDescription();
        bindEditTextQuantity();
        bindEditTextMinQuantity();
        bindEditTextPrice();
        bindViewImage();
        bindEditTextDate();
    }

    private void initProduct() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.product = (Product) extras.getParcelable(PARAM_PRODUCT);
        }
        this.product = this.product == null ? new Product() : this.product;
    }

    private void bindEditTextName(){
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.setText(product.getName() == null ? "" : product.getName());
    }

    private void bindEditTextDescription(){
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextDescription.setText(product.getDescription() == null ? "" : product.getDescription());
    }

    private void bindEditTextQuantity(){
        editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);
        editTextQuantity.setText(product.getQuantity() == null ? "" : new Integer(product.getQuantity()).toString());
    }

    private void bindEditTextMinQuantity(){
        editTextMinQuantity = (EditText) findViewById(R.id.editTextMinQuantity);
        editTextMinQuantity.setText(product.getMinQuantity() == null ? "" : Integer.toString(product.getMinQuantity()));
    }

    private void bindEditTextPrice(){
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        editTextPrice.setText(product.getPrice() == null ? "" : Double.toString(product.getPrice()));
    }

    private void bindEditTextDate(){
        editTextDate = (EditText) findViewById(R.id.product_form_et_date);
        editTextDate.setText(product.getDate() == null ? "" : convertLongToDate(product.getDate()));
    }

    private void bindViewImage(){
        imageViewColor = (View) findViewById(R.id.imageViewColor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_save:
                onMenuSaveClick();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onMenuSaveClick() {
        String requiredMessage = getString(R.string.msg_required);
        String alertMessage = getString(R.string.alert_message_quantities);
        if (!FormHelper.checkRequireFields(requiredMessage, editTextName, editTextDescription, editTextQuantity, editTextMinQuantity, editTextPrice, editTextDate)) {
            if(!FormHelper.checkMinimumQuantity(alertMessage, editTextMinQuantity, editTextQuantity)){
                bindProduct();
                new SaveProduct().execute(product); //save on database
                //TODO tirar coment�rio web
                //save on cloud
//                new SaveProductOnWebTask(){
//
//                    private ProgressDialog progressDialog;
//
//                    @Override
//                    protected void onPreExecute() {
//                        super.onPreExecute();
//                        progressDialog = new ProgressDialog(ProductFormActivity.this);
//                        progressDialog.setMessage(getString(R.string.msg_saving_product));
//                        progressDialog.show();
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void aVoid) {
//                        super.onPostExecute(aVoid);
//                        progressDialog.dismiss();
//                        Toast.makeText(ProductFormActivity.this, getString(R.string.msg_successfully_saved), Toast.LENGTH_LONG).show();
//                        ProductFormActivity.this.finish();
//                    }
//
//                }.execute(product);
            }
        }
    }

    private void bindProduct() {
        product.setName(editTextName.getText().toString());
        product.setDescription(editTextDescription.getText().toString());
        product.setQuantity(Integer.parseInt(editTextQuantity.getText().toString()));
        product.setMinQuantity(Integer.parseInt(editTextMinQuantity.getText().toString()));
        product.setPrice(Double.parseDouble(editTextPrice.getText().toString()));
        product.setImage(imageViewColor.getBackground().toString());
        product.setDate(Long.parseLong("11111111111111"));//TODO Tirar MOCK
    }

    private class SaveProduct extends AsyncTask<Product, Void, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ProductFormActivity.this);
            progressDialog.setMessage(getString(R.string.msg_saving_product));
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Product... params) {
            ProductBusinessService.save(product);
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            progressDialog.dismiss();
            Toast.makeText(ProductFormActivity.this, getString(R.string.msg_successfully_saved), Toast.LENGTH_LONG).show();
            ProductFormActivity.this.finish();
        }
    }

    private String convertLongToDate(Long dateFromWeb){
        String longDate = dateFromWeb.toString();
        long millisecond = Long.parseLong(longDate);
        return DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
    }


}
