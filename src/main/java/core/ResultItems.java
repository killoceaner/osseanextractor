package core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Object contains extract results.<br>
 * It is contained in Page and will be processed in pipeline.
 * 
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 * @see Page
 * @see us.codecraft.webmagic.pipeline.Pipeline
 */
public class ResultItems {

	private Map<String, Object> fields = new LinkedHashMap<String, Object>();

	private Map<String, Boolean> isFieldSkip = new LinkedHashMap<String, Boolean>();

	private boolean skip;

	private String pageUrl;

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		Object o = fields.get(key);
		if (o == null) {
			return null;
		}
		return (T) fields.get(key);
	}

	public <T> ResultItems put(String key, T value) {
		fields.put(key, value);
		return this;
	}

	public Map<String, Object> getAll() {
		return fields;
	}

	public boolean getFieldSkip(String key) {
		if (isFieldSkip.containsKey(key))
			return isFieldSkip.get(key);
		return false;
	}

	public ResultItems putFieldSkip(String key, boolean value) {
		isFieldSkip.put(key, value);
		return this;
	}

	/**
	 * Whether to skip the result.<br>
	 * Result which is skipped will not be processed by Pipeline.
	 * 
	 * @return whether to skip the result
	 */
	public boolean isSkip() {
		return skip;
	}

	/**
	 * Set whether to skip the result.<br>
	 * Result which is skipped will not be processed by Pipeline.
	 * 
	 * @param skip
	 *            whether to skip the result
	 * @return this
	 */
	public ResultItems setSkip(boolean skip) {
		this.skip = skip;
		return this;
	}

	/**
	 * is all field output skiped
	 * 
	 * @return
	 */
	public boolean isAllFieldSkip() {
		if (fields.size() > isFieldSkip.size())
			return false;
		Set<String> keys = isFieldSkip.keySet();
		for (String key : keys) {
			if (!isFieldSkip.get(key))
				return false;
		}
		return true;
	}

	/**
	 * is all field output skiped find by name;
	 * 
	 * @return
	 */
	public boolean isAllFieldSkip(String... fieldName) {
		for (String name : fieldName)
			if (!getFieldSkip(name))
				return false;
		return true;
	}

	public String getUrl() {
		return pageUrl;
	}

	public void setUrl(String url) {
		this.pageUrl = url;
	}

	@Override
	public String toString() {
		return "ResultItems{" + "fields=" + fields + ",url=" + pageUrl
				+ ", skip=" + skip + '}';
	}
}
