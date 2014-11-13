package com.potato.gamevideo.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.potato.gamevideo.R;
import com.potato.gamevideo.entity.video.lol.duowan.VideoCategory;
import com.potato.gamevideo.utils.VolleyApplication;

public class VideoCategoryAdapter extends BaseExpandableListAdapter {
	private Activity activity;
	private List<VideoCategory> data;
	private static LayoutInflater inflater = null;
	public List<VideoCategory> getList() {
        return data;
    }
	
	public VideoCategoryAdapter(Activity a, List<VideoCategory> d,
			ExpandableListView listView) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return data.get(groupPosition).subCategory.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.video_category_item, null);
		Log.d("getGroupView", "groupPosition:" + groupPosition);

		TextView title = (TextView) vi.findViewById(R.id.title); // 标题
		final NetworkImageView imageView = (NetworkImageView) vi
				.findViewById(R.id.pic); // 缩略图

		VideoCategory videoCategory = data.get(groupPosition);

		title.setText(videoCategory.subCategory.get(childPosition).name);
		ImageLoader imageLoader = VolleyApplication.getInstance()
				.getImageLoader();
		imageView.setDefaultImageResId(R.drawable.downloading);
		imageView.setErrorImageResId(R.drawable.ic_launcher);
		imageView.setImageUrl(
				videoCategory.subCategory.get(childPosition).icon, imageLoader);
		return vi;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return data.get(groupPosition).subCategory.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return data.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return data.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.video_category_group, null);
		TextView groupTitle = (TextView) vi.findViewById(R.id.groupTitle); // 标题
		VideoCategory videoCategory = data.get(groupPosition);
		groupTitle.setText(videoCategory.name);
		return vi;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}
