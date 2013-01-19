package no.henning.restful.model;

import org.apache.http.client.methods.HttpUriRequest;

import android.util.Log;
import no.henning.restful.http.HttpRestClient;
import no.henning.restful.http.builder.RestHttpRequestDetail;
import no.henning.restful.http.callback.HttpRestClientResponseCallback;
import no.henning.restful.http.status.RestHttpResponse;
import no.henning.restful.model.interfaces.DefaultRestActions;

public class Model implements DefaultRestActions
{

	@Override
	public void get()
	{
		RestHttpRequestDetail detail = new RestHttpRequestDetail(this, "GET");
		
		HttpUriRequest request = detail.buildRequest();
		
		Log.d("restful", "URI: " + request.getURI());
		Log.d("restful", "METHOD: " + request.getMethod());
		
		HttpRestClient.request(detail.buildRequest(), new HttpRestClientResponseCallback()
			{
				
				@Override
				public void onDone(RestHttpResponse response)
				{
					// TODO Auto-generated method stub
					Log.d("restful", "Response: " + response.getResponse());
				}
			});
	}

	@Override
	public void save()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete()
	{
		// TODO Auto-generated method stub
		
	}

}
