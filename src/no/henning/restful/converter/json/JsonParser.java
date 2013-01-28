package no.henning.restful.converter.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import no.henning.restful.callback.Callback;
import no.henning.restful.converter.json.utils.JsonParserUtil;
import no.henning.restful.utils.CallbackHelper;
import no.henning.restful.utils.GenericHelper;
import no.henning.restful.utils.ProxyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonParser
{
	public static <T> Collection<T> parseCollection(JSONArray jsonArray,
			Class<T> type)
	{
		Collection<T> collection = new ArrayList<T>();

		for (int i = 0; i < jsonArray.length(); i++)
		{
			collection.add(parse(jsonArray.optJSONObject(i), type));
		}

		return collection;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] parseArray(JSONArray jsonArray, Class<T> type)
	{
		T[] array = (T[]) Array.newInstance(type, jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++)
		{
			array[i] = parse(jsonArray.optJSONObject(i), type);
		}
		return array;
	}

	public static <T> T parse(JSONObject jsonObject, Class<T> type)
	{
		if (jsonObject == null) return null;

		try
		{
			Log.d("restful", "Json parsing: Type is " + type);
			// Create a new instance of this type
			T returnObj = (T) type.newInstance();

			// Lets populate some fields!
			List<Field> validFields = JsonParserUtil
					.getJsonFieldsToPopulate(returnObj.getClass());

			for (Field field : validFields)
			{
				field.setAccessible(true);

				String jsonFieldName = JsonParserUtil
						.getJsonFieldNameFromClassField(field);

				if (jsonObject.isNull(jsonFieldName)) continue;

				Class<?> fieldClass = field.getType();

				Object jsonValue = jsonObject.get(jsonFieldName);

				Log.d("restful",
						"JsonParser: Field Class: " + fieldClass.getName());
				Log.d("restful",
						"JsonParser: Json Value Class: " + jsonValue.getClass());

				if (JsonParserUtil.isJsonArray(jsonValue))
				{
					JsonParserUtil.castJsonArrayValue(field, returnObj,
							jsonValue);
				}
				else
				{
					JsonParserUtil.castJsonValue(field, returnObj, jsonValue);
				}
			}

			return returnObj;
		}
		catch (Exception ex)
		{
			Log.d("restful", "Json parsing: " + ex.getMessage());
		}

		return null;
	}

	public static <T> T parse(String json, Class<T> type)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(json);

			return parse(jsonObject, type);
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T parse(JSONObject jsonObject, Callback<?> callback)
	{
		Type callbackType = CallbackHelper.getCallbackType(callback);

		return (T) parse(jsonObject, (Class<?>) callbackType);
	}

	@SuppressWarnings("unchecked")
	public static <T> T parse(JSONArray jsonArray, Callback<?> callback)
	{
		Type callbackType = CallbackHelper.getCallbackType(callback);
		
		if (GenericHelper.isCollection(callbackType))
		{
			Type type = GenericHelper.getUnderlyingGenericType((ParameterizedType) callbackType);
			
			return (T) parseCollection(jsonArray, (Class<?>) type);
		}
		else if (GenericHelper.isArray(callbackType))
		{
			Type type = GenericHelper.getUnderlyingGenericArrayType((GenericArrayType)callbackType);
			
			return (T) parseArray(jsonArray, (Class<?>) type);
		}
		
		return null;
	}
	public static <T> T parse(String json, Callback<?> callback)
	{
		try
		{
			if (json.startsWith("["))
			{
				JSONArray jsonArray = new JSONArray(json);
				
				return parse(jsonArray, callback);

			}
			else
			{
				JSONObject jsonObject = new JSONObject(json);

				return parse(jsonObject, callback);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
