package no.henning.restful.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import no.henning.restful.http.HttpRestClient;
import no.henning.restful.http.builder.RestHttpRequestDetail;
import no.henning.restful.http.callback.HttpRestClientResponseCallback;
import no.henning.restful.http.status.RestHttpResponse;
import no.henning.restful.model.Model;
import no.henning.restful.service.RestService;
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
		
		Class<? extends Model> model = GenericHelper.getModelFromProxyMethod(method);
		
		String path = ProxyHelper.getAbsolutePathFromProxyMethod(method, arguments);
		String httpVerb = HttpHelper.getHttpRequestVerbFromProxyMethod(method);
		
		return "LOL";
	}
}
