package com.example.myapplication.adapters;

import android.annotation.SuppressLint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.activites.AddressActivity;
import com.example.myapplication.models.AddressModel;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    List<AddressModel> addressModelList;
    Context context;
    SelectedAddress selectedAddress;

    private RadioButton selectedRadioBtn;

    public AddressAdapter(Context context, List<AddressModel> addressModelList, SelectedAddress selectedAddress) {
        this.context = context;
        this.addressModelList = addressModelList;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.address.setText(addressModelList.get(position).getUserAddress());

        // Handle radio button clicks
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Unselect all addresses
                for (AddressModel address : addressModelList) {
                    address.setSelected(false);
                }
                // Set the clicked address as selected
                addressModelList.get(position).setSelected(true);

                // Update the previously selected radio button (if any)
                if (selectedRadioBtn != null) {
                    selectedRadioBtn.setChecked(false);
                }

                // Set the clicked radio button as selected
                selectedRadioBtn = (RadioButton) view;
                selectedRadioBtn.setChecked(true);

                // Pass the selected address back to the activity
                selectedAddress.setAddress(addressModelList.get(position).getUserAddress());
            }
        });

        // Set the radio button state based on selection
        holder.radioButton.setChecked(addressModelList.get(position).isSelected());
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView address;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);
        }
    }

    // Interface for selected address
    public interface SelectedAddress {
        void setAddress(String address);
    }
}
