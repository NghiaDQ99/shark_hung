package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String IS_LIST_EMPLOYEE_FLAG = "IS_LIST_EMPLOYEE_FLAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent(MainActivity.this, ListActivity.class);

        fakeDefaultData();

        binding.btnNV.setOnClickListener(view -> {
            intent.putExtra(IS_LIST_EMPLOYEE_FLAG, true);
            startActivity(intent);
        });
        binding.btnVT.setOnClickListener(view -> {
            intent.putExtra(IS_LIST_EMPLOYEE_FLAG, false);
            startActivity(intent);
        });
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
            db.addPosition("Kế toán", 10);
            db.addPosition("Nhân viên", 5);
        }
    }
}