package no.henning.restful.utils;

import java.lang.reflect.Method;

import android.util.Log;

import no.henning.restful.model.Model;
import no.henning.restful.model.annotation.BelongsTo;
import no.henning.restful.model.annotation.UsesRestService;
import no.henning.restful.service.RestService;
import no.henning.restful.service.annotation.Url;

public class GenericHelper
{
	/**
	 * GET REST SERVICE
	 */

	public static Class<? extends RestService> getRestServiceFromModel(
			Class<? extends Model> model)
	{
		if (model == null) return null;

		Log.d("restful",
				"getRestServiceFromModel: Trying to retrieve what RestService "
						+ model.getSimpleName() + " uses...");
		// Tries to retrieve the annotation that specifies a rest service from a
		// model
		UsesRestService annotation = model.getAnnotation(UsesRestService.class);

		if (annotation == null) return null;

		Log.d("restful", "getRestServiceFromModel: " + model.getSimpleName()
				+ " uses " + annotation.value().getSimpleName());

		return annotation.value();
	}

	public static Class<? extends RestService> getRestServiceFromProxyMethod(
			Method method)
	{
		if (method == null) return null;

		Log.d("restful",
				"getRestServiceFromProxyMethod: Trying to retrieve what RestService "
						+ method.getDeclaringClass().getSimpleName() + " uses");
		
		Class<? extends Model> model = getModelFromProxyMethod(method);

		return getRestServiceFromModel(model);
	}

	public static Class<? extends Model> getModelFromProxyMethod(Method method)
	{
		if (method == null) return null;

		Log.d("restful", "getModelFromProxyMethod: Trying to find what model "
				+ method.getDeclaringClass().getSimpleName() + " belongs to");

		// A action interface should implement a BelongsTo annotation so we can
		// process requests properly.
		BelongsTo annotation = method.getDeclaringClass().getAnnotation(
				BelongsTo.class);

		if (annotation == null) return null;

		Log.d("restful", "getModelFromProxyMethod: Belongs to "
				+ annotation.value().getSimpleName());

		return annotation.value();
	}
	
	
	public static Url getUrlAnnotationFromClass(Class<?> clazz)
	{
		if (clazz == null) return null;
		
		Url urlAnnotation = clazz.getAnnotation(Url.class);
		
		return urlAnnotation;
	}
	
	public static String getResourcePathFromModel(Class<? extends Model> model)
	{
		if (model == null) return null;
		
		Url urlAnnotation = getUrlAnnotationFromClass(model);
		
		if (urlAnnotation != null)
			return urlAnnotation.value();
		
		// If Model hasn't specified a Url, we'll use the Model name as resource path
		
		// TODO: This can't possibly be the way it's supposed to work
		return model.getSimpleName().toLowerCase();
	}
}
