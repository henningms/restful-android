package no.henning.restful.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
	
	public static Class<?> getUnderlyingType(Field field)
	{
		Class<?> fieldType = field.getType();
		
		if (isArray(fieldType))
			return fieldType.getComponentType();
		
		if (isCollection(fieldType))
			return getUnderlyingGenericType((ParameterizedType) field.getGenericType());
		
		return fieldType;
	}

	public static Class<?> getUnderlyingArrayType(Class<?> type)
	{
		return type.getComponentType();
	}
	
	public static Class<?> getUnderlyingGenericType(ParameterizedType type)
	{
		return (Class<?>) type.getActualTypeArguments()[0];
	}
	
	public static boolean isCollection(Class<?> type)
	{
		if (type == null) return false;
		if (Collection.class.isAssignableFrom(type)) return true;
		if (List.class.isAssignableFrom(type)) return true;
		
		// Map ain't no ordinary collection, shame on thee!
		//if (Map.class.isAssignableFrom(type)) return true;
		
		return false;	
	}
	
	public static boolean isArray(Class<?> type)
	{
		if (type == null) return false;
		if (type.isArray()) return true;
		
		return false;
	}
}
