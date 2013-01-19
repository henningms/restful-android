package no.henning.restful.http;

import java.io.IOException;

import no.henning.restful.callback.Callback;
import no.henning.restful.http.callback.HttpRestClientResponseCallback;
import no.henning.restful.http.status.RestHttpResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class HttpRestClient extends AsyncTask<HttpUriRequest, Void, RestHttpResponse>
{
	private final static DefaultHttpClient client = new DefaultHttpClient();
	
	private HttpRestClientResponseCallback callback;
	
	public HttpRestClient(HttpRestClientResponseCallback callback)
	{
		this.callback = callback;
	}
	
	@Override
	protected RestHttpResponse doInBackground(HttpUriRequest... requests)
	{
		try
		{
			HttpResponse response = client.execute(requests[0]);
			String responseAsString = EntityUtils
					.toString(response.getEntity());
			
			StatusLine responseStatusLine = response.getStatusLine();

			return new RestHttpResponse(responseStatusLine.getStatusCode(),
					responseStatusLine.getReasonPhrase(), responseAsString);
		}
		catch (ClientProtocolException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(RestHttpResponse response)
	{
		if (callback == null) return;
		
		callback.onDone(response);
	}

	public static void request(HttpUriRequest request, HttpRestClientResponseCallback callback)
	{
		new HttpRestClient(callback).execute(request);
	}
}
