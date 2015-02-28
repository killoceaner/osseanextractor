package net.trustie.model;

import java.util.List;
import net.trustie.utils.DateHandler;
import net.trustie.utils.StringHandler;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractBy.Source;
import core.AfterExtractor;
import core.Page;
import core.ValidateExtractor;

@ExtractBy("//*[@id='main']/div[@class='main']/div[@id='article_details']")
public class CsdnBlogs_Model implements AfterExtractor, ValidateExtractor {
	private String blogId;

	private String blogPageMD5;

	private String blogUrl;

	private String extractTime;

	private int history = 0;

	private String blogTag;

	private String blogCategory;

	@ExtractBy("//div[@class='article_title']/h1/span[@class='link_title']/a/text()")
	private String blogTitle;

	@ExtractBy("//*div[@id='article_content']/allText()")
	private String blogContent;

	@ExtractBy("//*div[@class='tag2box']/a/allText()")
	private List<String> blogTags;

	@ExtractBy("//*div[@class='article_manage']/span[@class='link_view']/text()")
	private String readNum;

	@ExtractBy("//*div[@class='article_manage']/span[@class='link_comments']/text()")
	private String commentNum;

	@ExtractBy(value = "//*[@id='panel_Profile']/ul[@class='panel_body profile']/div[@id='blog_userface']/span/a[@class='user_name']/text()", source = Source.RawHtml)
	private String author;

	@ExtractBy(value = "//*[@id='panel_Profile']/ul[@class='panel_body profile']/div[@id='blog_userface']/span/a[@class='user_name']/@href", source = Source.RawHtml)
	private String authorUrl;

	@ExtractBy("//*div[@class='article_manage']/span[@class='link_postdate']/text()")
	private String postTime;

	@ExtractBy("//*div[@class='article_manage']/span[@class='link_categories']/a/allText()")
	private List<String> blogCategories;

	@ExtractBy("//*[@id='digg']/dl[@id='btnDigg']/dd/text()")
	private String supportNum;

	@ExtractBy("//*[@id='digg']/dl[@id='btnBury']/dd/text()")
	private String opposeNum;

	public void afterProcess(Page page) {
		// 处理blogId
		this.blogId = StringHandler.matchRightString(page.getPageUrl(),
				"details/\\d+");
		this.blogId = StringHandler.matchRightString(this.blogId, "\\d+");

		// commentNum
		this.commentNum = StringHandler.findRigthString(this.commentNum, "(",
				")");

		// 处理blogUrl
		this.blogUrl = page.getPageUrl();

		// 处理readNum
		this.readNum = StringHandler.matchRightString(this.readNum, "\\d+");

		// 处理blogTag
		this.blogTag = StringHandler.combineTags(this.blogTags);

		// 处理blogCategory
		this.blogCategory = StringUtils.join(this.blogCategories, ",");

		// 处理blogPageMD5
		this.blogPageMD5 = DigestUtils.md5Hex(this.commentNum
				+ this.blogContent);

		// 处理抽取extractTime
		this.extractTime = DateHandler.getExtractTime();

		// 处理postTime
		this.postTime = DateHandler.formatAllTypeDate(this.postTime,page.getTime());

		// 处理作者
		if (StringUtils.isBlank(this.author))
			this.authorUrl = null;
	}

	@Override
	public void validate(Page page) {
		if (StringHandler.isAtLeastOneBlank(this.blogTitle)) {
			page.setResultSkip(this, true);
			return;
		}

		if (!StringHandler.canFormatterInteger(this.blogId, this.commentNum,
				this.readNum, this.supportNum, this.opposeNum)) {
			page.setResultSkip(this, true);
			return;
		}

		if (!DateHandler.canFormatToDate(this.postTime, this.extractTime))
			page.setResultSkip(this, true);	
	}

	public String getBlogId() {
		return blogId;
	}

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	public String getBlogPageMD5() {
		return blogPageMD5;
	}

	public void setBlogPageMD5(String blogPageMD5) {
		this.blogPageMD5 = blogPageMD5;
	}

	public String getExtractTime() {
		return extractTime;
	}

	public void setExtractTime(String extractTime) {
		this.extractTime = extractTime;
	}

	public int getHistory() {
		return history;
	}

	public void setHistory(int history) {
		this.history = history;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogContent() {
		return blogContent;
	}

	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}

	public List<String> getBlogTags() {
		return blogTags;
	}

	public void setBlogTags(List<String> blogTags) {
		this.blogTags = blogTags;
	}

	public String getReadNum() {
		return readNum;
	}

	public void setReadNum(String readNum) {
		this.readNum = readNum;
	}

	public String getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public String getSupportNum() {
		return supportNum;
	}

	public void setSupportNum(String supportNum) {
		this.supportNum = supportNum;
	}

	public String getOpposeNum() {
		return opposeNum;
	}

	public void setOpposeNum(String opposeNum) {
		this.opposeNum = opposeNum;
	}

	public String getBlogTag() {
		return blogTag;
	}

	public void setBlogTag(String blogTag) {
		this.blogTag = blogTag;
	}

	public String getBlogCategory() {
		return blogCategory;
	}

	public void setBlogCategory(String blogCategory) {
		this.blogCategory = blogCategory;
	}

	public List<String> getBlogCategories() {
		return blogCategories;
	}

	public void setBlogCategories(List<String> blogCategories) {
		this.blogCategories = blogCategories;
	}

	public String getBlogUrl() {
		return blogUrl;
	}

	public void setBlogUrl(String blogUrl) {
		this.blogUrl = blogUrl;
	}
}
