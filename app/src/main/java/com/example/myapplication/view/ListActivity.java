package com.example.myapplication.view;

import static com.example.myapplication.view.AddOrUpdateActivity.TYPE_ADD_EMPLOYEE;
import static com.example.myapplication.view.AddOrUpdateActivity.TYPE_ADD_POSITION;
import static com.example.myapplication.view.AddOrUpdateActivity.TYPE_SCREEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.myapplication.adapters.EmployeePositionAdapter;
import com.example.myapplication.adapters.PositionSpinnerAdapter;
import com.example.myapplication.databinding.ActivityListBinding;
import com.example.myapplication.db.DatabaseHelper;
import com.example.myapplication.models.Employee;
import com.example.myapplication.models.Position;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ActivityListBinding binding;
    private boolean isListEmployee = false;
    private boolean isHighSalary = false;
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
        isHighSalary = getIntent().getBooleanExtra(MainActivity.IS_HIGH_SALARY, false);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        if (isListEmployee) {
            if (isHighSalary) {
                binding.tvTitle.setText("Danh sách nhân viên lương cao");
                listEmployee = db.filterEmployeesAboveSalary();
                binding.btnAdd.setVisibility(View.GONE);
                binding.spnPosition.setVisibility(View.GONE);
            } else {
                binding.tvTitle.setText("Danh sách nhân viên");
                listEmployee = db.getAllEmployees();
                binding.btnAdd.setVisibility(View.VISIBLE);
                binding.spnPosition.setVisibility(View.VISIBLE);
                setupPositionSpinner();
            }
            binding.rcvEmployee.setVisibility(View.VISIBLE);
            binding.rcvPosition.setVisibility(View.GONE);
            adapter = new EmployeePositionAdapter(this);
            adapter.setListEmployee(listEmployee);
            binding.rcvEmployee.setLayoutManager(new LinearLayoutManager(this));
            binding.rcvEmployee.setAdapter(adapter);
        } else {
            binding.tvTitle.setText("Danh sách vị trí");
            listPosition = db.getAllPosition();
            binding.rcvEmployee.setVisibility(View.GONE);
            binding.rcvPosition.setVisibility(View.VISIBLE);
            binding.spnPosition.setVisibility(View.GONE);
            adapter = new EmployeePositionAdapter(this);
            adapter.setListPosition(listPosition);
            binding.rcvPosition.setLayoutManager(new LinearLayoutManager(this));
            binding.rcvPosition.setAdapter(adapter);
        }
        binding.btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this, AddOrUpdateActivity.class);
            intent.putExtra(TYPE_SCREEN, isListEmployee ? TYPE_ADD_EMPLOYEE : TYPE_ADD_POSITION);
            startActivity(intent);
        });
        binding.btnBack.setOnClickListener(view -> onBackPressed());
    }

    private void setupPositionSpinner() {
         ArrayList<Position> listPosition = db.getAllPosition();
         listPosition.add(0, new Position(0, "Tất cả vị trí", 0));
        PositionSpinnerAdapter spinnerAdapter = new PositionSpinnerAdapter(this, listPosition);
        binding.spnPosition.setAdapter(spinnerAdapter);
        binding.spnPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setFilteredPositionId(((Position) adapterView.getItemAtPosition(i)).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        if (isListEmployee) {
            if (isHighSalary) {
                listEmployee = db.filterEmployeesAboveSalary();
            } else {
                listEmployee = db.getAllEmployees();
            }
            adapter.setListEmployee(listEmployee);
        } else {
            listPosition = db.getAllPosition();
            adapter.setListPosition(listPosition);
        }
        adapter.notifyDataSetChanged();
    }
}