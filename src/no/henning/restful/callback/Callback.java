package no.henning.restful.callback;

public interface Callback<T> 
{
	void onSuccess(T response);
	void onError(T response);
}
