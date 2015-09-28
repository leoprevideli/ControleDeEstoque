package br.com.cast.turmaformacao.controledeestoque.model.persistence.product;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.controledeestoque.model.entities.Product;

public class ProductContract {

    public static final String TABLE = "product";
    public static final String ID = "id";
    public static final String IDWEB = "idWeb";
    public static final String IMAGE = "image";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String QUANTITY = "quantity";
    public static final String MIN_QUANTITY = "min_quantity";
    public static final String PRICE = "price";
    public static final String DATE = "date";
    public static final String[] COLUMNS = { ID, IDWEB, IMAGE, NAME, DESCRIPTION, QUANTITY, MIN_QUANTITY, PRICE, DATE };

    public static String getCreateTableScript(){
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(IDWEB + " INTEGER UNIQUE, ");
        sql.append(IMAGE + " TEXT NOT NULL, ");
        sql.append(NAME + " TEXT NOT NULL, ");
        sql.append(DESCRIPTION + " TEXT NOT NULL, ");
        sql.append(QUANTITY + " INTEGER NOT NULL, ");
        sql.append(MIN_QUANTITY + " INTEGER NOT NULL, ");
        sql.append(PRICE + " REAL NOT NULL,");
        sql.append(DATE + " INTEGER NOT NULL ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Product product) {
        ContentValues values = new ContentValues();
        values.put(ProductContract.ID, product.getId());
        values.put(ProductContract.IDWEB, product.getIdWeb());
        values.put(ProductContract.IMAGE, product.getImage());
        values.put(ProductContract.NAME, product.getName());
        values.put(ProductContract.DESCRIPTION, product.getDescription());
        values.put(ProductContract.QUANTITY, product.getQuantity());
        values.put(ProductContract.MIN_QUANTITY, product.getMinQuantity());
        values.put(ProductContract.PRICE, product.getPrice());
        values.put(ProductContract.DATE, product.getDate());
        return values;
    }

    public static Product getProduct(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Product product = new Product();
            product.setId(cursor.getLong(cursor.getColumnIndex(ProductContract.ID)));
            product.setIdWeb(cursor.getLong(cursor.getColumnIndex(ProductContract.IDWEB)));
            product.setImage(cursor.getString(cursor.getColumnIndex(ProductContract.IMAGE)));
            product.setName(cursor.getString(cursor.getColumnIndex(ProductContract.NAME)));
            product.setDescription(cursor.getString(cursor.getColumnIndex(ProductContract.DESCRIPTION)));
            product.setQuantity(cursor.getInt(cursor.getColumnIndex(ProductContract.QUANTITY)));
            product.setMinQuantity(cursor.getInt(cursor.getColumnIndex(ProductContract.MIN_QUANTITY)));
            product.setPrice(cursor.getDouble(cursor.getColumnIndex(ProductContract.PRICE)));
            product.setDate(cursor.getLong(cursor.getColumnIndex(ProductContract.DATE)));
            return product;
        }
        return null;
    }

    public static List<Product> getProducts(Cursor cursor) {
        List<Product> products = new ArrayList<>();
        while (cursor.moveToNext()) {
            products.add(getProduct(cursor));
        }
        return products;
    }
}
