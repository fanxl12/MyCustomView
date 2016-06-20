package com.fanxl.mycustomview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fanxl.mycustomview.inter.TagSlidingInterface;
import com.fanxl.mycustomview.widget.DialogFragmentViewMenu;
import com.fanxl.mycustomview.widget.RightListMenu;
import com.fanxl.mycustomview.widget.ScrollTagView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TagSlidingInterface {

	private ScrollTagView second_stag;
	private ImageButton right_bt_arrow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initToolbar();
		init();
	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	private void init() {
		second_stag = (ScrollTagView) findViewById(R.id.main_stag);
		List<String> datas = new ArrayList<>();
		datas.add("价格");
		datas.add("类型");
		datas.add("距离");
		datas.add("样式");
		datas.add("评价");
		second_stag.setTabDatas(datas);
		second_stag.setTagSlidingInterface(this);

		right_bt_arrow = (ImageButton) findViewById(R.id.right_bt_arrow);
		right_bt_arrow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isLeft){
					second_stag.toRight();
				}else {
					second_stag.toLeft();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()){
			case R.id.action_style_one:
				showDialogMenu();
				break;
			case R.id.action_style_two:
				showListDialog();
				break;
			case R.id.action_style_three:
				showFragmentDialog();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void showFragmentDialog(){
		DialogFragmentViewMenu menu = DialogFragmentViewMenu.getInstance(R.layout.dialog_fragment_menu);
		menu.show(getSupportFragmentManager(), "DialogFragmentViewMenu");
	}

	private Dialog rightMenu;
	private void showDialogMenu() {
		if (rightMenu==null){
			rightMenu = new Dialog(this, R.style.Theme_dialog);
			rightMenu.setContentView(R.layout.right_dialog_menu);
			Window win = rightMenu.getWindow();
			win.setGravity(Gravity.RIGHT|Gravity.TOP);
			rightMenu.setCanceledOnTouchOutside(true);
			win.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		rightMenu.show();
	}

	private RightListMenu listMenu = null;
	private void showListDialog(){
		if (listMenu==null){
			String[] datas = new String[5];
			datas[0] = "项目1";
			datas[1] = "项目2";
			datas[2] = "项目3";
			datas[3] = "项目4";
			datas[4] = "项目5";
			listMenu = RightListMenu.getInstance(datas);
			listMenu.setOnMenuItemClickListener(new RightListMenu.MenuItemClickListener() {
				@Override
				public void itemCliclListener(int position) {
					Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
				}
			});
		}
		listMenu.show(getSupportFragmentManager(), "RightListMenu");
	}

	private boolean isLeft = true;

	@Override
	public void slid2Left() {
		isLeft = true;
		right_bt_arrow.setImageResource(R.drawable.jiantou_right);
	}

	@Override
	public void slid2Right() {
		isLeft = false;
		right_bt_arrow.setImageResource(R.drawable.jiantou_left);
	}
}
