package com.qd8s.werewolf2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by home on 7/3/17.
 */

public class UserListAdapter extends BaseAdapter {
    private Context _context;
    private LayoutInflater _inflater;
    private List<User> _userList;

    public UserListAdapter(Context context, List<User> users) {
        _context = context;
        _userList = users;
        _inflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _userList.size();
    }

    @Override
    public Object getItem(int position) {
        return _userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = _inflater.inflate(R.layout.user_row, parent, false);
        TextView userTitle = (TextView) rowView.findViewById(R.id.userName);
        User user = (User) getItem(position);

        userTitle.setText(user.getName());
        return rowView;
    }
}
