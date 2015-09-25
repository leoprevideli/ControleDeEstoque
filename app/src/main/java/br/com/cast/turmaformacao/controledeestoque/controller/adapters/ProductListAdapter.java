package br.com.cast.turmaformacao.controledeestoque.controller.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.controledeestoque.R;
import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;

public class ProductListAdapter extends BaseAdapter {

    private Activity context;
    private List<Product> products;

    public ProductListAdapter(Activity context) {
        this.context = context;
        this.products = new ArrayList<>();
    }

    public void setDataValues(List<Product> values) {
        products.clear();
        products.addAll(values);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Product product = getItem(i);
        View productListItemView = context.getLayoutInflater().inflate(R.layout.product_list_item, null);

        TextView productName = (TextView) productListItemView.findViewById(R.id.productName);
        productName.setText(" " + product.getName());

        TextView productPrice = (TextView) productListItemView.findViewById(R.id.productPrice);
        productPrice.setText(" " + product.getPrice().toString());

        TextView productQuantity = (TextView) productListItemView.findViewById(R.id.productQuantity);
        productQuantity.setText(" " + product.getQuantity().toString());

        return productListItemView;
    }
}
