package com.fanxl.mycustomview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fanxl.mycustomview.R;
import com.fanxl.mycustomview.inter.TagSlidingInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanxl2 on 2016/6/16.
 * 可水平滑动的下拉菜单
 */
public class ScrollTagView extends HorizontalScrollView {

	private LinearLayout mContainer;
	private int viewWidth = 0;
	private Context mContext;
	private LayoutInflater inflater;
	private int itemWidth = 0;

	public ScrollTagView(Context context) {
		this(context, null);
	}

	public ScrollTagView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollTagView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext=context;
		this.inflater = LayoutInflater.from(mContext);
		init();
		initTabs();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width = 0;
		int height = 0;

		if (widthMode==MeasureSpec.EXACTLY){
			width = widthSize;
		}else {

		}

		if (heightMode==MeasureSpec.EXACTLY){
			height = heightSize;
		}else {

		}

		viewWidth = width - getPaddingLeft() - getPaddingRight();
		itemWidth = viewWidth / 3;

		Log.i(TAG, "onMeasure viewWidth: " + viewWidth);

		setMeasuredDimension(width, height);
		initTabs();
	}

	private List<String> datas;

	public void setTabDatas(List<String> datas){
		this.datas=datas;
		invalidate();
	}

	private TagSlidingInterface tagSlidingInterface;
	public void setTagSlidingInterface(TagSlidingInterface tagSlidingInterface){
		this.tagSlidingInterface=tagSlidingInterface;
	}

	private void initTabs() {
		if(itemWidth==0)return;
		Log.i(TAG, "initTabs itemWidth: "+itemWidth);
		if (mContainer.getChildCount()==datas.size())return;
		for (int i=0; i<datas.size(); i++){
			final int position = i;
			View view = inflater.inflate(R.layout.tag_item, null);
			CheckBox cb = (CheckBox) view.findViewById(R.id.tag_item_cb);
			cb.setText(datas.get(i));
			cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (buttonView.isPressed()){
						selectTab(position, isChecked);
					}
				}
			});
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.MATCH_PARENT);;
			mContainer.addView(view, params);
		}
	}


	private void init() {
		datas = new ArrayList<>();
		mContainer = new LinearLayout(mContext);
		mContainer.setOrientation(LinearLayout.HORIZONTAL);
		addView(mContainer);
	}

	private int downX;
	private final int HORIZONTAIL_X = 10;
	private static final String TAG = "ScrollTagView";

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()){
			case MotionEvent.ACTION_DOWN:
				downX = (int) ev.getX();
				break;
			case MotionEvent.ACTION_UP:
				int x = (int) ev.getX();
				int currentPostX = downX - x;
				if (currentPostX>HORIZONTAIL_X){
					toRight();
				}else if (currentPostX<-HORIZONTAIL_X){
					toLeft();
				}
				return true;
		}
		return super.onTouchEvent(ev);
	}

	public void toLeft(){
		smoothScrollTo(0,0);
		if (tagSlidingInterface!=null){
			tagSlidingInterface.slid2Left();
		}
	}

	public void toRight(){
		smoothScrollTo(viewWidth,0);
		if (tagSlidingInterface!=null){
			tagSlidingInterface.slid2Right();
		}
	}

	public void selectTab(int position, boolean isChecked){
		int count = mContainer.getChildCount();
		for (int i=0; i<count; i++){
			CheckBox cb = getCheckBoxByPosition(i);
			if (i==position){
				cb.setChecked(isChecked);
			}else{
				cb.setChecked(false);
			}
		}
	}

	private CheckBox getCheckBoxByPosition(int position){
		RelativeLayout view = (RelativeLayout)mContainer.getChildAt(position);
//		CheckBox cb = (CheckBox) view.findViewById(R.id.tag_item_cb);
		CheckBox cb = (CheckBox) view.getChildAt(0);
		return cb;
	}

}
