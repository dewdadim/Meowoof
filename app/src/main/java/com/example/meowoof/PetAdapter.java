package com.example.meowoof;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetHolder> {

    private Context context;
    private ArrayList<Pet> pets;

    public PetAdapter(Context context, ArrayList<Pet> pets) {
        this.context = context;
        this.pets = pets;
    }

    @NonNull
    @Override
    public PetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pet_layout_item, parent, false);

        return new PetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetAdapter.PetHolder holder, int position) {
        Pet pet = pets.get(position);
        holder.setDetails(pet);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    class PetHolder extends RecyclerView.ViewHolder{
        private TextView txtName, txtCategory, txtGender,txtID, deleteButton;

        public PetHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtStepCount);
            txtGender = itemView.findViewById(R.id.txtDate);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtID = itemView.findViewById(R.id.txtID);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                    dialog.setTitle("Delete Pet");
                    dialog.setMessage("Are you sure want to delete "+txtName.getText().toString()+"?");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseHelper db = new DatabaseHelper(context);

                            db.deletePet(Integer.parseInt(txtID.getText().toString()));
                            ((MyPetsActivity)context).refreshActivtiy();
                        }
                    });
                    dialog.setNegativeButton("No", null);

                    dialog.show();
                }
            });
        }

        void setDetails(Pet pet){
            txtID.setText(String.format("%d", pet.getId()));
            txtName.setText(pet.getName());
            txtGender.setText(pet.getGender());
            txtCategory.setText(pet.getCategory());
        }
    }
}
