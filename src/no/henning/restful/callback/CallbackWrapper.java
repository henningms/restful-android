package no.henning.restful.callback;

import no.henning.restful.http.status.RestHttpResponse;

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
	
	public void error(RestHttpResponse response)
	{
		callback.onError(response);
	}
}
