package com.potato.gamevideo.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.potato.gamevideo.R;
import com.potato.gamevideo.activity.lol.VideoSummaryListActivity;
import com.potato.gamevideo.adapter.VideoCategoryAdapter;
import com.potato.gamevideo.entity.video.lol.duowan.VideoCategory;
import com.potato.gamevideo.service.app.lol.duowan.VideoService;
import com.potato.gamevideo.utils.ICallback;
import com.potato.gamevideo.utils.VolleyApplication;
import com.potato.gamevideo.view.slidingmenu.BaseSlidingMenu;

public class MainActivity extends Activity implements OnClickListener {
	private VideoService videoService = new VideoService();
	private ExpandableListView listView;
	private boolean isLoading = false;
	private VideoCategoryAdapter adapter = null;
	private ProgressDialog dialog;
	private RelativeLayout item1 = null;
	private BaseSlidingMenu menu = null;

	@SuppressLint("InlinedApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ExpandableListView) findViewById(R.id.pull_to_refresh_listview);
		menu = (BaseSlidingMenu) findViewById(R.id.slidingMenu);
		item1 = (RelativeLayout) findViewById(R.id.item1);
		item1.setOnClickListener(this);
		
		
		listView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent();
				String tag = adapter.getList().get(groupPosition).subCategory
						.get(childPosition).tag;
				intent.putExtra("tag", tag);
				intent.setClass(MainActivity.this,
						VideoSummaryListActivity.class);
				startActivity(intent);
				return true;
			}
		});
		

		dialog = new ProgressDialog(this,
				ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
		dialog.setMessage("加载中...");
		dialog.show();
		getVideoCatelogs();
	}

	/**
	 * 获取LOL视频目录
	 */
	private void getVideoCatelogs() {
		if (!isLoading) {
			isLoading = true;
			videoService.getVideoCategories(
					new ICallback<List<VideoCategory>>() {
						// 成功
						@Override
						public void doCallback(List<VideoCategory> t) {
							adapter = new VideoCategoryAdapter(
									MainActivity.this, t, listView);
							listView.setAdapter(adapter);

							listView.expandGroup(0);
							isLoading = false;
							dialog.hide();
						}
					}, new ICallback<VolleyError>() {
						// 失败
						@Override
						public void doCallback(VolleyError t) {
							Toast.makeText(MainActivity.this, "加载失败",
									Toast.LENGTH_SHORT).show();
							isLoading = false;
						}
					}, VolleyApplication.getInstance().getRequestQueue());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.item1:
			//Toast.makeText(this, "item1", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent();
			intent.setClass(this, WeChatActivity.class);
			startActivity(intent);
			break;
		}

	}
	
	public void toggleMenu(View v){
		menu.ToggleMenu();
	}
}
