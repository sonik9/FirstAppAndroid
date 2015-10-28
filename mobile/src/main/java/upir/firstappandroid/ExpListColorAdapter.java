package upir.firstappandroid;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorFilter;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vital on 28/10/2015.
 */
public class ExpListColorAdapter extends BaseExpandableListAdapter {


    private ArrayList<ArrayList<Integer>> mGroups;
    private Context mContext;
    SharedPreferences pref;

    public ExpListColorAdapter (Context context,ArrayList<ArrayList<Integer>> groups){
        mContext = context;
        mGroups = groups;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
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
        return true;
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        textGroup.setText("Colors ");
        return convertView;

    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_settings_color, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        textChild.setText(mGroups.get(groupPosition).get(childPosition).toString());

        final ImageView color = (ImageView)convertView.findViewById(R.id.colorChild);
        color.setBackgroundColor(mGroups.get(groupPosition).get(childPosition));
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref = mContext.getSharedPreferences(null,0);
                SharedPreferences.Editor ed = pref.edit();
                ed.putString("show_color",mGroups.get(groupPosition).get(childPosition).toString());
                ed.commit();
                Toast.makeText(mContext, "Color saved", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
