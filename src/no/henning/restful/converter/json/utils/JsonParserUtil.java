package no.henning.restful.converter.json.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import no.henning.restful.model.annotation.Ignore;
import no.henning.restful.model.annotation.Named;

public class JsonParserUtil
{
	public static String getJsonFieldNameFromClassField(Field field)
	{
		if (field == null) return null;
		
		if (!field.isAnnotationPresent(Named.class))
			return field.getName();
		
		Named annotation = field.getAnnotation(Named.class);
		
		return annotation.value();
	}
	
	public static List<Field> getJsonFieldsToPopulate(Class<?> type)
	{
		List<Field> listOfFields = new ArrayList<Field>();
		
		Field[] fields = type.getDeclaredFields();
		
		for (Field field : fields)
		{
			if (field.isAnnotationPresent(Ignore.class))
				continue;
			
			listOfFields.add(field);
		}
		
		return listOfFields;
	}
}
