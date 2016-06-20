package com.fanxl.mycustomview.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fanxl.mycustomview.R;

/**
 * Created by fanxl2 on 2016/6/16.
 */
public class RightListMenu extends DialogFragment {

	public static final String LIST_DATAS = "LIST_DATAS";

	public static RightListMenu getInstance(String[] datas){
		Bundle bundle = new Bundle();
		bundle.putStringArray(LIST_DATAS, datas);
		RightListMenu myRightMenu = new RightListMenu();
		myRightMenu.setArguments(bundle);
		return myRightMenu;
	}

	@Override
	public void onStart() {
		super.onStart();

		Dialog dialog = getDialog();
		if (dialog!=null){
			WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
			p.y = 220;
			p.x = 20;
			p.width = 500;
			p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
			p.gravity = Gravity.RIGHT|Gravity.TOP;
			getDialog().getWindow().setAttributes(p);
			getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_menu_bg_house_manage);
		}
	}

	private static final String TAG = "RightListMenu";

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.right_list_menu, container);
		ListView menu_lv = (ListView) view;
		String [] datas = getArguments().getStringArray(LIST_DATAS);
		menu_lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datas){


			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView txt = (TextView) view.findViewById(android.R.id.text1);
				txt.setGravity(Gravity.CENTER);
				return view;
			}
		});



		menu_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.i(TAG, "onItemClick: "+position);
				dismiss();
				if (menuItemClickListener!=null){
					menuItemClickListener.itemCliclListener(position);
				}
			}
		});
		menu_lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		return view;
	}

	public interface MenuItemClickListener{
		void itemCliclListener(int position);
	}

	private MenuItemClickListener menuItemClickListener;

	public void setOnMenuItemClickListener(MenuItemClickListener menuItemClickListener){
		this.menuItemClickListener=menuItemClickListener;
	}

}
