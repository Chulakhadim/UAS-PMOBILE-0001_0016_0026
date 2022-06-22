package com.example.wishnote;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context context;
    private ArrayList<Model> arrayList;


    public Adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Model model = arrayList.get(position);

        String name = model.getName();
        String price = model.getPrice();
        String descr = model.getDescr();

        holder.name.setText(name);
        holder.price.setText("Rp. " + price);
        holder.descr.setText(descr);

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                Button delete = dialog.findViewById(R.id.btndeleted);
                Button cancel = dialog.findViewById(R.id.btncanel);
                delete.setOnClickListener(new View.OnClickListener() {
                    DBHelper db = new DBHelper(context);
                    @Override
                    public void onClick(View view) {
                        // continue with delete
                        DBHelper db = new DBHelper(context);
                        db.deletelistdata(name);
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView name, price, descr;
        ImageView btndelete;

        public Holder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nama_barang);
            price = itemView.findViewById(R.id.harga);
            descr = itemView.findViewById(R.id.deskripsi);

            btndelete = itemView.findViewById(R.id.btndelete);
        }
    }
}
