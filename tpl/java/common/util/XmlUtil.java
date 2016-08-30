package {{groupid}}.common.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class XmlUtil {

	public static String toXML(Object obj) {
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);// 是否省略xm头声明信息
			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromXML(String xml, Class<T> valueType) {
		try {
			JAXBContext context = JAXBContext.newInstance(valueType);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * xml字符串转换成bean对象
	 * 
	 * @param xmlStr
	 *            xml字符串
	 * @param clazz
	 *            待转换的class
	 * @return 转换后的对象
	 */
	public static Object xmlStrToBean(String xmlStr, Class clazz) {
		Object obj = null;
		try {
			// 将xml格式的数据转换成Map对象
			Map<String, Object> map = xmlStrToMap(xmlStr);
			// 将map对象的数据转换成Bean对象
			obj = mapToBean(map, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 将xml格式的字符串转换成Map对象
	 * 
	 * @param xmlStr
	 *            xml格式的字符串
	 * @return Map对象
	 * @throws Exception
	 *             异常
	 */
	public static Map<String, Object> xmlStrToMap(String xmlStr)
			throws Exception {
		if (StringUtils.isEmpty(xmlStr)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 将xml格式的字符串转换成Document对象
		Document doc = DocumentHelper.parseText(xmlStr);
		// 获取根节点
		Element root = doc.getRootElement();
		// 获取根节点下的所有元素
		List children = root.elements();
		// 循环所有子元素
		if (children != null && children.size() > 0) {
			for (int i = 0; i < children.size(); i++) {
				Element child = (Element) children.get(i);
				map.put(child.getName(), child.getTextTrim());
			}
		}
		return map;
	}

	/**
	 * 将Map对象通过反射机制转换成Bean对象
	 * 
	 * @param map
	 *            存放数据的map对象
	 * @param clazz
	 *            待转换的class
	 * @return 转换后的Bean对象
	 * @throws Exception
	 *             异常
	 */
	public static Object mapToBean(Map<String, Object> map, Class clazz)
			throws Exception {
		Object obj = clazz.newInstance();
		if (map != null && map.size() > 0) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String propertyName = entry.getKey();
				Object value = entry.getValue();
				String setMethodName = "set"
						+ propertyName.substring(0, 1).toUpperCase()
						+ propertyName.substring(1);
				Field field = getClassField(clazz, propertyName);
				Class fieldTypeClass = field.getType();
				value = convertValType(value, fieldTypeClass);
				clazz.getMethod(setMethodName, field.getType()).invoke(obj,
						value);
			}
		}
		return obj;
	}

	/**
	 * 将Object类型的值，转换成bean对象属性里对应的类型值
	 * 
	 * @param value
	 *            Object对象值
	 * @param fieldTypeClass
	 *            属性的类型
	 * @return 转换后的值
	 */
	private static Object convertValType(Object value, Class fieldTypeClass) {
		Object retVal = null;
		if (Long.class.getName().equals(fieldTypeClass.getName())
				|| long.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Long.parseLong(value.toString());
		} else if (Integer.class.getName().equals(fieldTypeClass.getName())
				|| int.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Integer.parseInt(value.toString());
		} else if (Float.class.getName().equals(fieldTypeClass.getName())
				|| float.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Float.parseFloat(value.toString());
		} else if (Double.class.getName().equals(fieldTypeClass.getName())
				|| double.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Double.parseDouble(value.toString());
		} else {
			retVal = value;
		}
		return retVal;
	}

	/**
	 * 获取指定字段名称查找在class中的对应的Field对象(包括查找父类)
	 * 
	 * @param clazz
	 *            指定的class
	 * @param fieldName
	 *            字段名称
	 * @return Field对象
	 */
	private static Field getClassField(Class clazz, String fieldName) {
		if (Object.class.getName().equals(clazz.getName())) {
			return null;
		}
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		Class superClass = clazz.getSuperclass();
		if (superClass != null) {// 简单的递归一下
			return getClassField(superClass, fieldName);
		}
		return null;
	}

	// xml字符串转换对象
	/*
	 * public static <T> T XMLtoObject(String xml, Class<T> valueType) { try {
	 * 
	 * JAXBContext ctx = JAXBContext.newInstance(valueType.getClasses());
	 * Unmarshaller um = ctx.createUnmarshaller(); T obj = (T)um.unmarshal(new
	 * StringReader(xml)); return obj; } catch (Exception e) { throw new
	 * RuntimeException(e); } }
	 */

	/**
	 * map to xml xml <node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node>
	 * 
	 * @param map
	 * @return
	 */
	public static String maptoXml(Map map) {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement("xml");
		for (Object obj : map.keySet()) {
			Element keyElement = nodeElement.addElement("key");
			keyElement.addAttribute("label", String.valueOf(obj));
			keyElement.setText(String.valueOf(map.get(obj)));
		}
		return doc2String(document);
	}

	/**
	 * Converter Map<Object, Object> instance to xml string. Note: currently, we
	 * aren't consider more about some collection types, such as array,list,
	 *
	 * @param dataMap
	 *            the data map
	 *
	 * @return the string
	 */
	public static String converter(SortedMap<String, String> dataMap) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<xml>\n");
		Set<String> objSet = dataMap.keySet();
		for (Object key : objSet) {
			if (key == null) {
				continue;
			}
			strBuilder.append("<").append(key.toString()).append(">");
			Object value = dataMap.get(key);
			strBuilder.append(coverter(value));
			strBuilder.append("</").append(key.toString()).append(">\n");
		}
		strBuilder.append("</xml>");
		return strBuilder.toString();
	}

	/**
	 * Coverter.
	 *
	 * @param object
	 *            the object
	 * @return the string
	 */
	@SuppressWarnings("unused")
	public static String coverter(Object object) {
		if (object instanceof Object[]) {
			return coverter((Object[]) object);
		}
		if (object instanceof Collection) {
			return coverter((Collection<?>) object);
		}
		StringBuilder strBuilder = new StringBuilder();
		if (isObject(object)) {
			Class<? extends Object> clz = object.getClass();
			Field[] fields = clz.getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);
				if (field == null) {
					continue;
				}
				String fieldName = field.getName();
				Object value = null;
				try {
					value = field.get(object);
				} catch (IllegalArgumentException e) {
					continue;
				} catch (IllegalAccessException e) {
					continue;
				}
				strBuilder.append("<").append(fieldName)
						.append(" className=\"")
						.append(value.getClass().getName()).append("\">\n");
				strBuilder.append(value.toString() + "\n");
				strBuilder.append("</").append(fieldName).append(">\n");
			}
		} else if (object == null) {
			strBuilder.append("null");
		} else {
			strBuilder.append(object.toString());
		}
		return strBuilder.toString();
	}

	/**
	 * Checks if is object.
	 *
	 * @param obj
	 *            the obj
	 *
	 * @return true, if is object
	 */
	private static boolean isObject(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			return false;
		}
		if (obj instanceof Integer) {
			return false;
		}
		if (obj instanceof Double) {
			return false;
		}
		if (obj instanceof Float) {
			return false;
		}
		if (obj instanceof Byte) {
			return false;
		}
		if (obj instanceof Long) {
			return false;
		}
		if (obj instanceof Character) {
			return false;
		}
		if (obj instanceof Short) {
			return false;
		}
		if (obj instanceof Boolean) {
			return false;
		}
		return true;
	}

	/**
	 * list to xml xml <nodes><node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node><node><key
	 * label="key1">value1</key><key
	 * label="key2">value2</key>......</node></nodes>
	 * 
	 * @param list
	 * @return
	 */
	public static String listtoXml(List list) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element nodesElement = document.addElement("nodes");
		int i = 0;
		for (Object o : list) {
			Element nodeElement = nodesElement.addElement("node");
			if (o instanceof Map) {
				for (Object obj : ((Map) o).keySet()) {
					Element keyElement = nodeElement.addElement("key");
					keyElement.addAttribute("label", String.valueOf(obj));
					keyElement.setText(String.valueOf(((Map) o).get(obj)));
				}
			} else {
				Element keyElement = nodeElement.addElement("key");
				keyElement.addAttribute("label", String.valueOf(i));
				keyElement.setText(String.valueOf(o));
			}
			i++;
		}
		return doc2String(document);
	}

	/**
	 * json to xml {"node":{"key":{"@label":"key1","#text":"value1"}}} conver
	 * <o><node class="object"><key class="object"
	 * label="key1">value1</key></node></o>
	 * 
	 * @param json
	 * @return
	 */
	public static String jsontoXml(String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();  
			Map jsonObject = JSON.parseObject(json,Map.class);
			 
			return maptoXml(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to map xml <node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node>
	 * 
	 * @param xml
	 * @return
	 */
	public static Map<String, String> xmltoMap(String xml) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Document document = DocumentHelper.parseText(xml);
			Element nodeElement = document.getRootElement();
			List node = nodeElement.elements();
			for (Iterator it = node.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				map.put(elm.attributeValue("label"), elm.getText());
				elm = null;
			}
			node = null;
			nodeElement = null;
			document = null;
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to list xml <nodes><node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node><node><key
	 * label="key1">value1</key><key
	 * label="key2">value2</key>......</node></nodes>
	 * 
	 * @param xml
	 * @return
	 */
	public static List xmltoList(String xml) {
		try {
			List<Map> list = new ArrayList<Map>();
			Document document = DocumentHelper.parseText(xml);
			Element nodesElement = document.getRootElement();
			List nodes = nodesElement.elements();
			for (Iterator its = nodes.iterator(); its.hasNext();) {
				Element nodeElement = (Element) its.next();
				Map map = xmltoMap(nodeElement.asXML());
				list.add(map);
				map = null;
			}
			nodes = null;
			nodesElement = null;
			document = null;
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to json <node><key label="key1">value1</key></node> 转化为
	 * {"key":{"@label":"key1","#text":"value1"}}
	 * 
	 * @param xml
	 * @return
	 */
	public static String xmltoJson(String xml) {
		
		
		try {
			return JSON.toJSONString(xmlStrToMap(xml));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param document
	 * @return
	 */
	public static String doc2String(Document document) {
		String s = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用UTF-8编码
			OutputFormat format = new OutputFormat("   ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

}
