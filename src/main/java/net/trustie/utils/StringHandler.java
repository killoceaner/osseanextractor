package net.trustie.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

public class StringHandler {
	private static String indefiniteArticleA = "a";
	private static String indefiniteArticleAn = "an";
	private static String prepositionAbout = "about";
	private static String prepositionOver = "over";

	/**
	 * 默认形式合并tags
	 * 
	 * @param tags
	 *            标签集合
	 * @return tag,以默认<tag1>,<tag2>,<tag3>...形式返回
	 */
	public static String combineTags(List<String> tags) {
		if (tags != null && tags.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (String str : tags) {
				sb.append('<').append(str.trim()).append('>').append(',');
			}
			return sb.substring(0, sb.length() - 1).toString();
		}
		return null;
	}

	/**
	 * 以用户定义的形式合并tags
	 * 
	 * @param tags
	 *            标签集合
	 * @param varchar
	 *            用户定义
	 * @return tag,以<tag1>varchar<tag2>varchar<tag3>...形式返回
	 */
	public static String combineTags(List<String> tags, char varchar) {
		if (tags != null && tags.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (String str : tags) {
				sb.append('<').append(str.trim()).append('>').append(varchar);
			}
			return sb.substring(0, sb.length() - 1).toString();
		}
		return null;
	}

	/**
	 * 判断字符串列表是否至少有一个为空或者空字符串
	 * 
	 * @param strings
	 *            字符串列表
	 * @return 如果strings中都不为空或者空字符，返回false,否则返回true
	 */
	public static boolean isAtLeastOneBlank(String... strings) {
		for (String str : strings) {
			if (StringUtils.isBlank(str))
				return true;
		}
		return false;
	}

	/**
	 * 判断字符串数组是否全部不为空或者空字符
	 * 
	 * @param string
	 * @return true全部不为空;否则false
	 */
	public static boolean isAllNotBlank(String... strings) {
		for (String str : strings) {
			if (StringUtils.isBlank(str))
				return false;
		}
		return true;
	}

	/**
	 * 获取两个字符串中间的字符串
	 * 
	 * @param string
	 * @param str1
	 * @param str2
	 * @return string
	 */
	public static String findRigthString(String string, String str1, String str2) {
		if (string != null && string.contains(str1) && string.contains(str2)) {
			String result = string.substring(string.indexOf(str1) + 1,
					string.indexOf(str2));
			if (StringUtils.isNotBlank(result))
				return result.trim();
		}
		return null;
	}

	/**
	 * 匹配合适的字符串
	 * 
	 * @param string
	 *            被匹配的字符串
	 * @param regex
	 *            匹配规则
	 * @return 匹配到的字符串
	 */
	public static String matchRightString(String string, String regex) {
		if (StringHandler.isAllNotBlank(string, regex)) {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(string);
			if (matcher.find())
				return matcher.group().trim();
		}
		return null;
	}

