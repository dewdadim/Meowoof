package com.example.meowoof;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Meowoof.db";
    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(username TEXT PRIMARY KEY, password TEXT, name TEXT, phoneNumber TEXT)");
        db.execSQL("CREATE TABLE pets(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, category TEXT, gender TEXT, owner TEXT, FOREIGN KEY(owner) REFERENCES users(username))");
        db.execSQL("CREATE TABLE walkHistory(id INTEGER PRIMARY KEY AUTOINCREMENT, pet INTEGER, owner TEXT, stepCount INTEGER, save_at DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(owner) REFERENCES users(username), FOREIGN KEY(pet) REFERENCES pets(name))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS pets");
        db.execSQL("DROP TABLE IF EXISTS walkHistory");
        onCreate(db);
    }

    void addUser(String username, String password, String name, String phoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("username", username);
        cv.put("password", password);
        cv.put("name", name);
        cv.put("phoneNumber", phoneNumber);

        long result = db.insert("users", null, cv);
        if(result == -1){
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show();
        }
    }

    void updateUser(String username, String name, String phoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("phoneNumber", phoneNumber);

        long result = db.update("users", cv, "username = ?", new String[]{username});
        if(result == -1){
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getUserByUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        return data;
    }

    boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});

        if(cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }

    boolean checkUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});

        if(cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }

    void addPet(String name, String category, String gender, String owner){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("category", category);
        cv.put("gender", gender);
        cv.put("owner", owner);

        long result = db.insert("pets", null, cv);
        if(result == -1){
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show();
        }
    }

    boolean checkPet(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pets WHERE name = ?", new String[]{name});

        if(cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }

    Cursor getPetByName(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM pets WHERE name = ?", new String[]{name});
        return data;
    }

    Cursor getAllPetsByOwner(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM pets WHERE owner = ?", new String[]{username});
        return data;
    }

    void deletePet(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("pets", "id=?", new String[]{String.valueOf(id)});
    }

    void addWalkHistory(String pet, String owner, int stepCount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("pet", pet);
        cv.put("owner", owner);
        cv.put("stepCount", stepCount);

        long result = db.insert("walkHistory", null, cv);
        if(result == -1){
            Toast.makeText(context,"Failed to save walk history", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getAllWalkHistoryByOwner(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM walkHistory WHERE owner = ?", new String[]{username});
        return data;
    }
}
