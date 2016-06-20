package com.fanxl.mycustomview.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.fanxl.mycustomview.R;

/**
 * Created by fanxl2 on 2016/6/16.
 */
public class DialogFragmentViewMenu extends DialogFragment {

	public static final String LAYOUT_RES_ID = "LAYOUT_RES_ID";

	public static DialogFragmentViewMenu getInstance(int layoutResId){
		Bundle bundle = new Bundle();
		bundle.putInt(LAYOUT_RES_ID, layoutResId);
		DialogFragmentViewMenu myRightMenu = new DialogFragmentViewMenu();
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

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		int layoutResId = getArguments().getInt(LAYOUT_RES_ID);
		View view = inflater.inflate(layoutResId, container);
		return view;
	}
}
