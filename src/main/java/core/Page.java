package core;

import java.util.Date;

import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.UrlUtils;

/**
 * 
 * @author Administrator
 * 
 */
public class Page {
	private ResultItems resultItems = new ResultItems();

	private Html html;

	private Json json;

	private String rawText;

	private Selectable url;

	private String pageUrl;
	
	private Date time;

	public Page() {

	}

	public Page setSkip(boolean skip) {
		resultItems.setSkip(skip);
		return this;
	}

	/**
	 * store extract results
	 * 
	 * @param key
	 * @param field
	 */
	public void putField(String key, Object field) {
		resultItems.put(key, field);
	}

	/**
	 * decide the extract results produced by single model are store;
	 * 
	 * @param key
	 * @param value
	 */
	public void setResultSkip(Object object, boolean value) {
		resultItems.putFieldSkip(object.getClass().getCanonicalName(), value);
	}

	/**
	 * get results is Skiped
	 */
	public boolean getResultSkip(Object object) {
		return resultItems.getFieldSkip(object.getClass().getCanonicalName());
	}
	
	/**
	 * resultItems is all skip
	 * @return
	 */
	public boolean isAllResultSkip(){
		return resultItems.isAllFieldSkip();
	}
	
	/**
	 * resultItems is all skip find by name;
	 * @param names
	 * @return
	 */
	public boolean isAllResultSkip(String...names){
		return resultItems.isAllFieldSkip(names);
	}

	/**
	 * get html content of page
	 * 
	 * @return html
	 */
	public Html getHtml() {
		if (html == null) {
			html = new Html(UrlUtils.fixAllRelativeHrefs(rawText, pageUrl));
		}
		return html;
	}

	/**
	 * get json content of page
	 * 
	 * @return json
	 * @since 0.5.0
	 */
	public Json getJson() {
		if (json == null) {
			json = new Json(rawText);
		}
		return json;
	}

	/**
	 * @param html
	 * @deprecated since 0.4.0 The html is parse just when first time of calling
	 *             {@link #getHtml()}, so use {@link #setRawText(String)}
	 *             instead.
	 */
	public void setHtml(Html html) {
		this.html = html;
	}

	public String getRawText() {
		return rawText;
	}

	public Page setRawText(String rawText) {
		this.rawText = rawText;
		return this;
	}

	public Selectable getUrl() {
		return url;
	}

	public void setUrl(Selectable url) {
		this.url = url;
	}

	public ResultItems getResultItems() {
		return resultItems;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}	

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Page{" + "pageUrl=" + pageUrl + ", resultItems=" + resultItems
				+ ", rawText='" + rawText + '\'' + ", url=" + url + '}';
	}

}
