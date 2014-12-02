package com.potato.gamevideo.service.common;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.potato.gamevideo.utils.IRequestCallback;

public class BaseService {
	public <T> ErrorListener initErrorListener(final IRequestCallback<T> requestCallback){
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (requestCallback != null) {
					requestCallback.onFail(error);
				}
			}
		};
	}
}
