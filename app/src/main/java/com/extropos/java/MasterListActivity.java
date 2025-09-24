package com.extropos.java;

import com.extropos.java.fragment.CategoryListFragment;
import com.extropos.java.fragment.ProductListFragment;
import com.extropos.java.fragment.UserListFragment;
import com.extropos.java.utils.Constants;
import com.extropos.java.utils.Shared;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * An activity representing a list of Items. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link MasterDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MasterListFragment} and the item details (if present) is a
 * {@link MasterDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link MasterListFragment.Callbacks} interface to listen for item selections.
 */
public class MasterListActivity extends FragmentActivity implements
		MasterListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_master_list);
		
		// Initialize Shared utilities (fonts, preferences, etc.)
		Shared.initialize(this);

		if (findViewById(R.id.master_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((MasterListFragment) getFragmentManager().findFragmentById(
					R.id.master_list)).setActivateOnItemClick(true);
			
			setScreen("1");
		}

		// Handle deep links if any
		Intent intent = getIntent();
		if (intent != null && intent.getData() != null) {
			// Handle deep link URI
			// Add logic to handle deep links, e.g., navigate to specific item
		}
	}

	/**
	 * Callback method from {@link MasterListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			/*Bundle arguments = new Bundle();
			arguments.putString(MasterDetailFragment.ARG_ITEM_ID, id);
			MasterDetailFragment fragment = new MasterDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.replace(R.id.master_detail_container, fragment).commit();
			*/
			setScreen(id);
		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, MasterDetailActivity.class);
			detailIntent.putExtra(MasterDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	private void setScreen(String id)
	{
		while (getSupportFragmentManager().getBackStackEntryCount() > 0){
		    getSupportFragmentManager().popBackStackImmediate();
		}
		
		Bundle arguments = new Bundle();
		arguments.putString(Constants.ARG_ITEM_ID, id);
		
		Fragment fragment  = new UserListFragment();
		String tag = "";
		
		switch (id) {
		    case "1":
		        fragment = new UserListFragment();
		        break;
		    case "2":
		        fragment = new CategoryListFragment();
		        break;
		    case "3":
		        fragment = new ProductListFragment();
		        break;
		    default:
		        // Handle default case
		        break;
		}
	
		fragment.setArguments(arguments);
		getSupportFragmentManager()
		.beginTransaction()
		.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		.replace(R.id.master_detail_container, fragment,tag)
		.commit();
	}
}
