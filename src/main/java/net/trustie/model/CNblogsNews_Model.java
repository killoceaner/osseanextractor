package net.trustie.model;

import java.util.List;

import net.trustie.utils.DateHandler;
import net.trustie.utils.StringHandler;
import org.apache.commons.codec.digest.DigestUtils;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractBy.Source;
import core.AfterExtractor;
import core.Page;
import core.ValidateExtractor;

@ExtractBy("//*div[@id='main_wrapper']/div[@id='sideleft']/div[@id='news_main']")
public class CNblogsNews_Model implements AfterExtractor, ValidateExtractor {
	private int newsId;

	private String newsUrl;

	private String pageMD5;

	private String history = "0";

	private String tag;

	private String extractTime;

	@ExtractBy("//div[@id='news_info']/span[@class='news_poster']/a/text()")
	private String newsAuthor;

	@ExtractBy("//div[@id='news_info']/span[@class='news_poster']/a/@href")
	private String newsAuthorUrl;

	@ExtractBy("//div[@id='news_title']/a/text()")
	private String newsTitle;

	@ExtractBy("//div[@id='news_body']/allText()")
	private String newsContent;

	@ExtractBy("//div[@id='news_info']/span[@class='time']/text()")
	private String relativeTime;

	@ExtractBy("//*[@id='news_content']/div[@id='news_otherinfo']/div[@id='come_from']/allText()")
	private String comeFrom;

	@ExtractBy("//div[@id='news_more_info']/div[@class='news_tags']/a/text()")
	private List<String> tags;

	@ExtractBy("//div[@id='news_info']/a[@id='link_source1']/@href")
	private String originFrom;

	@ExtractBy(value = "//*[@id='sideleft']/div[@id='guide']/h3/a[3]/text()", source = Source.RawHtml)
	private String newsCategorie;

	public void afterProcess(Page page) {
		// 处理newsId
		this.newsId = Integer.parseInt(StringHandler.matchRightString(
				page.getPageUrl(), "\\d+"));

		// 处理newsUrl
		this.newsUrl = page.getPageUrl();

		// tag
		this.tag = StringHandler.combineTags(tags);

		// 处理comeFrom
		this.comeFrom = StringHandler.subString(this.comeFrom, "来自:");

		// 处理extractTime;
		this.extractTime = DateHandler.getExtractTime();

		// 处理relativeTime
		this.relativeTime = DateHandler.formatAllTypeDate(this.relativeTime,page.getTime());

		// 处理pageMD5
		this.pageMD5 = DigestUtils.md5Hex(this.newsTitle);
	}

	public void validate(Page page) {
		if (StringHandler.isAtLeastOneBlank(this.newsTitle, this.newsAuthorUrl)) {
			page.setResultSkip(this, true);
			return;
		}

		if (!DateHandler.canFormatToDate(this.relativeTime, this.extractTime))
			page.setResultSkip(this, true);	
	}

	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	public String getPageMD5() {
		return pageMD5;
	}

	public void setPageMD5(String pageMD5) {
		this.pageMD5 = pageMD5;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getNewsAuthor() {
		return newsAuthor;
	}

	public void setNewsAuthor(String newsAuthor) {
		this.newsAuthor = newsAuthor;
	}

	public String getNewsAuthorUrl() {
		return newsAuthorUrl;
	}

	public void setNewsAuthorUrl(String newsAuthorUrl) {
		this.newsAuthorUrl = newsAuthorUrl;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getRelativeTime() {
		return relativeTime;
	}

	public void setRelativeTime(String relativeTime) {
		this.relativeTime = relativeTime;
	}

	public String getExtractTime() {
		return extractTime;
	}

	public void setExtractTime(String extractTime) {
		this.extractTime = extractTime;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getOriginFrom() {
		return originFrom;
	}

	public void setOriginFrom(String originFrom) {
		this.originFrom = originFrom;
	}

	public String getNewsCategorie() {
		return newsCategorie;
	}

	public void setNewsCategorie(String newsCategorie) {
		this.newsCategorie = newsCategorie;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
}
