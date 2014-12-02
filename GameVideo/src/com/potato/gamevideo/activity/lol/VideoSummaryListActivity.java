package com.potato.gamevideo.activity.lol;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.potato.gamevideo.R;
import com.potato.gamevideo.adapter.VideoSummaryListAdapter;
import com.potato.gamevideo.entity.video.lol.duowan.VideoSummary;
import com.potato.gamevideo.service.app.lol.duowan.VideoService;
import com.potato.gamevideo.utils.IRequestCallback;
import com.potato.gamevideo.utils.VolleyApplication;

public class VideoSummaryListActivity extends Activity {
	private VideoService videoService = new VideoService();
	private PullToRefreshListView listView;
	private boolean isLoading = false;
	private VideoSummaryListAdapter adapter = null;
	private ProgressDialog dialog;
	private String tag = null;
	private int pageIndex = 1;
	private List<VideoSummary> videoList;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_list);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		tag = bundle.getString("tag");
		listView = (PullToRefreshListView) findViewById(R.id.video_listview);
		listView.setMode(Mode.BOTH);
		getVideoCatelogs(null, tag, pageIndex);

		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// ��Ѷ�б�ˢ��
				if (listView.isHeaderShown()) {
					// ��Ѷ�б�ˢ��
					String label = DateUtils.formatDateTime(
							getApplicationContext(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_ABBREV_ALL);

					// �����ϴ�ˢ��ʱ��
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
							label);
					pageIndex = 1;
					getVideoCatelogs(null, tag, pageIndex);
				} else if (listView.isFooterShown()) {
					// ���ظ���
					pageIndex++;
					getVideoCatelogs(null, tag, pageIndex);
				}
			}
		});

		dialog = new ProgressDialog(this,
				ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
		dialog.setMessage("������...");
		dialog.show();
	}

	/**
	 * ��ȡLOL��ƵĿ¼
	 */
	private void getVideoCatelogs(String heroEnName, String tag,
			final int pageIndex) {
		if (!isLoading) {
			isLoading = true;
			videoService.getVideoSummaryList(
					new IRequestCallback<List<VideoSummary>>() {

						/**
						 * �ɹ�
						 * 
						 * @param t
						 */
						@Override
						public void onSuccess(List<VideoSummary> t) {
							if (pageIndex == 1) {
								// �������ҳ
								videoList = t;
								adapter = new VideoSummaryListAdapter(
										VideoSummaryListActivity.this,
										videoList, listView);
								listView.setAdapter(adapter);
								dialog.hide();
							} else {
								if (t != null && t.size() > 0) {
									videoList.addAll(t);
								} else {
									VideoSummaryListActivity.this.pageIndex--;
									Toast.makeText(
											VideoSummaryListActivity.this,
											"û�и�����", Toast.LENGTH_SHORT).show();
								}
							}
							adapter.notifyDataSetChanged();
							listView.onRefreshComplete();

							isLoading = false;
							listView.onRefreshComplete();
							dialog.hide();
						}

						@Override
						public void onFail(VolleyError error) {
							Toast.makeText(VideoSummaryListActivity.this,
									"����ʧ��", Toast.LENGTH_SHORT).show();
							listView.onRefreshComplete();
							isLoading = false;
						}
					}, VolleyApplication.getInstance().getRequestQueue(),
					heroEnName, tag, pageIndex);
		}
	}
}
