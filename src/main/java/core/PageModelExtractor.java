package core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Formatter;
import us.codecraft.webmagic.model.formatter.BasicTypeFormatter;
import us.codecraft.webmagic.model.formatter.ObjectFormatter;
import us.codecraft.webmagic.model.formatter.ObjectFormatters;
import us.codecraft.webmagic.selector.Selector;
import us.codecraft.webmagic.selector.XpathSelector;
import us.codecraft.webmagic.utils.ClassUtils;
import us.codecraft.webmagic.utils.ExtractorUtils;

public class PageModelExtractor {
	private Class<?> clazz;
	private Extractor objectExtractor;
	private List<FieldExtractor> fieldExtractors;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static PageModelExtractor create(Class<?> clazz) {
		PageModelExtractor pageModelExtractor = new PageModelExtractor();
		pageModelExtractor.init(clazz);
		return pageModelExtractor;

	}

	private void init(Class<?> clazz) {
		this.clazz = clazz;
		initClassExtractors();
		fieldExtractors = new ArrayList<FieldExtractor>();
		for (Field field : ClassUtils.getFieldsIncludeSuperClass(clazz)) {
			field.setAccessible(true);
			FieldExtractor fieldExtractor = getAnnotationExtractBy(clazz, field);
			if (fieldExtractor != null) {
				checkFormat(field, fieldExtractor);
				fieldExtractors.add(fieldExtractor);
			}
		}
	}

