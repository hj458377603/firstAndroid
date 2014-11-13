package com.potato.gamevideo.service.app.lol.duowan;

import java.util.List;

import android.annotation.SuppressLint;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.potato.gamevideo.entity.video.lol.duowan.VideoCategory;
import com.potato.gamevideo.entity.video.lol.duowan.VideoSummary;
import com.potato.gamevideo.service.common.config.ServiceConfig;
import com.potato.gamevideo.service.common.http.GsonRequest;
import com.potato.gamevideo.utils.ICallback;

public class VideoService {

	/**
	 * 获取LOL视频目录列表
	 * 
	 * @param successCallback
	 * @param errorCallback
	 * @param queue
	 */
	public void getVideoCategories(
			final ICallback<List<VideoCategory>> successCallback,
			final ICallback<VolleyError> errorCallback, RequestQueue queue) {

		String url = ServiceConfig.DuoWanVideoCategoryServiceUrl;
		TypeToken<List<VideoCategory>> typeToken = new TypeToken<List<VideoCategory>>() {
		};
		GsonRequest<List<VideoCategory>> gsonRequest = new GsonRequest<List<VideoCategory>>(
				url, typeToken, new Response.Listener<List<VideoCategory>>() {
					@Override
					public void onResponse(List<VideoCategory> videoCategories) {
						// callback UI主线程
						if (successCallback != null) {
							successCallback.doCallback(videoCategories);
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (errorCallback != null) {
							errorCallback.doCallback(error);
						}
					}
				});
		queue.add(gsonRequest);
	}

	/**
	 * 获取LOL视频摘要列表
	 * 
	 * @param successCallback
	 * @param errorCallback
	 * @param queue
	 * @param heroEnName
	 * @param tag
	 * @param pageIndex
	 */
	@SuppressLint("DefaultLocale")
	public void getVideoSummaryList(
			final ICallback<List<VideoSummary>> successCallback,
			final ICallback<VolleyError> errorCallback, RequestQueue queue,
			String heroEnName, String tag, int pageIndex) {

		String url = String.format(
				ServiceConfig.DuoWanVideoSummaryListServiceUrl, heroEnName,
				tag, pageIndex);

		TypeToken<List<VideoSummary>> typeToken = new TypeToken<List<VideoSummary>>() {
		};

		GsonRequest<List<VideoSummary>> gsonRequest = new GsonRequest<List<VideoSummary>>(
				url, typeToken, new Response.Listener<List<VideoSummary>>() {
					@Override
					public void onResponse(List<VideoSummary> videoSummaryList) {
						// callback UI主线程
						if (successCallback != null) {
							successCallback.doCallback(videoSummaryList);
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (errorCallback != null) {
							errorCallback.doCallback(error);
						}
					}
				});
		queue.add(gsonRequest);
	}
}
