package no.henning.restful.model.annotation;

import no.henning.restful.service.RestService;

public @interface UsesRestService
{
	Class<? extends RestService> value();
}
