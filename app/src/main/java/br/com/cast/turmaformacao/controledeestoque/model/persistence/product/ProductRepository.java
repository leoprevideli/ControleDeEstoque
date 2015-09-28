package br.com.cast.turmaformacao.controledeestoque.model.persistence.product;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;
import br.com.cast.turmaformacao.controledeestoque.model.persistence.DatabaseHelper;

public class ProductRepository {

    public ProductRepository() {
        super();
    }

    public static List<Product> getAll(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(ProductContract.TABLE, ProductContract.COLUMNS, null, null, null, null, null);
        List<Product> products = ProductContract.getProducts(cursor);

        db.close();
        databaseHelper.close();
        return products;
    }

    public static void save(Product product) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = ProductContract.getContentValues(product);
        if (product.getId() == null) {
            db.insert(ProductContract.TABLE, null, values);
        } else {
            String where = ProductContract.ID + " = ? ";
            String[] params = {product.getId().toString()};
            db.update(ProductContract.TABLE, values, where, params);
        }

        db.close();
        databaseHelper.close();
    }

    public static void delete(Product product){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String where = ProductContract.ID + " = ? ";
        String[] params = {String.valueOf(product.getId())};
        db.delete(ProductContract.TABLE, where, params);

        db.close();
        databaseHelper.close();
    }

    public static Product getProductByWebId(long webId){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String where = ProductContract.IDWEB + " = ? ";
        String[] params = {String.valueOf(webId)};

        Cursor cursor = db.query(ProductContract.TABLE, ProductContract.COLUMNS, where, params, null, null, null);
        Product returnProduct = ProductContract.getProduct(cursor);

        db.close();
        databaseHelper.close();
        return returnProduct;
    }
}
