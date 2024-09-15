package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String IS_LIST_EMPLOYEE_FLAG = "IS_LIST_EMPLOYEE_FLAG";
    public static final String IS_HIGH_SALARY = "IS_HIGH_SALARY";

    static {
        System.loadLibrary("native-lib");
    }

    private native String getAESKey();
    private native String getAESIv();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent(MainActivity.this, ListActivity.class);

        fakeDefaultData();

        binding.btnNV.setOnClickListener(view -> {
            intent.putExtra(IS_LIST_EMPLOYEE_FLAG, true);
            intent.putExtra(IS_HIGH_SALARY, false);
            startActivity(intent);
        });
        binding.btnVT.setOnClickListener(view -> {
            intent.putExtra(IS_LIST_EMPLOYEE_FLAG, false);
            startActivity(intent);
        });
        binding.btnNYHighSalary.setOnClickListener(view -> {
            intent.putExtra(IS_LIST_EMPLOYEE_FLAG, true);
            intent.putExtra(IS_HIGH_SALARY, true);
            startActivity(intent);
        });
        Log.d("MainActivity", "Chuỗi hằng số từ CMake: " + getAESKey());
        Log.d("MainActivity", "Chuỗi hằng số từ CMake: " + getAESIv());
    }

    private void fakeDefaultData() {
        DatabaseHelper db = new DatabaseHelper(this);

        if (db.getAllEmployees().size() == 0) {
            db.addEmployee("Nguyễn Mạnh Hưng", "01/01/1999", "Hưng Yên", 1);
            db.addEmployee("Nguyễn Thị A", "01/01/1999", "Hưng Yên", 2);
            db.addEmployee("Nguyễn Văn B", "01/01/1999", "Hưng Yên", 3);
            db.addEmployee("Nguyễn Văn C", "01/01/1999", "Hưng Yên", 3);
        }

        if (db.getAllPosition().size() == 0) {
            db.addPosition("Giám đốc", 50);
            db.addPosition("Kế toán", 11);
            db.addPosition("Nhân viên", 5);
        }
    }
}