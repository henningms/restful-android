package no.henning.restful.callback;

import android.util.Log;

public class CallbackWrapper<T>
{
	private final Callback<T> callback;
	
	public CallbackWrapper(Callback<T> callback)
	{
		this.callback = callback;
	}
	
	@SuppressWarnings("unchecked")
	public void success(Object object)
	{
		callback.onSuccess((T) object);
	}
	
	@SuppressWarnings("unchecked")
	public void error(Object object)
	{
		callback.onError((T) object);
	}
}
