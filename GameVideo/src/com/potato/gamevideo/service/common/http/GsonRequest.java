package com.potato.gamevideo.service.common.http;

import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.potato.gamevideo.utils.IRequestCallback;

public class GsonRequest<T> extends Request<T> {
	private Gson mGson = new Gson();
	private final Listener<T> mListener;
	private TypeToken<T> mTypeToken;
	private IRequestCallback<T> requestCallback;

	private GsonRequest(int method, String url, ErrorListener errorListener,
			IRequestCallback<T> requestCallback, TypeToken<T> typeToken) {
		super(method, url, errorListener);
		mListener = initListener();
		mTypeToken = typeToken;
		this.requestCallback = requestCallback;
	}

	public GsonRequest(int method, String url,
			IRequestCallback<T> requestCallback, ErrorListener errorListener,
			TypeToken<T> typeToken) {
		this(method, url, errorListener, requestCallback, typeToken);
	}

	private Listener<T> initListener() {
		return new Response.Listener<T>() {
			@Override
			public void onResponse(T t) {
				// callback UIÖ÷Ïß³Ì
				if (requestCallback != null) {
					requestCallback.onSuccess(t);
				}
			}
		};
	}

//	private TypeToken<T> getTypeToke() {
//		return new TypeToken<T>() {
//		};
//	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return (Response<T>) Response.success(
					mGson.fromJson(jsonString, mTypeToken.getType()),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

}
