package no.henning.restful.model;

import java.lang.reflect.Field;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import no.henning.restful.callback.Callback;
import no.henning.restful.converter.json.JsonParser;
import no.henning.restful.converter.json.JsonWriter;
import no.henning.restful.http.HttpRestClient;
import no.henning.restful.http.builder.RestHttpRequestDetail;
import no.henning.restful.http.callback.HttpRestClientResponseCallback;
import no.henning.restful.http.status.RestHttpResponse;
import no.henning.restful.model.interfaces.DefaultRestActions;
import no.henning.restful.model.util.ModelUtil;

public class Model implements DefaultRestActions
{

	public Model()
	{

	}

	@Override
	public void get(final Callback<Model> callback)
	{
		performRequest("GET", this, callback);
	}

	@Override
	public void get()
	{
		get(null);
	}

	@Override
	public void save(Callback<Model> callback)
	{
		performRequest("POST", this, callback);
	}
	
	@Override
	public void save()
	{
		save(null);
	}
	
	@Override
	public void update(Callback<Model> callback)
	{
		performRequest("PUT", this, callback);
	}
	
	@Override
	public void update()
	{
		update(null);
	}

	@Override
	public void delete(Callback<Model> callback)
	{
		performRequest("DELETE", this, callback);
	}
	@Override
	public void delete()
	{
		delete(null);
	}
	
	private void performRequest(String httpVerb, Model body, final Callback<Model> callback)
	{
		RestHttpRequestDetail detail = new RestHttpRequestDetail(this, httpVerb);

		HttpUriRequest request = detail.buildRequest();
		
		final Model that = this;

		HttpRestClient.request(request, new HttpRestClientResponseCallback()
			{

				@Override
				public void onDone(RestHttpResponse response)
				{
					// TODO Auto-generated method stub
					Log.d("restful", "Response: " + response.getResponse());

					// Replace values with JSON String ;)
					updateValues(response.getResponse());
					
					if (callback == null) return;
					
					callback.onSuccess(that);
				}
			});
	}

	private void updateValues(String json)
	{
		Model model = JsonParser.parse(json, this.getClass());
		updateValues(model);
	}

	private void updateValues(Model fromModel)
	{
		ModelUtil.replaceValues(fromModel, this);
	}
	
	@Override
	public String toString()
	{
		return JsonWriter.from(this).toString();
	}
}
