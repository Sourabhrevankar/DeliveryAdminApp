package com.example.deliveryadminapp;

import static com.example.deliveryadminapp.userlist.displayToast;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> list;


    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = list.get(position);
        holder.userName.setText(user.getName());
        holder.userPhone.setText(user.getPhoneno());
        holder.userOrgName.setText(user.getOrgname());
        holder.userOrgAdd.setText(user.getAddress());
        holder.userQuantity.setText(user.getQuantity());
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
                DatabaseReference dReference = fDatabase.getReference("users").child(user.userID);
                dReference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if(ref == null){
                            Toast.makeText(context,"delete:"+error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else{
                            StorageReference sRef = FirebaseStorage.getInstance().getReference("users/").child(user.getUserID());
                            sRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context,"Deleting from database",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });

                Intent intent = new Intent(context,AfterDelivered.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView userName,userOrgName,userOrgAdd,userPhone,userQuantity;
        Button buttonDelete;



        public MyViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);
            userName = itemView.findViewById(R.id.NameText);
            userOrgName = itemView.findViewById(R.id.OrgNameText);
            userOrgAdd = itemView.findViewById(R.id.AddressText);
            userPhone = itemView.findViewById(R.id.PhoneText);
            userQuantity = itemView.findViewById(R.id.QuantityText);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);


        }





        @Override
        public void onClick(View view) {

        }
    }

}
