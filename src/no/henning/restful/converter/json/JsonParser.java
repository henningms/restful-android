package no.henning.restful.converter.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import no.henning.restful.converter.json.utils.JsonParserUtil;

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
}
