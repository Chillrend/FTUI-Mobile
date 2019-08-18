package org.ftui.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.AddComplaintCamera;
import org.ftui.mobile.R;
import org.ftui.mobile.model.Photos;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> implements View.OnClickListener {
    private List<Photos> items;
    private Context ctx;

    public PhotosAdapter(List<Photos> items, Context ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.captured_photos_recyclerview_item, parent, false);
        int height = parent.getMeasuredHeight();
        int width = parent.getMeasuredWidth() / 4;
        view.setLayoutParams(new RecyclerView.LayoutParams(width, height));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.imagePrevivew.setImageBitmap(items.get(position).getPhoto());
        holder.imagePrevivew.setScaleType(ImageView.ScaleType.FIT_XY);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(holder.getAdapterPosition());
                Toasty.info(ctx, "Deleted Item at Pos : " + holder.getAdapterPosition(), Toasty.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount(){
        return (items != null) ? items.size() : 0;
    }

    public void removeAt(int position){
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());

        AddComplaintCamera.setPhotosArray(items);
    }

    @Override
    public void onClick(View v){
        Toasty.info(ctx, "Clicked Delete Button!", Toasty.LENGTH_LONG).show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout parent;
        public ImageView imagePrevivew;
        public ImageButton deleteButton;


        public ViewHolder(View v) {
            super(v);

            parent = v.findViewById(R.id.picture_container);
            imagePrevivew = v.findViewById(R.id.taken_picture);
            deleteButton = v.findViewById(R.id.taken_picture_del_button);
        }
    }
}
