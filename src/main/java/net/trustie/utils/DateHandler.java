package net.trustie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateHandler {
	private static Logger logger = LoggerFactory.getLogger("DateHandler");
	private static String dateFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取抽取时间
	 * 
	 * @return 以yyy-MM-dd HH:mm:ss形式的字符串返回
	 */
	public static String getExtractTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(new Date());
	}

	/**
	 * 判断Strings是否可以转换为yyyy-mm-dd HH:MM:SS形式的Date型
	 * 
	 * @param strings
	 * @return
	 */
	public static boolean canFormatToDate(String... strings) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		for (String s : strings) {
			if (StringUtils.isBlank(s))
				return false;
			try {
				sdf.parse(s);
			} catch (ParseException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 字符串形式的date上加上一个时间（秒）
	 * 
	 * @param str
	 * @param second
	 * @return 字符串
	 */
	public static String addTimeToDate(String str, long second) {
		if (canFormatToDate(str)) {
			return addTimeToDate(stringToDate(str), second);
		}
		return null;
	}

	/**
	 * 在date类型时间上加上一个时间（秒）
	 * 
	 * @param dateTime
	 * @param second
	 * @return yyyy-mm-dd HH:mm:ss
	 */
	public static String addTimeToDate(Date date, long second) {
		if (date == null)
			return null;
		return dateToString(timeStampToDate(dateToTimeStamp(date) + second
				* 1000));
	}

	/**
	 * 自动处理各种类型的日期类型字符串，匹配yyyy-mm-dd
	 * hh:mm:ss类型日期;补全yyyy-mm-dd类型的日期;补全补全yyyy-mm-dd hh:mm类型的日期;转化x(年 or 月 or
	 * 天)前类型的日期，同时支持英文。
	 * 
	 * @param string
	 * @return 日期以字符串形式返回
	 */
	public static String formatAllTypeDate(String string, Date date) {
		if (StringUtils.isBlank(string))
			return null;

		if (canFormatToDate(string))
			return string;

		if (date != null) {
			string = handlerDefaultDate(string, date);
			if (canFormatToDate(string))
				return string;
		}

		string = replaceChinesDate(string);
		if (canFormatToDate(string))
			return string;

		string = replacePointDate(string);
		if (canFormatToDate(string))
			return string;

		string = standardForDate(string);
		if (canFormatToDate(string)) {
			return string;
		}

		return string;

	}

	/**
	 * 转化x(年 or 月 or 天)前类型的日期，同时支持英文，日后还需扩展
	 * 
	 * @param string
	 * @return
	 */
	private static String handlerDefaultDate(String string, Date date) {
		if (StringHandler.canMatchRightStrings(string, "\\d+", "年", "前")
				|| StringHandler.canMatchRightStrings(string, "\\d+", "year")) {
			int years = Integer.parseInt(StringHandler.matchRightString(string,
					"\\d+"));
			return addTimeToDate(date, -years * 365 * 24 * 60 * 60);
		}

		else if (StringHandler.canMatchRightStrings(string, "半" + "年", "前")
				|| StringHandler.canMatchRightStrings(string, "half", "year"))
			return addTimeToDate(date, -183 * 24 * 60 * 60);

		else if (StringHandler.canMatchRightStrings(string, "\\d+", "月", "前")
				|| StringHandler.canMatchRightStrings(string, "\\d+", "mon")) {
			int months = Integer.parseInt(StringHandler.matchRightString(
					string, "\\d+"));
			return addTimeToDate(date, -months * 30 * 24 * 60 * 60);
		}

		else if (StringHandler.canMatchRightStrings(string, "半", "月", "前")
				|| StringHandler.canMatchRightStrings(string, "half ", " mon"))
			return addTimeToDate(date, -15 * 24 * 60 * 60);

		else if (StringHandler.canMatchRightStrings(string, "\\d+", "周", "前")
				|| StringHandler.canMatchRightStrings(string, "\\d+ ", " week")) {
			int weeks = Integer.parseInt(StringHandler.matchRightString(string,
					"\\d+"));
			return addTimeToDate(date, -weeks * 7 * 24 * 60 * 60);
		}

		else if (StringHandler.canMatchRightStrings(string, "\\d+", "天", "前")
				|| StringHandler.canMatchRightStrings(string, "\\d+", "day")) {
			int days = Integer.parseInt(StringHandler.matchRightString(string,
					"\\d+"));
			return addTimeToDate(date, -days * 24 * 60 * 60);
		}

		else if (StringHandler.canMatchRightStrings(string, "\\d+", "小时", "前")
				|| StringHandler.canMatchRightStrings(string, "\\d+", "hour")) {
			int hours = Integer.parseInt(StringHandler.matchRightString(string,
					"\\d+"));
			return addTimeToDate(date, -hours * 60 * 60);
		}

		else if (StringHandler.canMatchRightStrings(string, "半", "小时", "前")
				|| StringHandler.canMatchRightStrings(string, "half ", " hour"))
			return addTimeToDate(date, -30 * 60);

		else if (StringHandler.canMatchRightStrings(string, "\\d+", "分钟", "前")
				|| StringHandler.canMatchRightStrings(string, "\\d+", "min")) {
			int minutes = Integer.parseInt(StringHandler.matchRightString(
					string, "\\d+"));
			return addTimeToDate(date, -minutes * 60);
		}

		else if (StringHandler.canMatchRightStrings(string, "\\d+", "秒", "前")
				|| StringHandler.canMatchRightStrings(string, "\\d+", "sec")) {
			int seconds = Integer.parseInt(StringHandler.matchRightString(
					string, "\\d+"));
			return addTimeToDate(date, -seconds);
		}

		else
			return string;
	}

	/**
	 * 标准化年x月x日这种新型的日期
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceChinesDate(String str) {
		if (StringUtils.isNotBlank(str))
			return str.replace("年", "-").replace("月", "-").replace("日", "")
					.replace("时", ":").replace("分", ":").replace("秒", "")
					.trim();
		return null;
	}

	public static String replacePointDate(String str) {
		if (StringUtils.isNotBlank(str))
			return str.replace(".", "-").replace(".", "-").replace(".", "")
					.trim();
		return null;
	}

	/**
	 * 标准化date,形式（yyyy-mm-dd hh:mm:ss）
	 * 
	 * @param date
	 * @return
	 */
	public static String standardForDate(String date) {
		if (StringUtils.isBlank(date))
			return null;

		String[] times = date.split("-");
		if (times.length < 3)
			return date;

		times[0] = StringHandler.matchRightString(times[0], "[0-9]{4}");
		times[1] = handlerForData(times[1], 0, 13);
		times[2] = handlerTime(times[2]);

		if (times[0].length() != 4
				|| !StringHandler.canFormatterInteger(times[0])
				|| StringHandler.isAtLeastOneBlank(times[1], times[2]))
			return null;

		return new StringBuffer().append(times[0]).append("-").append(times[1])
				.append("-").append(times[2]).toString();
	}

	private static String handlerTime(String str) {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(str)) {
			String[] strings = str.split(":");
			switch (strings.length) {
			case 1:
				strings[0] = handlerDayAndHour(strings[0]);
				if (StringHandler.isAtLeastOneBlank(strings))
					return null;
				sb.append(strings[0]).append(":00:00");
				break;
			case 2:
				strings[0] = handlerDayAndHour(strings[0]);
				strings[1] = handlerForData(strings[1], -1, 60);
				if (StringHandler.isAtLeastOneBlank(strings))
					return null;
				sb.append(handlerDayAndHour(strings[0])).append(":")
						.append(strings[1]).append(":00");
				break;
			case 3:
				strings[0] = handlerDayAndHour(strings[0]);
				strings[1] = handlerForData(strings[1], -1, 60);
				strings[2] = handlerForData(
						StringHandler.matchRightString(strings[2], "\\d+"), -1,
						60);
				if (StringHandler.isAtLeastOneBlank(strings))
					return null;
				sb.append(handlerDayAndHour(strings[0])).append(":")
						.append(strings[1]).append(":").append(strings[2]);
				break;
			default:
				return null;
			}
			return sb.toString();
		}
		return null;
	}

	private static String handlerDayAndHour(String str) {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(str)) {
			str = str.trim();
			String[] strs = str.split("[' ']{1,}");
			switch (strs.length) {
			case 1:
				strs[0] = handlerForData(strs[0], 0, 32);
				if (StringHandler.isAtLeastOneBlank(strs))
					return null;
				sb.append(strs[0]).append(" 00");
				break;
			case 2:
				strs[0] = handlerForData(strs[0], 0, 32);
				strs[1] = handlerForData(strs[1], -1, 24);
				if (StringHandler.isAtLeastOneBlank(strs))
					return null;
				sb.append(strs[0]).append(" ").append(strs[1]);
				break;
			default:
				return null;
			}
			return sb.toString();
		}
		return null;
	}

	private static String handlerForData(String str, int min, int max) {
		if (StringUtils.isNotBlank(str)
				&& StringHandler.canFormatterInteger(str.trim())
				&& Integer.parseInt(str.trim()) > min
				&& Integer.parseInt(str.trim()) < max) {
			if (str.trim().length() == 1)
				return "0" + str.trim();
			return str.trim();
		}
		return null;
	}

	/**
	 * Date转化为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		if (date == null)
			return null;
		return new SimpleDateFormat(dateFormat).format(date);
	}

	/**
	 * 字符串转化为Date
	 * 
	 * @param str
	 * @return
	 */
	public static Date stringToDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		if (StringUtils.isBlank(str))
			return null;
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			logger.error("String [" + str + "] ## Can Not Format To Date", e);
			return null;
		}

	}

	/**
	 * Date型转化为时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static long dateToTimeStamp(Date date) {
		if (date != null)
			return date.getTime();
		else {
			logger.error("Date Is Null Will Return 0");
			return 0;
		}
	}

	/**
	 * 时间戳转化为Date
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static Date timeStampToDate(long timeStamp) {
		return new Date(timeStamp);
	}


	/**
	 * 格式化时间戳  dd MM yyyy hh:mm
	 * 
	 */
	public static String handleTime(String str) {
		String tmp = str.trim();
		String[] tmpList = tmp.split(" ");
		if(tmpList.length>0){
		String date = tmpList[0];
		String month = tmpList[1];
		String year = tmpList[2];
		String time = tmpList[3];
		switch (month) {
		case "Jan": {
			month = "01";
			break;
		}
		case "Feb": {
			month = "02";
			break;
		}
		case "Mar": {
			month = "03";
			break;
		}
		case "Apr": {
			month = "04";
			break;
		}
		case "May": {
			month = "05";
			break;
		}
		case "Jun": {
			month = "06";
			break;
		}
		case "Jul": {
			month = "07";
			break;
		}
		case "Aug": {
			month = "08";
			break;
		}
		case "Sep": {
			month = "09";
			break;
		}
		case "Oct": {
			month = "10";
			break;
		}
		case "Nov": {
			month = "11";
			break;
		}
		case "Dec": {
			month = "12";
			break;
		}
		default: {
			month = "00";
			break;
		}
		}
		String resultDate = year + "-" + month + "-" + date + " " + time;
		return resultDate;
		}
		return null ;
	}
	/**
	 * OpenhubProject CommitTime 先根据约定的方式解决以后再想更好的方法
	 * @param str
	 * @return
	 */
	public static String handleTimeToFormat(String str) {
		String tmp = str.trim();
		String[] tmpList = tmp.split(" ");
		if(tmpList.length>0){
		String date = tmpList[2];
		String month = tmpList[1];
		String year = tmpList[tmpList.length-1];
		String time = tmpList[3];
		switch (month) {
		case "Jan": {
			month = "01";
			break;
		}
		case "Feb": {
			month = "02";
			break;
		}
		case "Mar": {
			month = "03";
			break;
		}
		case "Apr": {
			month = "04";
			break;
		}
		case "May": {
			month = "05";
			break;
		}
		case "Jun": {
			month = "06";
			break;
		}
		case "Jul": {
			month = "07";
			break;
		}
		case "Aug": {
			month = "08";
			break;
		}
		case "Sep": {
			month = "09";
			break;
		}
		case "Oct": {
			month = "10";
			break;
		}
		case "Nov": {
			month = "11";
			break;
		}
		case "Dec": {
			month = "12";
			break;
		}
		default: {
			month = "00";
			break;
		}
		}
		String resultDate = year + "-" + month + "-" + date + " " + time;		
		return resultDate;
		}
		return null ;
	}

	
	
	public static String handleFreeCodePostTime(String str){
		str = str.trim();
		String[] tmpList = str.split(" ");
		String ans = null;
		if(tmpList.length>0){
			String date = tmpList[0];
			String month = tmpList[1];
			String year = tmpList[2];
			String time = tmpList[3];
			month = changeMonthToNum(month);
			ans = year+"-"+month+"-"+date+" "+time;
		}else{
			return null;
		}
		return ans;
	}
	
	private static String changeMonthToNum(String month){
		if(month!=null){
			switch (month) {
			case "Jan": {
				month = "01";
				break;
			}
			case "Feb": {
				month = "02";
				break;
			}
			case "Mar": {
				month = "03";
				break;
			}
			case "Apr": {
				month = "04";
				break;
			}
			case "May": {
				month = "05";
				break;
			}
			case "Jun": {
				month = "06";
				break;
			}
			case "Jul": {
				month = "07";
				break;
			}
			case "Aug": {
				month = "08";
				break;
			}
			case "Sep": {
				month = "09";
				break;
			}
			case "Oct": {
				month = "10";
				break;
			}
			case "Nov": {
				month = "11";
				break;
			}
			case "Dec": {
				month = "12";
				break;
			}
			default: {
				month = "00";
				break;
			}
			}
			return month;
		}else{
			return null;
		}
		
	}
}
