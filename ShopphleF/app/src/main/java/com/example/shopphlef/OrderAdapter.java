package com.example.shopphlef;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<OrderItem> orderItems;

    public OrderAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderItem item = orderItems.get(position);
        holder.orderNumber.setText(item.getOrderNumber());
        holder.orderDate.setText(item.getOrderDate());
        holder.productBrand.setText(item.getProductBrand());
        holder.productDescription.setText(item.getProductDescription());
        holder.orderPrice.setText(item.getOrderPrice());
        holder.orderImage.setImageResource(item.getOrderImage());
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView orderNumber, orderDate, productBrand, productDescription, orderPrice;
        ImageView orderImage;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.aot);
            orderDate = itemView.findViewById(R.id.order_date);
            productBrand = itemView.findViewById(R.id.product_brand);
            productDescription = itemView.findViewById(R.id.product_description);
            orderPrice = itemView.findViewById(R.id.order_price);
            orderImage = itemView.findViewById(R.id.order_image);
        }
    }
}
