package no.henning.restful.callback;

import no.henning.restful.http.status.RestHttpResponse;

public interface Callback<T> 
{
	void onSuccess(T response);
	void onError(RestHttpResponse response);
}
