package no.henning.restful.http.callback;

import no.henning.restful.http.status.RestHttpResponse;

public interface HttpRestClientResponseCallback
{
	void onDone(RestHttpResponse response);
}
