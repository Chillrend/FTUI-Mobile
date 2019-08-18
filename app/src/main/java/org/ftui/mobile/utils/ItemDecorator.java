package org.ftui.mobile.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import org.ftui.mobile.R;

public class ItemDecorator extends RecyclerView.ItemDecoration {
    private final int decoration_width;
    private Context ctx;

    public ItemDecorator(Context ctx) {
        this.decoration_width = ctx.getResources().getDimensionPixelSize(R.dimen.small_margin);
        this.ctx = ctx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        super.getItemOffsets(outRect, view, parent, state);

        if(parent != null && view != null){
            int itemPosition = parent.getChildAdapterPosition(view);
            int totalCount = parent.getAdapter().getItemCount();
            if(itemPosition >= 0 && itemPosition < totalCount - 1){
                outRect.right = decoration_width;
            }
        }
    }
}
