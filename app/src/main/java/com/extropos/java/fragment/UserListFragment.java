package com.extropos.java.fragment;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.extropos.java.R;
import com.extropos.java.adapter.UserListAdapter;
import com.extropos.java.dummy.MasterContent;
import com.extropos.java.entity.User;
import com.extropos.java.sqlite.DatabaseManager;
import com.extropos.java.sqlite.ds.UserDataSource;
import com.extropos.java.utils.Constants;
import com.extropos.java.utils.Shared;

public class UserListFragment extends Fragment implements OnClickListener{
	private MasterContent.DummyItem mItem;
	private ListView lv;
	private UserListAdapter adapter;
	private ArrayList<User> dtlist;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(Constants.ARG_ITEM_ID)) {
			mItem = MasterContent.ITEM_MAP.get(getArguments().getString(Constants.ARG_ITEM_ID));
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_user_list,container, false);
		
		lv = (ListView)rootView.findViewById(R.id.listView1);
		adapter = new UserListAdapter(getActivity(),mItem.id);
		
		popolateAdapter();
		lv.setAdapter(adapter);
				
		TextView title = (TextView)rootView.findViewById(R.id.item_detail);
		title.setTypeface(Shared.OpenSansSemibold);
		if (mItem != null) {
			title.setText(mItem.content);
		}
		
		rootView.findViewById(R.id.imageButton1).setOnClickListener(this);
		
		return rootView;
	}
	
	private void popolateAdapter()
	{
		SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
        UserDataSource ds = new UserDataSource(db);
        dtlist = ds.getAll();
     
		adapter.set(dtlist);
		DatabaseManager.getInstance().closeDatabase();
	}

	@Override
	public void onClick(View v) {
		Fragment fragment  = new UserAddFragment();
		Bundle arguments = new Bundle();
		arguments.putString(Constants.ARG_ITEM_ID, mItem.id);
		fragment.setArguments(arguments);
		getFragmentManager().beginTransaction()
		.setTransition(android.R.anim.slide_in_left)
		.addToBackStack("add")
		.replace(R.id.master_detail_container, fragment).commit();
	}

}


