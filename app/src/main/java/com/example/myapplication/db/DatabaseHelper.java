package com.example.myapplication.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.models.Employee;
import com.example.myapplication.models.Position;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MAD.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EMPLOYEE = "tbl_employee";
    private static final String TABLE_POSITION = "tbl_position";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_HOMETOWN = "hometown";
    private static final String COLUMN_POSITION = "position_id";
    private static final String COLUMN_SALARY = "salary";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_EMPLOYEE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DOB + " TEXT, " +
                COLUMN_HOMETOWN + " TEXT, " +
                COLUMN_POSITION + " INTEGER);";
        String query1 = "CREATE TABLE " + TABLE_POSITION +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SALARY + " FLOAT);";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query1);
    }

    public void addEmployee(String name, String dob, String hometown, int positionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DOB, dob);
        cv.put(COLUMN_HOMETOWN, hometown);
        cv.put(COLUMN_POSITION, positionId);
        long result = db.insert(TABLE_EMPLOYEE, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addPosition(String name, float salary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_SALARY, salary);
        long result = db.insert(TABLE_POSITION, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_EMPLOYEE;
        String query1 = "DROP TABLE IF EXISTS " + TABLE_POSITION;
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query1);
        onCreate(sqLiteDatabase);
    }

    @SuppressLint("Range")
    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EMPLOYEE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int employeeId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String employeeName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int positionId = cursor.getInt(cursor.getColumnIndex(COLUMN_POSITION));
                String dob = cursor.getString(cursor.getColumnIndex(COLUMN_DOB));
                String hometown = cursor.getString(cursor.getColumnIndex(COLUMN_HOMETOWN));
                Employee employee = new Employee(employeeId, employeeName, dob, hometown, positionId);
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return employeeList;
    }

    @SuppressLint("Range")
    public ArrayList<Position> getAllPosition() {
        ArrayList<Position> positionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_POSITION;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int positionId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                float salary = cursor.getFloat(cursor.getColumnIndex(COLUMN_SALARY));
                Position position = new Position(positionId, name, salary);
                positionList.add(position);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return positionList;
    }

    Cursor readAllEmployee() {
        String query = "SELECT * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllPosition() {
        String query = "SELECT * FROM " + TABLE_POSITION;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateEmployee(int id, String name, String dob, String homeTown, int positionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DOB, dob);
        cv.put(COLUMN_HOMETOWN, homeTown);
        cv.put(COLUMN_POSITION, positionId);

        long result = db.update(TABLE_EMPLOYEE, cv, "_id=?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteEmployee(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_EMPLOYEE, "_id=?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void updatePosition(int id, String name, float salary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_SALARY, salary);

        long result = db.update(TABLE_POSITION, cv, "_id=?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deletePosition(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_POSITION, "_id=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    public String getPositionNameByEmployee(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT p." + COLUMN_NAME +
                " FROM " + TABLE_EMPLOYEE + " e INNER JOIN " + TABLE_POSITION + " p ON e." + COLUMN_POSITION + " = p." + COLUMN_ID +
                " WHERE e." + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        String positionName = null;
        if (cursor.moveToFirst()) {
            positionName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        }
        cursor.close();
        return positionName != null ? positionName : "Chưa có";
    }

    @SuppressLint("Range")
    public float getSalaryByEmployee(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT p." + COLUMN_SALARY +
                " FROM " + TABLE_EMPLOYEE + " e INNER JOIN " + TABLE_POSITION + " p ON e." + COLUMN_POSITION + " = p." + COLUMN_ID +
                " WHERE e." + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        float positionName = 0;
        if (cursor.moveToFirst()) {
            positionName = cursor.getFloat(cursor.getColumnIndex(COLUMN_SALARY));
        }
        cursor.close();
        return positionName;
    }
}
