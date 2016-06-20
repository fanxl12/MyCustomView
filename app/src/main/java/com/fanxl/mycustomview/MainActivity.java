package com.fanxl.mycustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.fanxl.mycustomview.inter.TagSlidingInterface;
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
		init();
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
