package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.adapters.EmployeePositionAdapter;
import com.example.myapplication.databinding.ActivityListBinding;
import com.example.myapplication.db.DatabaseHelper;
import com.example.myapplication.models.Employee;
import com.example.myapplication.models.Position;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ActivityListBinding binding;
    private boolean isListEmployee = false;
    private ArrayList<Employee> listEmployee;
    private ArrayList<Position> listPosition;
    private EmployeePositionAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DatabaseHelper(this);
        isListEmployee = getIntent().getBooleanExtra(MainActivity.IS_LIST_EMPLOYEE_FLAG, false);
        initView();
    }

    private void initView() {
        if (isListEmployee) {
            listEmployee = db.getAllEmployees();
            binding.rcvEmployee.setVisibility(View.VISIBLE);
            binding.rcvPosition.setVisibility(View.GONE);
            adapter = new EmployeePositionAdapter(this);
            adapter.setListEmployee(listEmployee);
            binding.rcvEmployee.setLayoutManager(new LinearLayoutManager(this));
            binding.rcvEmployee.setAdapter(adapter);
        } else {
            listPosition = db.getAllPosition();
            binding.rcvEmployee.setVisibility(View.GONE);
            binding.rcvPosition.setVisibility(View.VISIBLE);
            adapter = new EmployeePositionAdapter(this);
            adapter.setListPosition(listPosition);
            binding.rcvPosition.setLayoutManager(new LinearLayoutManager(this));
            binding.rcvPosition.setAdapter(adapter);
        }
    }
}