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

@ExtractBy("//*[@id='OSC_Content']/div[@class='Question']/div[@class='Body']/div[@class='main']")
public class OSChinaQuestion_Model implements AfterExtractor, ValidateExtractor {

	private String questionUrl = "";

	private String questionId = "0";

	private String extractTime = null;

	private String pageMD5 = "";	

	private int history = 0;

	private String tag = "";

	@ExtractBy("//*[@class='info']/span[@class='pinfo']/span/a[@class='answer_count']/text()")
	private String replyNum;

	@ExtractBy("//*[@class='info']/span[@class='pinfo']/span[2]/text()")
	private String viewNum;

	@ExtractBy("//*[@class='info']/span[@class='pinfo']/a[@class='Asker-Name']/text()")
	private String author;

	@ExtractBy("//*[@class='info']/span[@class='ainfo']/a/@href")
	private String authorUrl;

	@ExtractBy("//div[@class='Title']/div[@class='QTitle']/h1/a/text()")
	private String questionTitle;

	@ExtractBy("//*[@class='tags_toolbars']/div[@id='tags_nav']/a/allText()")
	private List<String> questionTags;

	@ExtractBy(value = "//*[@id='OSC_Banner']/div[@class='wp998']/dl/dd/a[2]/text()", source = Source.RawHtml)
	private String questionType;

	@ExtractBy("//*[@class='info']/span[@class='pinfo']/span[@class='pub_time']/text()")
	private String postTime;

	@ExtractBy("//*[@class='store']/a[@id='favor_trigger']/text()")
	private String housedNum;

	@ExtractBy("//[@id='Vote']/span[@class='q_vote_num']/text()")
	private String laudNum;

	@ExtractBy("//[@class='Content']/div[@class='detail']/allText()")
	private String questionContent = null;

	public void afterProcess(Page page) {
		// 处理标签
		this.tag = StringHandler.combineTags(questionTags);

		// 处理收藏数
		this.housedNum = StringHandler.findRigthString(housedNum, "(", ")");

		// 处理帖子的url
		this.questionUrl = page.getPageUrl();

		// 处理浏览次数
		this.viewNum = StringHandler.matchRightString(this.viewNum, "\\d+");

		// 处理帖子的Id
		this.questionId = StringHandler.matchRightString(this.questionUrl,
				"\\d+_\\d+");

		// 处理抽取时间
		this.extractTime = DateHandler.getExtractTime();

		// 处理帖子的PageMD5
		this.pageMD5 = DigestUtils.md5Hex(this.questionContent
				+ this.questionTitle);

		// 处理postime		
		this.postTime=DateHandler.formatAllTypeDate(this.postTime,page.getTime());
	}

	public void validate(Page page) {
		// TODO Auto-generated method stub
		if (StringHandler
				.isAtLeastOneBlank(this.questionTitle, this.questionType,
						this.author, this.authorUrl, this.questionId)) {
			page.setResultSkip(this, true);
			return;
		}

		if (!StringHandler.canFormatterInteger(this.replyNum, this.housedNum,
				this.laudNum, this.viewNum)) {
			page.setResultSkip(this, true);
			return;
		}

		if (!DateHandler.canFormatToDate(this.postTime, this.extractTime))
			page.setResultSkip(this, true);		
	}

	public String getQuestionUrl() {
		return questionUrl;
	}

	public String getQuestionId() {
		return questionId;
	}

	public String getReplyNum() {
		return replyNum;
	}

	public String getViewNum() {
		return viewNum;
	}

	public String getAuthor() {
		return author;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public List<String> getQuestionTags() {
		return questionTags;
	}

	public String getQuestionType() {
		return questionType;
	}

	public String getPostTime() {
		return postTime;
	}

	public String getExtractTime() {
		return extractTime;
	}

	public String getHousedNum() {
		return housedNum;
	}

	public String getLaudNum() {
		return laudNum;
	}

	public String getPageMD5() {
		return pageMD5;
	}

	public int getHistory() {
		return history;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionUrl(String questionUrl) {
		this.questionUrl = questionUrl;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public void setReplyNum(String replyNum) {
		this.replyNum = replyNum;
	}

	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public void setQuestionTags(List<String> questionTags) {
		this.questionTags = questionTags;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public void setExtractTime(String extractTime) {
		this.extractTime = extractTime;
	}

	public void setHousedNum(String housedNum) {
		this.housedNum = housedNum;
	}

	public void setLaudNum(String laudNum) {
		this.laudNum = laudNum;
	}

	public void setPageMD5(String pageMD5) {
		this.pageMD5 = pageMD5;
	}

	public void setHistory(int history) {
		this.history = history;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