	private void checkFormat(Field field, FieldExtractor fieldExtractor) {
		// TODO Auto-generated method stub
		Formatter formatter = field.getAnnotation(Formatter.class);
		if (formatter != null
				&& !formatter.formatter().equals(ObjectFormatter.class)) {
			if (formatter != null) {
				if (!formatter.formatter().equals(ObjectFormatter.class)) {
					ObjectFormatter<?> objectFormatter = initFormatter(formatter
							.formatter());
					objectFormatter.initParam(formatter.value());
					fieldExtractor.setObjectFormatter(objectFormatter);
					return;
				}
			}
		}
		if (!fieldExtractor.isMulti()
				&& !String.class.isAssignableFrom(field.getType())) {
			Class<?> fieldClazz = BasicTypeFormatter.detectBasicClass(field
					.getType());
			ObjectFormatter<?> objectFormatter = getObjectFormatter(field,
					fieldClazz, formatter);
			if (objectFormatter == null) {
				throw new IllegalStateException(
						"Can't find formatter for field " + field.getName()
								+ " of type " + fieldClazz);
			} else {
				fieldExtractor.setObjectFormatter(objectFormatter);
			}
		} else if (fieldExtractor.isMulti()) {
			if (!List.class.isAssignableFrom(field.getType())) {
				throw new IllegalStateException("Field " + field.getName()
						+ " must be list");
			}
			if (formatter != null) {
				if (!formatter.subClazz().equals(Void.class)) {
					ObjectFormatter<?> objectFormatter = getObjectFormatter(
							field, formatter.subClazz(), formatter);
					if (objectFormatter == null) {
						throw new IllegalStateException(
								"Can't find formatter for field "
										+ field.getName() + " of type "
										+ formatter.subClazz());
					} else {
						fieldExtractor.setObjectFormatter(objectFormatter);
					}
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private ObjectFormatter<?> initFormatter(
			Class<? extends ObjectFormatter> formatter) {
		// TODO Auto-generated method stub
		try {
			return formatter.newInstance();
		} catch (InstantiationException e) {
			logger.error("init ObjectFormatter fail", e);
		} catch (IllegalAccessException e) {
			logger.error("init ObjectFormatter fail", e);
		}
		return null;
	}

	private ObjectFormatter<?> getObjectFormatter(Field field,
			Class<?> subClazz, Formatter formatter) {
		// TODO Auto-generated method stub
		return initFormatter(ObjectFormatters.get(subClazz));
	}

	@SuppressWarnings("deprecation")
	private FieldExtractor getAnnotationExtractBy(Class<?> clazz, Field field) {
		FieldExtractor fieldExtractor = null;
		ExtractBy extractBy = field.getAnnotation(ExtractBy.class);
		if (extractBy != null) {
			Selector selector = ExtractorUtils.getSelector(extractBy);
			fieldExtractor = new FieldExtractor(
					field,
					selector,
					extractBy.source() == ExtractBy.Source.RawHtml ? FieldExtractor.Source.RawHtml
							: FieldExtractor.Source.Html, extractBy.notNull(),
					extractBy.multi()
							|| List.class.isAssignableFrom(field.getType()));
			Method setterMethod = getSetterMethod(clazz, field);
			if (setterMethod != null) {
				fieldExtractor.setSetterMethod(setterMethod);
			}
		}
		return fieldExtractor;
	}

	private Method getSetterMethod(Class<?> clazz, Field field) {
		// TODO Auto-generated method stub
		String name = "set" + StringUtils.capitalize(field.getName());
		try {
			Method declaredMethod = clazz.getDeclaredMethod(name,
					field.getType());
			declaredMethod.setAccessible(true);
			return declaredMethod;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	private void initClassExtractors() {
		// TODO Auto-generated method stub
		Annotation annotation = clazz.getAnnotation(ExtractBy.class);
		if (annotation != null) {
			ExtractBy extractBy = (ExtractBy) annotation;
			objectExtractor = new Extractor(
					new XpathSelector(extractBy.value()),
					Extractor.Source.Html, extractBy.notNull(),
					extractBy.multi());
		}
	}

	public Object process(Page page) {
		if (objectExtractor == null) {
			return processSingle(page, null, true);
		} else {
			if (objectExtractor.multi) {
				List<Object> os = new ArrayList<Object>();
				List<String> list = objectExtractor.getSelector().selectList(
						page.getRawText());
				for (String s : list) {
					Object o = processSingle(page, s, false);
					if (o != null) {
						os.add(o);
					}
				}
				return os;
			} else {
				String select = objectExtractor.getSelector().select(
						page.getRawText());
				Object o = processSingle(page, select, false);
				return o;
			}
		}
	}

	private Object processSingle(Page page, String html, boolean isRaw) {
		// TODO Auto-generated method stub
		Object o = null;
		try {
			o = clazz.newInstance();			
			for (FieldExtractor fieldExtractor : fieldExtractors) {
				if (fieldExtractor.isMulti()) {
					List<String> value;
					switch (fieldExtractor.getSource()) {
					case RawHtml:
						value = page.getHtml().selectDocumentForList(
								fieldExtractor.getSelector());
						break;
					case Html:
						if (isRaw) {
							value = page.getHtml().selectDocumentForList(
									fieldExtractor.getSelector());
						} else {
							value = fieldExtractor.getSelector().selectList(
									html);
						}
						break;
					case Url:
						value = fieldExtractor.getSelector().selectList(
								page.getUrl().toString());
						break;
					default:
						value = fieldExtractor.getSelector().selectList(html);
					}
					if ((value == null || value.size() == 0)
							&& fieldExtractor.isNotNull()) {
						return null;
					}
					if (fieldExtractor.getObjectFormatter() != null) {
						List<Object> converted = convert(value,
								fieldExtractor.getObjectFormatter());
						setField(o, fieldExtractor, converted);
					} else {
						setField(o, fieldExtractor, value);
					}
				} else {
					String value;
					switch (fieldExtractor.getSource()) {
					case RawHtml:
						value = page.getHtml().selectDocument(
								fieldExtractor.getSelector());
						break;
					case Html:
						if (isRaw) {
							value = page.getHtml().selectDocument(
									fieldExtractor.getSelector());
						} else {
							value = fieldExtractor.getSelector().select(html);
						}
						break;
					case Url:
						value = fieldExtractor.getSelector().select(
								page.getUrl().toString());
						break;
					default:
						value = fieldExtractor.getSelector().select(html);
					}
					if (value == null && fieldExtractor.isNotNull()) {
						return null;
					}
					if (fieldExtractor.getObjectFormatter() != null) {

						Object converted = convert(value,
								fieldExtractor.getObjectFormatter());
						if (converted == null && fieldExtractor.isNotNull()) {
							return null;
						}
						setField(o, fieldExtractor, converted);
					} else {
						setField(o, fieldExtractor, value);
					}
				}
			}
			if (AfterExtractor.class.isAssignableFrom(clazz)) {
				((AfterExtractor) o).afterProcess(page);
			}
			if (ValidateExtractor.class.isAssignableFrom(clazz)) {
				((ValidateExtractor) o).validate(page);				
			}
		} catch (InstantiationException e) {
			logger.error("extract fail", e);
		} catch (IllegalAccessException e) {
			logger.error("extract fail", e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	private List<Object> convert(List<String> values,
			ObjectFormatter<?> objectFormatter) {
		List<Object> objects = new ArrayList<Object>();
		for (String value : values) {
			Object converted = convert(value, objectFormatter);
			if (converted != null) {
				objects.add(converted);
			}
		}
		return objects;
	}

	private Object convert(String value, ObjectFormatter<?> objectFormatter) {
		// TODO Auto-generated method stub
		try {
			Object format = objectFormatter.format(value);
			logger.debug("String {} is converted to {}", value, format);
			return format;
		} catch (Exception e) {
			logger.error("convert " + value + " to " + objectFormatter.clazz()
					+ " error!", e);
		}
		return null;
	}

	private void setField(Object o, FieldExtractor fieldExtractor, Object value)
			throws IllegalAccessException, InvocationTargetException {
		if (value == null) {
			return;
		}
		if (fieldExtractor.getSetterMethod() != null) {
			fieldExtractor.getSetterMethod().invoke(o, value);
		}
		fieldExtractor.getField().set(o, value);
	}

	Class<?> getClazz() {
		return clazz;
	}

}
