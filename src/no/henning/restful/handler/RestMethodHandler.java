package no.henning.restful.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.apache.http.client.methods.HttpUriRequest;

import no.henning.restful.callback.Callback;
import no.henning.restful.callback.CallbackWrapper;
import no.henning.restful.converter.json.JsonParser;
import no.henning.restful.converter.json.JsonWriter;
import no.henning.restful.http.HttpRestClient;
import no.henning.restful.http.builder.RestHttpRequestDetail;
import no.henning.restful.http.callback.HttpRestClientResponseCallback;
import no.henning.restful.http.status.RestHttpResponse;
import no.henning.restful.model.Model;
import no.henning.restful.utils.GenericHelper;
import no.henning.restful.utils.HttpHelper;
import no.henning.restful.utils.ProxyHelper;

import android.util.Log;

public class RestMethodHandler implements InvocationHandler
{
	private Class<?> declaringClass;
	
	public RestMethodHandler(Class<?> type)
	{
		declaringClass = type;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
			throws Throwable
	{
		
		Log.d("restful", "RestMethodHandler: Getting Model associated with " + method.getName());
		Class<? extends Model> model = GenericHelper.getModelFromProxyMethod(method);
		
		Log.d("restful", "RestMethodHandler: Getting absolute path");
		String path = ProxyHelper.getAbsolutePathFromProxyMethod(method, arguments);
		
		Log.d("restful", "RestMethodHandler: Getting what HTTP verb to use");
		String httpVerb = HttpHelper.getHttpRequestVerbFromProxyMethod(method);
		
		String queryPath = ProxyHelper.getProxyQueryPath(method, arguments);
		Log.d("restful", "RestMethodHandler: Full path: " + path + queryPath);
		
		String absolutePath = path + queryPath;
		
		Object entityObject = ProxyHelper.getEntityObjectFromProxyMethod(method, arguments);
		String entityAsJsonString = JsonWriter.from(entityObject).toString();
		
		final Callback<?> callback = ProxyHelper.getCallbackArgument(arguments);
		final Type callbackType = ProxyHelper.getCallbackType(callback);
		
		HttpUriRequest httpRequest = new RestHttpRequestDetail(model, absolutePath, httpVerb, entityAsJsonString).buildRequest();
		HttpRestClient.request(httpRequest, new HttpRestClientResponseCallback()
			{
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public void onDone(RestHttpResponse response)
				{
					// TODO Auto-generated method stub
					Log.d("restful", "Response: " + response.getResponse());
					
					
					if (response.getStatusCode() >= 200 && response.getStatusCode() < 300)
					{	
						Object t = JsonParser.parse(response.getResponse(), callback);
						new CallbackWrapper(callback).success(t);
					}
					else
					{
						new CallbackWrapper(callback).error(response);
					}
					
				}
			});
		
		return true;
	}
}
