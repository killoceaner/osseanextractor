package core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.utils.ClassUtils;
public class ConsolePipeline implements Pipeline {

	@SuppressWarnings("rawtypes")
	public void process(ResultItems resultItems, Task task) {
		Set<String> keySet = resultItems.getAll().keySet();
		for (String key : keySet) {
			Object object = resultItems.get(key);
			Class clazz = object.getClass();			
			if (AfterExtractor.class.isAssignableFrom(clazz)) {				
				for (Field field : ClassUtils.getFieldsIncludeSuperClass(clazz)) {
					field.setAccessible(true);
					prinfField(field, clazz, object);
				}
			} else
				System.out.println(key + ":\t" + resultItems.get(key));
			System.out.println("*****************************");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void prinfField(Field field, Class clazz, Object object) {
		String fieldName = field.getName();
		String outPut = fieldName + ":\t";
		fieldName = "get" + StringUtils.capitalize(field.getName());
		try {
			Method method = clazz.getMethod(fieldName);
			System.out.println(outPut + method.invoke(object));

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
