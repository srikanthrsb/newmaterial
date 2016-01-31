package com.sptuts.newmaterial.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;

/**
 * Created by SHRI on 1/11/2016.
 */
public class ContextMenuRecyclerView extends RecyclerView {
    public ContextMenuRecyclerView(Context context) {
        super(context);
    }
    private RecyclerViewContextMenuInfo mContextMenuInfo;

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        final int longPressPosition = getChildPosition(originalView);
        if (longPressPosition >= 0) {
            final long longPressId = getAdapter().getItemId(longPressPosition);
            mContextMenuInfo = new RecyclerViewContextMenuInfo(longPressPosition,longPressId);
            return super.showContextMenuForChild(originalView);
        }
        return false;
    }

    public static class RecyclerViewContextMenuInfo implements ContextMenu.ContextMenuInfo {

        public RecyclerViewContextMenuInfo(int position, long id) {
            this.position = position;
            this.id = id;
        }

        final public int position;
        final public long id;
    }
}
