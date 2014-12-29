package com.potato.gamevideo.service.app.lol.duowan;

import java.util.List;

import android.annotation.SuppressLint;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.google.gson.reflect.TypeToken;
import com.potato.gamevideo.entity.video.lol.duowan.VideoCategory;
import com.potato.gamevideo.entity.video.lol.duowan.VideoSummary;
import com.potato.gamevideo.service.common.BaseService;
import com.potato.gamevideo.service.common.config.ServiceConfig;
import com.potato.gamevideo.service.common.http.GsonRequest;
import com.potato.gamevideo.utils.IRequestCallback;

public class VideoService extends BaseService {

	/**
	 * 获取LOL视频目录列表
	 * 
	 * @param requestCallback
	 * @param queue
	 */
	public void getVideoCategories(
			final IRequestCallback<List<VideoCategory>> requestCallback,
			RequestQueue queue) {

		String url = ServiceConfig.DuoWanVideoCategoryServiceUrl;
		TypeToken<List<VideoCategory>> typeToken = new TypeToken<List<VideoCategory>>() {
		};
		
		GsonRequest<List<VideoCategory>> gsonRequest = new GsonRequest<List<VideoCategory>>(
				Method.GET, url, requestCallback,
				initErrorListener(requestCallback), typeToken);
		queue.add(gsonRequest);
	}

	/**
	 * 获取LOL视频摘要列表
	 * 
	 * @param requestCallback
	 * @param queue
	 * @param heroEnName
	 * @param tag
	 * @param pageIndex
	 */
	@SuppressLint("DefaultLocale")
	public void getVideoSummaryList(
			final IRequestCallback<List<VideoSummary>> requestCallback,
			RequestQueue queue, String heroEnName, String tag, int pageIndex) {
		String url = String.format(
				ServiceConfig.DuoWanVideoSummaryListServiceUrl, heroEnName,
				tag, pageIndex);

		TypeToken<List<VideoSummary>> typeToken = new TypeToken<List<VideoSummary>>() {
		};
		GsonRequest<List<VideoSummary>> gsonRequest = new GsonRequest<List<VideoSummary>>(
				Method.GET, url, requestCallback,
				initErrorListener(requestCallback), typeToken);
		queue.add(gsonRequest);
	}

}
