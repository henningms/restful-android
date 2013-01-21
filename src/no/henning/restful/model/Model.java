package no.henning.restful.model;

import java.lang.reflect.Field;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import no.henning.restful.callback.Callback;
import no.henning.restful.converter.json.JsonParser;
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
	public void get(final Callback<? extends Model> callback)
	{
		// TODO Auto-generated method stub
		RestHttpRequestDetail detail = new RestHttpRequestDetail(this, "GET");

		HttpUriRequest request = detail.buildRequest();

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
					
					callback.onSuccess(null);
				}
			});
	}

	@Override
	public void get()
	{
		get(null);
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

	private void updateValues(String json)
	{
		Model model = JsonParser.parse(json, this.getClass());
		updateValues(model);
	}

	private void updateValues(Model fromModel)
	{
		ModelUtil.replaceValues(fromModel, this);
	}

}
