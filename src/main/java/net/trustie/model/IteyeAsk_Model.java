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

@ExtractBy("//*[@id='page']/div[@id='content']//div[@id='main']/div[@class='problem']/dl")
public class IteyeAsk_Model implements AfterExtractor, ValidateExtractor {
	private int questionId;

	private String questionUrl;

	private String pageMD5;

	private String history = "0";

	private String extractTime;

	private String tag;

	@ExtractBy("//*div[@class='sproblem_right']/h3/span[@class='score']/text()")
	private String questionScore;

	@ExtractBy(value = "//*div[@class='sproblem_right']/h3/a/text()")
	private String questionTitle = "";

	@ExtractBy("//*div[@class='sproblem_right']/div[@class='new_content']/div[@class='iteye-blog-content-contain']/allText()|//*div[@class='sproblem_right']/div[@class='new_content']/allText()")
	private String questionContent = "";

	@ExtractBy(value = "//*[@id='content']/div[@id='main']/div[@class='crumbs']/a[2]/text()", source = Source.RawHtml)
	private String categories;

	@ExtractBy("//*div[@class='ask_label']/span/text()")
	private String postTime = "";

	@ExtractBy("//*div[@class='ask_label']/div[@class='tags']/a/allText()")
	private List<String> tags;

	@ExtractBy(value = "//*div[@id='content']/div[@id='main']/div[@id='solutions']/h3[@id='num']/text()", source = Source.RawHtml)
	private String answerNum = "0";

	@ExtractBy("//*div[@class='user_info']/span[@class='user_blog']/a/text()")
	private String author;

	@ExtractBy("//*div[@class='user_info']/span[@class='user_blog']/a/@href")
	private String authorUrl;

	@ExtractBy("//*div[@id='problem_action']/ul/li[2]/a/text()")
	private String interestNum;

	@ExtractBy("//dt/div[@class='vote']/span[1]/text()")
	private String voteUp;

	@ExtractBy("//dt/div[@class='vote']/span[2]/text()")
	private String voteDown;

	public void afterProcess(Page page) {
		// 处理questionUrl
		this.questionUrl = page.getPageUrl();

		// 处理tag
		this.tag = StringHandler.combineTags(tags);

		// 处理questionId
		this.questionId = Integer.parseInt(page.getPageUrl().substring(
				page.getPageUrl().lastIndexOf("/") + 1));

		// 处理answerNum
		if (StringUtils.isNotBlank(this.answerNum)) {
			this.answerNum = StringHandler.matchRightString(this.answerNum,
					"\\d+");
		} else
			this.answerNum = "0";

		// interestNum
		this.interestNum = StringHandler.matchRightString(this.interestNum,
				"\\d+");

		// 处理抽取时间
		this.extractTime = DateHandler.getExtractTime();

		// 处理pageMD5
		this.pageMD5 = DigestUtils.md5Hex(this.questionTitle + this.answerNum
				+ this.interestNum + this.voteUp + this.voteDown);

		// 处理postTime			
	    this.postTime=DateHandler.formatAllTypeDate(this.postTime,page.getTime());
	}

	@Override
	public void validate(Page page) {
		if (StringHandler.isAtLeastOneBlank(this.questionTitle, this.author,
				this.authorUrl)) {
			page.setResultSkip(this, true);
			return;
		}

		if (!StringHandler.canFormatterInteger(this.questionScore,
				this.answerNum, this.interestNum, this.voteUp, this.voteDown)) {
			page.setResultSkip(this, true);
		}

		if (!DateHandler.canFormatToDate(this.postTime, this.extractTime))
			page.setResultSkip(this, true);		
	}

	public int getQuestionId() {
		return questionId;
	}

	public String getQuestionUrl() {
		return questionUrl;
	}

	public String getPageMD5() {
		return pageMD5;
	}

	public String getHistory() {
		return history;
	}

	public String getExtractTime() {
		return extractTime;
	}

	public String getTag() {
		return tag;
	}

	public String getQuestionScore() {
		return questionScore;
	}

	public String getCategories() {
		return categories;
	}

	public String getPostTime() {
		return postTime;
	}

	public List<String> getTags() {
		return tags;
	}

	public String getAnswerNum() {
		return answerNum;
	}

	public String getAuthor() {
		return author;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public String getInterestNum() {
		return interestNum;
	}

	public String getVoteUp() {
		return voteUp;
	}

	public String getVoteDown() {
		return voteDown;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public void setQuestionUrl(String questionUrl) {
		this.questionUrl = questionUrl;
	}

	public void setPageMD5(String pageMD5) {
		this.pageMD5 = pageMD5;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public void setExtractTime(String extractTime) {
		this.extractTime = extractTime;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setQuestionScore(String questionScore) {
		this.questionScore = questionScore;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void setAnswerNum(String answerNum) {
		this.answerNum = answerNum;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public void setInterestNum(String interestNum) {
		this.interestNum = interestNum;
	}

	public void setVoteUp(String voteUp) {
		this.voteUp = voteUp;
	}

	public void setVoteDown(String voteDown) {
		this.voteDown = voteDown;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

}
