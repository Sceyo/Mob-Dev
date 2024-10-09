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
                    COLUMN_ID + " INTEGER, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_IMAGE_RES_ID + " INTEGER, " +
                    COLUMN_QUANTITY + " INTEGER);";

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
//    public List<CartItem> getAllCartItems() {
//        List<CartItem> cartItems = new ArrayList<>();
//        try (Cursor cursor = database.query(TABLE_CART, null, null, null, null, null, null)) {
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
//                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
//                    String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
//                    double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
//                    int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID));
//                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
//
//                    CartItem cartItem = new CartItem(id, name, description, price, imageResId, quantity);
//                    cartItems.add(cartItem);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "Error retrieving cart items", e);
//        }
//        return cartItems;
//    }


//    public boolean addCartItem(CartItem cartItem) {
//        try {
//            ContentValues values = new ContentValues();
//            values.put(COLUMN_ID, cartItem.getId());
//            values.put(COLUMN_NAME, cartItem.getBrand());
//            values.put(COLUMN_DESCRIPTION, cartItem.getDescription());
//            values.put(COLUMN_PRICE, cartItem.getPrice());
//            values.put(COLUMN_IMAGE_RES_ID, cartItem.getImageResId());
//            values.put(COLUMN_QUANTITY, cartItem.getQuantity());
//
//            // Check if the item already exists in the cart
//            if (getCartItemById(cartItem.getId()) != null) {
//                return updateCartItem(cartItem); // return update status
//            } else {
//                long result = database.insert(TABLE_CART, null, values);
//                return result != -1; // return true if insert was successful
//            }
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "Error adding cart item", e);
//            return false;
//        }
//    }
//
//    public boolean updateCartItem(CartItem cartItem) {
//        try {
//            ContentValues values = new ContentValues();
//            values.put(COLUMN_QUANTITY, cartItem.getQuantity());
//
//            int rowsAffected = database.update(TABLE_CART, values, COLUMN_ID + " = ?", new String[]{String.valueOf(cartItem.getId())});
//            return rowsAffected > 0; // return true if at least one row was updated
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "Error updating cart item", e);
//            return false;
//        }
//    }
//
//    public List<CartItem> getAllCartItems() {
//        List<CartItem> cartItems = new ArrayList<>();
//        try (Cursor cursor = database.query(TABLE_CART, null, null, null, null, null, null)) {
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
//                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
//                    String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
//                    double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
//                    int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID));
//                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
//
//                    CartItem cartItem = new CartItem(id, name, description, price, imageResId, quantity);
//                    cartItems.add(cartItem);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "Error retrieving cart items", e);
//        }
//
//        return cartItems;
//    }
//
//    public CartItem getCartItemById(int id) {
//        CartItem cartItem = null;
//        try (Cursor cursor = database.query(TABLE_CART, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null)) {
//            if (cursor != null && cursor.moveToFirst()) {
//                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
//                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
//                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
//                int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID));
//                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
//
//                cartItem = new CartItem(id, name, description, price, imageResId, quantity);
//            }
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "Error retrieving cart item by id", e);
//        }
//        return cartItem;
//    }
//
//    public void clearCart() {
//        try {
//            database.delete(TABLE_CART, null, null); // Deletes all rows from the cart table
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "Error clearing cart", e);
//        }
//    }
//
//    public boolean removeCartItem(int id) {
//        try {
//            int rowsDeleted = database.delete(TABLE_CART, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
//            return rowsDeleted > 0; // Return true if at least one row was deleted
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "Error removing cart item", e);
//            return false;
//        }
//    }
}
