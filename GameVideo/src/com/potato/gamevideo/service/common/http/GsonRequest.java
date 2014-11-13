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

public class GsonRequest<T> extends Request<T> {
	private Gson mGson;
	private final Listener<T> mListener;
	private TypeToken<T> mTypeToken;

	public GsonRequest(int method, String url, TypeToken<T> typeToken,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mGson = new Gson();
		mListener = listener;
		mTypeToken = typeToken;
	}

	public GsonRequest(String url, TypeToken<T> clazz, Listener<T> listener,
			ErrorListener errorListener) {
		this(Method.GET, url, clazz, listener, errorListener);
	}

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
