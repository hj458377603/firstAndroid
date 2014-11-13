package com.potato.gamevideo.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.potato.gamevideo.R;
import com.potato.gamevideo.entity.video.lol.duowan.VideoSummary;
import com.potato.gamevideo.utils.VolleyApplication;

public class VideoSummaryListAdapter extends BaseAdapter{
	private Activity activity;
	private List<VideoSummary> data;
	private static LayoutInflater inflater = null;
	
	public VideoSummaryListAdapter(Activity a, List<VideoSummary> d,
			PullToRefreshListView listView) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.activity_video_item, null);

		TextView title = (TextView) vi.findViewById(R.id.videoSummaryTitle); // 标题
		TextView uploadTime=(TextView) vi.findViewById(R.id.txtUploadTime);//上传时间
		TextView videoLength=(TextView) vi.findViewById(R.id.txtVideoLength);//视频时长
		final NetworkImageView imageView = (NetworkImageView) vi
				.findViewById(R.id.videoImageCover); // 缩略图

		VideoSummary videoSummary = data.get(position);
		title.setText(videoSummary.title);
		uploadTime.setText(videoSummary.getUpload_time());
		videoLength.setText(videoSummary.getVideo_length());
		ImageLoader imageLoader = VolleyApplication.getInstance()
				.getImageLoader();
		imageView.setDefaultImageResId(R.drawable.downloading);
		imageView.setErrorImageResId(R.drawable.ic_launcher);
		imageView.setImageUrl(videoSummary.cover_url, imageLoader);
		return vi;
	}

}
