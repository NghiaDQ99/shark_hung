package com.example.myapplication.adapters;

import static com.example.myapplication.view.AddOrUpdateActivity.ID;
import static com.example.myapplication.view.AddOrUpdateActivity.TYPE_SCREEN;
import static com.example.myapplication.view.AddOrUpdateActivity.TYPE_UPDATE_EMPLOYEE;
import static com.example.myapplication.view.AddOrUpdateActivity.TYPE_UPDATE_POSITION;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemEmployeePositionBinding;
import com.example.myapplication.db.DatabaseHelper;
import com.example.myapplication.models.Employee;
import com.example.myapplication.models.Position;
import com.example.myapplication.view.AddOrUpdateActivity;
import com.example.myapplication.view.ListActivity;

import java.util.ArrayList;

public class EmployeePositionAdapter extends RecyclerView.Adapter<EmployeePositionAdapter.EmployeePostionViewHolder>{
    private ArrayList<Employee> listEmployee;
    private ArrayList<Position> listPosition;
    private final ListActivity listActivity;
    private final DatabaseHelper db;
    private int filteredPositionId = 0;

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

    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredPositionId(int id) {
        this.filteredPositionId = id;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmployeePostionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeePostionViewHolder(ItemEmployeePositionBinding.inflate(LayoutInflater.from(listActivity), parent, false));
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull EmployeePostionViewHolder holder, int position) {
        if (listEmployee != null) {
            Employee item = listEmployee.get(position);

            if (filteredPositionId == 0 || item.getPosition_id() == filteredPositionId) {
                holder.itemView.setVisibility(View.VISIBLE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }

            holder.binding.tvPosition.setVisibility(View.VISIBLE);
            holder.binding.tvDoB.setVisibility(View.VISIBLE);
            holder.binding.tvHomeTown.setVisibility(View.VISIBLE);

            holder.binding.tvName.setText("Tên: " + item.getName());
            holder.binding.tvDoB.setText("Ngày sinh: " + item.getDoB());
            holder.binding.tvHomeTown.setText("Quê quán: " + item.getHomeTown());
            holder.binding.tvPosition.setText("Vị trí: " + db.getPositionNameByEmployee(item.getId()));
            holder.binding.tvSalary.setText("Mức lương: " + db.getSalaryByEmployee(item.getId()) + " triệu vnđ");
            holder.binding.btnDelete.setOnClickListener(view -> {
                db.deleteEmployee(item.getId());
                listEmployee.remove(position);
                notifyDataSetChanged();
            });
            holder.binding.btnEdit.setOnClickListener(view -> {
                Intent intent = new Intent(listActivity, AddOrUpdateActivity.class);
                intent.putExtra(TYPE_SCREEN, TYPE_UPDATE_EMPLOYEE);
                intent.putExtra(ID, item.getId());
                listActivity.startActivity(intent);
            });
        } else {
            Position item = listPosition.get(position);

            holder.binding.tvPosition.setVisibility(View.GONE);
            holder.binding.tvDoB.setVisibility(View.GONE);
            holder.binding.tvHomeTown.setVisibility(View.GONE);

            holder.binding.tvName.setText("Tên: " + item.getName());
            holder.binding.tvSalary.setText("Mức lương: " + item.getSalary() + " triệu vnđ");
            holder.binding.btnDelete.setOnClickListener(view -> {
                db.deletePosition(item.getId());
                listPosition.remove(position);
                notifyDataSetChanged();
            });
            holder.binding.btnEdit.setOnClickListener(view -> {
                Intent intent = new Intent(listActivity, AddOrUpdateActivity.class);
                intent.putExtra(TYPE_SCREEN, TYPE_UPDATE_POSITION);
                intent.putExtra(ID, item.getId());
                listActivity.startActivity(intent);
            });
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
