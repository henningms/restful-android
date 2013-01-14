package no.henning.restful.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
		// TODO Auto-generated method stub
		Log.e("restful", "" + method);
		
		return "LOL";
	}
}
