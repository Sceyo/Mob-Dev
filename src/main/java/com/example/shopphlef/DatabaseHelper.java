package com.example.shopphlef;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "shopphlef.db";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CART = "cart";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_RES_ID = "image_res_id";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_CART_ITEM_ID = "cart_item_id";
    private static final String COLUMN_PRODUCT_ID = "product_id";

    private SQLiteDatabase database;

    private static final String TABLE_CREATE_PRODUCTS =
            "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_IMAGE_RES_ID + " INTEGER, " +
                    COLUMN_QUANTITY + " INTEGER DEFAULT 1);";

    private static final String TABLE_CREATE_CART =
            "CREATE TABLE " + TABLE_CART + " (" +
                    COLUMN_CART_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRODUCT_ID + " INTEGER, " +
                    COLUMN_QUANTITY + " INTEGER DEFAULT 1, " +
                    "FOREIGN KEY(" + COLUMN_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = this.getWritableDatabase(); // Open the database
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_PRODUCTS);
        db.execSQL(TABLE_CREATE_CART);
    }

    public boolean deleteProduct(int id) {
        try {

            int rowsDeleted = database.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            return rowsDeleted > 0;
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error deleting product", e);
            return false; // Return false if there was an error
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            // Add quantity column to existing products table if it does not exist
            db.execSQL("ALTER TABLE " + TABLE_PRODUCTS + " ADD COLUMN " + COLUMN_QUANTITY + " INTEGER DEFAULT 1;");
        }
        if (oldVersion < 3) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);  // Drop old cart table
            db.execSQL(TABLE_CREATE_CART);  // Create new cart table
        }
    }

    @Override
    public synchronized void close() {
        super.close();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public void addProduct(Product product) {
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, product.getBrand());
            values.put(COLUMN_DESCRIPTION, product.getDescription());
            values.put(COLUMN_PRICE, product.getPrice());
            values.put(COLUMN_IMAGE_RES_ID, product.getImageResId());
            values.put(COLUMN_QUANTITY, 1); // Set default quantity to 1 when adding product

            long result = database.insert(TABLE_PRODUCTS, null, values);
            if (result == -1) {
                Log.e("DatabaseHelper", "Error adding product");
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error adding product", e);
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        try (Cursor cursor = database.query(TABLE_PRODUCTS, null, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                    double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                    int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID));
                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)); // Fetch the quantity

                    Product product = new Product(id, name, description, price, imageResId, quantity); // Include quantity in the product constructor
                    productList.add(product);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error retrieving products", e);
        }

        return productList;
    }

    public void clearProductsTableContents() {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database
        try {
            db.delete(TABLE_PRODUCTS, null, null); // Deletes all rows from the products table
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error clearing products table contents", e);
        }
    }

    public void addProductToCart(int productId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, productId);
        values.put(COLUMN_QUANTITY, 1);  // Add initial quantity as 1 or as per your logic

        long result = database.insert(TABLE_CART, null, values);
        if (result == -1) {
            Log.e("DatabaseHelper", "Error adding product to cart");
        } else {
            Log.d("DatabaseHelper", "Product added to cart with ID: " + productId);
        }
    }
    public List<Product> getAllCartItems() {
        List<Product> cartItems = new ArrayList<>();

        // SQL query to join cart and products tables
        String query = "SELECT products.*, cart." + COLUMN_QUANTITY + " FROM " + TABLE_CART + " AS cart " +
                "INNER JOIN " + TABLE_PRODUCTS + " AS products ON cart." + COLUMN_PRODUCT_ID + " = products." + COLUMN_ID;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String brand = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID));

                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
                if (quantity == 0) {
                    quantity = 1; // Set default quantity to 1 if not found
                }

                Product product = new Product(id, brand, description, price, imageResId, quantity);
                cartItems.add(product);
            }
            cursor.close();
        }

        return cartItems;
    }

    public void updateCartQuantity(int productId, int newQuantity) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUANTITY, newQuantity);

        int rowsUpdated = database.update(TABLE_CART, values, COLUMN_PRODUCT_ID + " = ?", new String[]{String.valueOf(productId)});

        if (rowsUpdated > 0) {
            Log.d("DatabaseHelper", "Quantity updated for Product ID: " + productId + " to " + newQuantity);
        } else {
            Log.w("DatabaseHelper", "No product found with ID: " + productId + " to update quantity");
        }
    }

    public void clearCartTableContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_CART, null, null);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error clearing products table contents", e);
        }
    }

}