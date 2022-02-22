package com.example.citybikes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NetworkAdapter extends RecyclerView.Adapter<NetworkAdapter.NetworkViewHolder> {
    private ArrayList<Network> networks;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public NetworkAdapter(Context context, ArrayList<Network> ITEMS) {
        networks = ITEMS;
        this.context = context;
    }

    @NonNull
    @Override
    public NetworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_row, parent, false);
        return new NetworkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworkViewHolder holder, int position) {
        Network network = networks.get(position);
        holder.bind((network));
    }

    @Override
    public int getItemCount() {
        return networks.size();
    }

    public class NetworkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName;
        TextView tvCompany;
        TextView tvId;

        public NetworkViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvTitle);
            tvCompany = (TextView) itemView.findViewById(R.id.tvDescription);
            tvId = (TextView) itemView.findViewById(R.id.tvPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(Network network) {
            tvName.setText(network.name);
            tvCompany.setText(network.city);
            tvId.setText(network.country);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("Click", String.valueOf(position));
            Network selectedNetwork = networks.get(position);
            Intent intent = new Intent(view.getContext(), BikeActivity.class);
            intent.putExtra("Bike", selectedNetwork);
            view.getContext().startActivity(intent);
        }
    }
}
