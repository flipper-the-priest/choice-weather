package com.example.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class JsonIo {
	private static final String TAG = JsonIo.class.getSimpleName();
	final static int timeout = 5000; // in milliseconds, so this is 5 seconds

	public static String loadJsonFromServer(String url) {
		String res = "";
		try {
			long start = System.currentTimeMillis();

			StringBuilder builder = new StringBuilder();

			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);

			HttpClient client = new DefaultHttpClient(httpParams);
			HttpGet request = new HttpGet(url);
			request.addHeader("User-Agent", "android");

			HttpResponse response = client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			long elapsed = System.currentTimeMillis() - start;
			
			res = builder.toString();
			
		} catch (MalformedURLException mue) {
			Log.e(TAG,
					"MalformedURLException Error: URL is malformed "
							+ mue.toString());
		} catch (ClientProtocolException cpe) {
			Log.e(TAG, "ClientProtocol Error: parsing data " + cpe.toString());
		} catch (IOException ioe) {
			Log.e(TAG, "IOException Error: parsing data " + ioe.toString());
		}
		
		// return JSON String
		return res;
	}

}