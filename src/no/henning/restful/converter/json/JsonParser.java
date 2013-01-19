package no.henning.restful.converter.json;

import java.lang.reflect.Field;
import java.util.List;

import no.henning.restful.converter.json.utils.JsonParserUtil;

import org.json.JSONObject;

public class JsonParser
{
	public static <T> T parse(JSONObject jsonObject, Class<?> type)
	{
		if (jsonObject == null) return null;
		
		try
		{
			// Create a new instance of this type
			T returnObj = (T) type.
			
			// Lets populate some fields!
			List<Field> validFields = JsonParserUtil.getJsonFieldsToPopulate(returnObj.getClass());
			
			for (Field field : validFields)
			{
				field.setAccessible(true);
				
				String jsonFieldName = JsonParserUtil.getJsonFieldNameFromClassField(field);
				Class<?> fieldClass = field.getClass();
				
				Object jsonValue = jsonObject.get(jsonFieldName);
				
				if (fieldClass.isInstance(jsonValue))
				{
					field.set(returnObj, fieldClass.cast(jsonValue));
				}
				
			}
			
			return returnObj;
		}
		catch (Exception ex)
		{
			
		}
		
		return null;
	}
}
