
package com.swtorserversstatus.ui;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.swtorserversstatus.model.Server;
import com.swtorserversstatus.utils.Utils;

import java.util.*;


public class ImageAdapter extends ArrayAdapter {

    private HashSet dataList;
    private Context context;
    private List list;

    @SuppressWarnings("unchecked")
    public ImageAdapter(Context context, int type, HashSet<?> l) {

        super(context, type);
        this.context = context;
        this.setDataList(l);
        this.list = new ArrayList<Server>(getDataList());
        Utils.ServerCompare serverCompare = new Utils.ServerCompare();
        Collections.sort(this.list,serverCompare);

    }

    public void remove(Object T)
    {
        getDataList().remove(T);
    }

    public int getCount() {
        return getDataList().size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public HashSet getDataList() {
        return dataList;
    }
     @SuppressWarnings("unchecked")
    public void addDataList(List dataList) {
        this.getDataList().addAll(dataList);
    }

    public void setDataList(HashSet dataList) {
        this.dataList = dataList;
    }

    public static class ViewHolder extends View {
        public ViewHolder(Context context) {
            super(context);
            tvServerName = new TextView(context);
            tvServerName.setId(11111);
            tvServerStatus = new TextView(context);
            tvServerStatus.setId(22222);
            tvServerLoad = new TextView(context);
            tvServerLoad.setId(33333);
            tvServerType = new TextView(context);
            tvServerType.setId(44444);
            tvServerTimezone = new TextView(context);
            tvServerType.setId(55555);
            tvServerLanguage = new TextView(context);
            tvServerLanguage.setId(66666);
        }

        public TextView tvServerName;
        public TextView tvServerStatus;
        public TextView tvServerLoad;
        public TextView tvServerType;
        public TextView tvServerTimezone;
        public TextView tvServerLanguage;

    }

    public View getView(int position, final View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder=null;

        Server server = (Server) list.get(position);
        if (v == null) {
            v = new RelativeLayout(this.context);
            holder= new ViewHolder(this.context);

            ((RelativeLayout) v).addView(holder.tvServerName);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.BELOW, holder.tvServerName.getId());
            RelativeLayout rl1 = new RelativeLayout(this.context);

            alignTextView(rl1, holder.tvServerName, holder.tvServerStatus);
            alignTextView(rl1, holder.tvServerStatus, holder.tvServerLoad);
            alignTextView(rl1, holder.tvServerLoad, holder.tvServerType);
            alignTextView(rl1, holder.tvServerType, server.getServerTimezone().length()>1?
                                holder.tvServerTimezone : holder.tvServerLanguage);
            ((RelativeLayout) v).addView(rl1, lp);

            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
         }

        if (server != null) {


            holder.tvServerName.setText(server.getServerName());
            holder.tvServerName.setTextColor(Color.parseColor("#3ccbf5"));
            holder.tvServerName.setTextSize(22);
            holder.tvServerStatus.setText(server.getServerStatus());
            holder.tvServerLoad.setText(server.getServerPopulation());
            holder.tvServerType.setText(server.getServerType());
            holder.tvServerTimezone.setText(server.getServerTimezone());
            holder.tvServerLanguage.setText(server.getServerLanguage());

            if (server.getServerStatus() != null) {

                if (server.getServerStatus().equals("UP"))
                    holder.tvServerStatus.setTextColor(Color.GREEN);

                setServerLoadColor(holder.tvServerLoad);


            } else {
                LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(com.swtorserversstatus.R.layout.main, null);
                holder.tvServerName.setTextColor(Color.WHITE);
            }

        }
        return v;
    }

    private void setServerLoadColor(TextView tv)
    {
        Map<String,String> map = new HashMap<String,String>();
        map.put("light","#63F578");
        map.put("standard","#F7F55A");
        map.put("heavy","#FFA545");
        map.put("very heavy","#FF4E4E");
        map.put("full","#FF4E4E");

        String color =  map.get(tv.getText().toString().toLowerCase());
        if(color!=null){
          tv.setTextColor(Color.parseColor(color));
        }

    }


    private void alignTextView(RelativeLayout rl, TextView left, TextView tv) {

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.RIGHT_OF, left.getId());
        lp.setMargins(5, 2, 0, 2);
        tv.setTypeface(Typeface.DEFAULT_BOLD);

        rl.addView(tv, lp);
    }

    @Override
    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return super.areAllItemsEnabled();
    }


    @Override
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return true;
    }


}

