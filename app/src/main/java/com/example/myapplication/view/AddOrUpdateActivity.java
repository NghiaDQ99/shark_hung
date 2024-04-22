package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.PositionSpinnerAdapter;
import com.example.myapplication.databinding.ActivityAddOrUpdateBinding;
import com.example.myapplication.db.DatabaseHelper;
import com.example.myapplication.models.Employee;
import com.example.myapplication.models.Position;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddOrUpdateActivity extends AppCompatActivity {

    private ActivityAddOrUpdateBinding binding;

    public static final int TYPE_ADD_EMPLOYEE = 1;
    public static final int TYPE_UPDATE_EMPLOYEE = 2;
    public static final int TYPE_ADD_POSITION = 3;
    public static final int TYPE_UPDATE_POSITION = 4;
    public static final String TYPE_SCREEN = "TYPE_SCREEN";
    public static final String ID = "ID";
    private int id = 0;
    private int type = 0;
    private Position selectedPosition;
    DatabaseHelper db;
    ArrayList<Position> listPosition;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOrUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getIntExtra(ID, 0);
        type = getIntent().getIntExtra(TYPE_SCREEN, 1);
        db = new DatabaseHelper(this);
        listPosition = db.getAllPosition();
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        binding.btnBack.setOnClickListener(view -> onBackPressed());
        switch (type) {
            case TYPE_ADD_EMPLOYEE:
                binding.tvTitle.setText("Thêm nhân viên");
                binding.tvDoB.setVisibility(View.VISIBLE);
                binding.edtDoB.setVisibility(View.VISIBLE);
                binding.tvHomeTown.setVisibility(View.VISIBLE);
                binding.edtHomeTown.setVisibility(View.VISIBLE);
                binding.tvPosition.setVisibility(View.VISIBLE);
                binding.spnPosition.setVisibility(View.VISIBLE);
                binding.tvSalary.setVisibility(View.GONE);
                binding.edtSalary.setVisibility(View.GONE);
                setupSpinnerAdapter();
                binding.btnFinish.setOnClickListener(view -> {
                    if (!TextUtils.isEmpty(binding.edtName.getText()) &&
                            !TextUtils.isEmpty(binding.edtDoB.getText()) &&
                            !TextUtils.isEmpty(binding.edtHomeTown.getText())) {
                        db.addEmployee(binding.edtName.getText().toString(),
                                binding.edtDoB.getText().toString(),
                                binding.edtHomeTown.getText().toString(),
                                selectedPosition.getId());
                    } else {
                        Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                });
                binding.edtDoB.setOnClickListener(view -> showDatePicker());
                break;
            case TYPE_UPDATE_EMPLOYEE:
                Employee employee = db.getEmployeeById(id);
                binding.tvTitle.setText("Sửa nhân viên");
                binding.edtName.setText(employee.getName());
                binding.edtDoB.setText(employee.getDoB());
                binding.edtHomeTown.setText(employee.getHomeTown());
                binding.tvDoB.setVisibility(View.VISIBLE);
                binding.edtDoB.setVisibility(View.VISIBLE);
                binding.tvHomeTown.setVisibility(View.VISIBLE);
                binding.edtHomeTown.setVisibility(View.VISIBLE);
                binding.tvPosition.setVisibility(View.VISIBLE);
                binding.spnPosition.setVisibility(View.VISIBLE);
                binding.tvSalary.setVisibility(View.GONE);
                binding.edtSalary.setVisibility(View.GONE);
                setupSpinnerAdapter();
                setSpinnerItem(employee.getPosition_id());
                binding.btnFinish.setOnClickListener(view -> {
                    if (!TextUtils.isEmpty(binding.tvName.getText()) &&
                            !TextUtils.isEmpty(binding.tvDoB.getText()) &&
                            !TextUtils.isEmpty(binding.tvHomeTown.getText())) {
                        db.updateEmployee(id,
                                binding.edtName.getText().toString(),
                                binding.edtDoB.getText().toString(),
                                binding.edtHomeTown.getText().toString(),
                                selectedPosition.getId());
                    } else {
                        Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                });
                binding.edtDoB.setOnClickListener(view -> showDatePicker());
                break;
            case TYPE_ADD_POSITION:
                binding.tvTitle.setText("Thêm vị trí");
                binding.tvDoB.setVisibility(View.GONE);
                binding.edtDoB.setVisibility(View.GONE);
                binding.tvHomeTown.setVisibility(View.GONE);
                binding.edtHomeTown.setVisibility(View.GONE);
                binding.tvPosition.setVisibility(View.GONE);
                binding.spnPosition.setVisibility(View.GONE);
                binding.tvSalary.setVisibility(View.VISIBLE);
                binding.edtSalary.setVisibility(View.VISIBLE);
                binding.btnFinish.setOnClickListener(view -> {
                    if (!TextUtils.isEmpty(binding.edtName.getText()) &&
                            !TextUtils.isEmpty(binding.edtSalary.getText())) {
                        db.addPosition(binding.edtName.getText().toString(),
                                Float.parseFloat(binding.edtSalary.getText().toString()));
                    } else {
                        Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case TYPE_UPDATE_POSITION:
                Position position = db.getPositionById(id);
                binding.tvTitle.setText("Sửa vị trí");
                binding.edtName.setText(position.getName());
                binding.edtSalary.setText(String.valueOf(position.getSalary()));
                binding.tvDoB.setVisibility(View.GONE);
                binding.edtDoB.setVisibility(View.GONE);
                binding.tvHomeTown.setVisibility(View.GONE);
                binding.edtHomeTown.setVisibility(View.GONE);
                binding.tvPosition.setVisibility(View.GONE);
                binding.spnPosition.setVisibility(View.GONE);
                binding.tvSalary.setVisibility(View.VISIBLE);
                binding.edtSalary.setVisibility(View.VISIBLE);
                binding.btnFinish.setOnClickListener(view -> {
                    if (!TextUtils.isEmpty(binding.edtName.getText()) &&
                            !TextUtils.isEmpty(binding.edtSalary.getText())) {
                        db.updatePosition(id,
                                binding.edtName.getText().toString(),
                                Float.parseFloat(binding.edtSalary.getText().toString()));
                    } else {
                        Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    private void setupSpinnerAdapter() {
        PositionSpinnerAdapter adapter = new PositionSpinnerAdapter(this, listPosition);
        binding.spnPosition.setAdapter(adapter);
        binding.spnPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPosition = (Position) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSpinnerItem(int positionId) {
        for (int i = 0; i < listPosition.size(); i++) {
            if (listPosition.get(i).getId() == positionId) {
                binding.spnPosition.setSelection(i);
                return;
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void showDatePicker() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        String currentTime = dateFormat.format(date);
        String dateString = !TextUtils.isEmpty(binding.edtDoB.getText().toString()) ? binding.edtDoB.getText().toString() : currentTime;

        calendar = Calendar.getInstance();

        try {
            Date date1 = dateFormat.parse(dateString);
            if (date1 != null) {
                calendar.setTime(date1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddOrUpdateActivity.this, R.style.SpinnerDatePickerDialog,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(Calendar.YEAR, year1);
                    selectedDate.set(Calendar.MONTH, monthOfYear);
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth1);
                    SimpleDateFormat sdfResult = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = sdfResult.format(selectedDate.getTime());
                    binding.edtDoB.setText(formattedDate);
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

}