	/**
	 * 检查字符数组是否全部可以转换为Integer
	 * 
	 * @param strings
	 * @return true全部可以转化为Integer
	 */
	public static boolean canFormatterInteger(String... strings) {
		try {
			for (String s : strings)
				Integer.parseInt(s.trim());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 过滤掉一些字符串
	 * 
	 * @param string
	 * @param strs
	 *            需要过滤的字符串数组
	 * @return String
	 */
	public static String subString(String string, String... strs) {
		if (string != null && string.length() > 0) {
			for (String str : strs) {
				string = string.replace(str, "");
			}
			return string;
		}
		return null;
	}

	/**
	 * 从部分html抽取字符串
	 * 
	 * @param html
	 * @param str
	 * @return
	 */
	public static String extractHtml(String html, String str) {
		if (html != null && html.length() > 0) {
			Document doc = Jsoup.parse(html);
			if (doc != null) {
				return Xsoup.compile(str).evaluate(doc).get();
			}
		}
		return null;
	}

	/**
	 * 从部分html抽取字符串
	 * 
	 * @param html
	 * @param str
	 * @return 链表形式
	 */
	public static List<String> extractHtmlList(String html, String str) {
		if (html != null && html.length() > 0) {
			Document doc = Jsoup.parse(html);
			if (doc != null) {
				return Xsoup.compile(str).evaluate(doc).list();
			}
		}
		return null;
	}

	/**
	 * 去除字符串前面的字符串
	 * 
	 * @param in
	 * @param header
	 * @return
	 */
	public static String removeHeader(String in, String header) {
		return StringUtils.substringAfter(in, header);
	}

	/**
	 * 移除字符串前面的不定冠词
	 * 
	 * @param in
	 * @return
	 */
	public static String removeIndefiniteArticles(String in) {
		if (in.startsWith(StringHandler.indefiniteArticleAn)) {
			StringHandler.removeHeader(in, StringHandler.indefiniteArticleAn);
			// System.out.println(in);
		} else if (in.startsWith(StringHandler.indefiniteArticleA)) {
			in = StringHandler.removeHeader(in,
					StringHandler.indefiniteArticleA);
		}
		return in;
	}

	/**
	 * 移除字符串前面的介词
	 * 
	 * @param in
	 * @return
	 */
	public static String removePreposition(String in) {
		if (in.startsWith(StringHandler.prepositionAbout)) {
			StringHandler.removeHeader(in, StringHandler.prepositionAbout);
			// System.out.println(in);
		} else if (in.startsWith(StringHandler.prepositionOver)) {
			in = StringHandler.removeHeader(in, StringHandler.prepositionOver);
		}
		return in.trim();
	}

	/**
	 * 去除字符串后面的字符串
	 * 
	 * @param in
	 * @param tail
	 * @return
	 */
	public static String removeTail(String in, String tail) {
		return StringUtils.substringBefore(in, tail);
	}

	/**
	 * 去除字串串两端
	 * 
	 * @param in
	 * @param header
	 * @param tail
	 * @return
	 */
	public static String stringBetween(String in, String header, String tail) {
		return StringUtils.substringBetween(in, header, tail);
	}

	/**
	 * 取得不带复数形式的单位
	 * 
	 * @param in
	 * @return
	 */
	public static String getUnit(String in) {
		char[] chars = in.toCharArray();
		String rs = "";
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] >= '9' || chars[i] <= '0') {
				rs += chars[i];
			}
		}
		if (StringUtils.endsWith(rs, "s")) {
			rs = StringHandler.removeTail(rs, "s");
		}

		return StringUtils.upperCase(rs.trim());
	}

	/**
	 * 去除字符串中空格
	 */

	public static String assemblyOSSEANMap(List<String> keys,
			List<String> values) {
		String rt = "";
		if (keys.size() == values.size()) {
			List<String> tmp = new ArrayList<String>();
			for (int i = 0; i < keys.size(); i++) {
				tmp.add(keys.get(i) + Seperator.SOURCE_SEPERATOR
						+ values.get(i));
			}
			rt = StringUtils.join(tmp, Seperator.OSSEAN_SEPERATOR);
		} else {

		}
		return rt;
	}

	/**
	 * String字符串中是否能匹配到相应的字符串
	 * 
	 * @param string
	 * @param regex
	 * @return
	 */
	public static boolean canMatchRightString(String string, String regex) {
		if (string != null && string.length() > 0 && regex != null
				&& regex.length() > 0) {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(string);
			if (matcher.find())
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param string
	 * @param regexs
	 * @return
	 */
	public static boolean canMatchRightStrings(String string, String... regexs) {
		for (String regex : regexs) {
			if (!canMatchRightString(string, regex))
				return false;
		}
		return true;
	}
	
	/**
	 * 从字符串中提取出整形
	 * 
	 * @param in
	 * @return
	 */
	public static int extractIntFromString(String in) {
		if (in == null) {
			return 0;
		} else {
			char[] chars = in.toCharArray();
			String rs = "";
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] <= '9' && chars[i] >= '0') {
					rs += chars[i];
				}
			}
			if ("".equals(rs)) {
				return 0;
			}
			return Integer.valueOf(rs);
		}
	}

}
