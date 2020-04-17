package com.rsypj.ftuimobile.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Nur Hildayanti Utami on 25/02/2019.
 * feel free to contact me on nur.hildayanti.u@gmail.com
 */
public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> lstTitle;
    private Map<String, List<String>> lstItem;

    public CustomExpandableListAdapter(Context context, List<String> lstTitle, Map<String, List<String>> lstItem) {
        this.context = context;
        this.lstTitle = lstTitle;
        this.lstItem = lstItem;
    }

    @Override
    public int getGroupCount() {
        return lstTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return lstItem.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lstTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return lstItem.get(lstTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String)getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_group_header, null);
        }

        TextView txtTitle = convertView.findViewById(R.id.lblListHeader);
        txtTitle.setTypeface(null, Typeface.BOLD);
        txtTitle.setText(title);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String)getChild(groupPosition,childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_group_child, null);
        }

        TextView txtTitle = convertView.findViewById(R.id.lblListItem);
        //txtTitle.setTypeface(null, Typeface.BOLD);
        txtTitle.setText(title);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
