package no.henning.restful.model;

import java.lang.reflect.Field;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import no.henning.restful.converter.json.JsonParser;
import no.henning.restful.http.HttpRestClient;
import no.henning.restful.http.builder.RestHttpRequestDetail;
import no.henning.restful.http.callback.HttpRestClientResponseCallback;
import no.henning.restful.http.status.RestHttpResponse;
import no.henning.restful.model.interfaces.DefaultRestActions;

public class Model implements DefaultRestActions
{

	public Model()
	{
		
	}
	
	@Override
	public void get()
	{
		RestHttpRequestDetail detail = new RestHttpRequestDetail(this, "GET");
		
		HttpUriRequest request = detail.buildRequest();
		
		Log.d("restful", "URI: " + request.getURI());
		Log.d("restful", "METHOD: " + request.getMethod());
		
		HttpRestClient.request(request, new HttpRestClientResponseCallback()
			{
				
				@Override
				public void onDone(RestHttpResponse response)
				{
					// TODO Auto-generated method stub
					Log.d("restful", "Response: " + response.getResponse());
				
					
					try
					{
						JSONObject jsonObject = new JSONObject(response.getResponse());
						Model model = JsonParser.parse(jsonObject, this.getClass());
						
						for (Field field : model.getClass().getDeclaredFields())
						{
							field.setAccessible(true);
							
							Log.d("restful", "" + field.getName() + " : " + field.get(model));
						}
					}
					catch (JSONException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IllegalArgumentException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
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
