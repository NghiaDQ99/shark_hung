package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemEmployeePositionBinding;
import com.example.myapplication.db.DatabaseHelper;
import com.example.myapplication.models.Employee;
import com.example.myapplication.models.Position;
import com.example.myapplication.view.ListActivity;

import java.util.ArrayList;

public class EmployeePositionAdapter extends RecyclerView.Adapter<EmployeePositionAdapter.EmployeePostionViewHolder>{
    private ArrayList<Employee> listEmployee;
    private ArrayList<Position> listPosition;
    private final ListActivity listActivity;
    private final DatabaseHelper db;

    public EmployeePositionAdapter(ListActivity listActivity) {
        this.listActivity = listActivity;
        db = new DatabaseHelper(listActivity);
    }

    public void setListEmployee(ArrayList<Employee> listEmployee) {
        this.listEmployee = listEmployee;
    }

    public void setListPosition(ArrayList<Position> listPosition) {
        this.listPosition = listPosition;
    }

    @NonNull
    @Override
    public EmployeePostionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeePostionViewHolder(ItemEmployeePositionBinding.inflate(LayoutInflater.from(listActivity), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EmployeePostionViewHolder holder, int position) {
        if (listEmployee != null) {
            Employee item = listEmployee.get(position);

            holder.binding.tvPosition.setVisibility(View.VISIBLE);
            holder.binding.tvDoB.setVisibility(View.VISIBLE);
            holder.binding.tvHomeTown.setVisibility(View.VISIBLE);

            holder.binding.tvName.setText("Tên: " + item.getName());
            holder.binding.tvDoB.setText("Ngày sinh: " + item.getDoB());
            holder.binding.tvHomeTown.setText("Quê quán: " + item.getHomeTown());
            holder.binding.tvPosition.setText("Vị trí: " + db.getPositionNameByEmployee(item.getPosition_id()));
            holder.binding.tvSalary.setText("Mức lương: " + db.getSalaryByEmployee(item.getPosition_id()) + " triệu vnđ");
        } else {
            Position item = listPosition.get(position);

            holder.binding.tvPosition.setVisibility(View.GONE);
            holder.binding.tvDoB.setVisibility(View.GONE);
            holder.binding.tvHomeTown.setVisibility(View.GONE);

            holder.binding.tvName.setText("Tên: " + item.getName());
            holder.binding.tvSalary.setText("Mức lương: " + item.getSalary() + " triệu vnđ");
        }
    }

    @Override
    public int getItemCount() {
        if (listEmployee != null) {
            return listEmployee.size();
        } else if (listPosition != null) {
            return listPosition.size();
        }
        return 0;
    }

    public static class EmployeePostionViewHolder extends RecyclerView.ViewHolder {
        private final ItemEmployeePositionBinding binding;
        public EmployeePostionViewHolder(@NonNull ItemEmployeePositionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
