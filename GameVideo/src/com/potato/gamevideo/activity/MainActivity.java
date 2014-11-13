package com.potato.gamevideo.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

public class MainActivity extends Activity implements OnClickListener {
	private VideoService videoService = new VideoService();
	private ExpandableListView listView;
	private boolean isLoading = false;
	private VideoCategoryAdapter adapter = null;
	private ProgressDialog dialog;
	private RelativeLayout item1 = null;

	@SuppressLint("InlinedApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ExpandableListView) findViewById(R.id.pull_to_refresh_listview);
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
		// listView.setMode(Mode.PULL_FROM_START);
		// listView.setOnRefreshListener(new
		// OnRefreshListener<ExpandableListView>() {
		// @Override
		// public void onRefresh(PullToRefreshBase<ExpandableListView>
		// refreshView) {
		//
		// // ��Ѷ�б�ˢ��
		// Log.d("pull", "refresh");
		// String label = DateUtils.formatDateTime(
		// getApplicationContext(),
		// System.currentTimeMillis(),
		// DateUtils.FORMAT_SHOW_TIME
		// | DateUtils.FORMAT_SHOW_DATE
		// | DateUtils.FORMAT_ABBREV_ALL);
		//
		// // �����ϴ�ˢ��ʱ��
		// refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
		// label);
		// getVideoCatelogs();
		// }
		// });

		dialog = new ProgressDialog(this,
				ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
		dialog.setMessage("������...");
		dialog.show();
		getVideoCatelogs();
	}

	/**
	 * ��ȡLOL��ƵĿ¼
	 */
	private void getVideoCatelogs() {
		if (!isLoading) {
			isLoading = true;
			videoService.getVideoCategories(
					new ICallback<List<VideoCategory>>() {
						// �ɹ�
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
						// ʧ��
						@Override
						public void doCallback(VolleyError t) {
							Toast.makeText(MainActivity.this, "����ʧ��",
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
}
