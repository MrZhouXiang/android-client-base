package puyuntech.com.beihai.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 作者：zx
 * 时间：2015-12-03 下午 14:55
 * 描述：
 */
public class AppBaseAdapter extends BaseAdapter {
    protected LayoutInflater inflater;
    protected Context context;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void skipIntent(Class clz, HashMap<String, Object> map) {
        Intent intent = new Intent(context, clz);
        if (map != null) {
            Iterator it = map.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry entry = (Map.Entry) it.next();

                String key = (String) entry.getKey();

                Serializable value = (Serializable) entry.getValue();

                intent.putExtra(key, value);
            }
        }
        try {
            context.startActivity(intent);

        } catch (Exception e) {

        }
    }

    public void skipIntent(Class clz) {
        Intent intent = new Intent(context, clz);
        try {
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }

    public void showToast(String message) {
        try {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

        }
    }

    public void showShortToast(String message) {
        try {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

        }
    }

}
