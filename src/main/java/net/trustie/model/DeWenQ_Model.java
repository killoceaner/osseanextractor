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

@ExtractBy("//*[@id='wrap']/div[@class='container']/div[@class='qa_lft']")
public class DeWenQ_Model implements AfterExtractor, ValidateExtractor {
	private String issueId;

	private String tag;

	private String issueUrl = "";

	private int history = 0;

	private String extractTime;

	private String pageMD5 = "";

	@ExtractBy("//div[@id='edit1']/h1[@id='title' ]/text()")
	private String issueTitle = "";

	@ExtractBy("//*[@id='qst_content']/div[@class='codetitle']/div[@class='que_con']/allText()")
	private String issueDetail = "";

	@ExtractBy("//div[@id='edit1']/div[@id='topic']/a/allText()")
	private List<String> tags;

	@ExtractBy(value = "//*[@class='qa_rgt']/div[@class='question_stats']/div[@class='stats']/p/b/text()", source = Source.RawHtml)
	private String scanerNum = "";

	@ExtractBy(value = "//*[@class='qa_rgt']/div[@class='follow_questions']/h2/span/text()", source = Source.RawHtml)
	private String attentionNum = "";

	@ExtractBy("//*[@class='microbar']/div[@class='function_items']/a[@class='qcmtNum']/b[@class='cmtNum']/text()")
	private String commentNum = "";

	@ExtractBy("//*[@class='microbar']/div[@class='function_items']/span/text()")
	private String postTime = "";

	@ExtractBy("//div[@id='edit1']/[@id='changebg']/div[@class='pad6']/ul/li[@class='list_tt']/p[@class='lftp']/a/text()")
	private String author = "";

	@ExtractBy("//div[@id='edit1']/[@id='changebg']/div[@class='pad6']/ul/li[@class='list_tt']/p[@class='lftp']/a/@href")
	private String authorUrl;

	@ExtractBy("//div[@class='answers_num']/span/text()")
	private String answerNum;

	public void afterProcess(Page page) {
		// 处理issueUrl
		this.issueUrl = page.getPageUrl();

		// 处理issueId
		this.issueId = StringHandler.matchRightString(page.getPageUrl(),
				"q/\\d+/");
		this.issueId = StringHandler.matchRightString(this.issueId, "\\d+");

		// 处理tag
		this.tag = StringHandler.combineTags(tags);

		// 处理scanerNum
		this.scanerNum = StringHandler
				.findRigthString(this.scanerNum, "(", ")");

		// 处理attentionNum
		if (this.attentionNum != null)
			this.attentionNum = StringHandler.matchRightString(
					this.attentionNum, "\\d+");
		if (this.attentionNum == null)
			this.attentionNum = "0";

		// 处理postTime
		this.postTime = DateHandler.formatAllTypeDate(this.postTime,page.getTime());

		// 处理authorUrl
		this.authorUrl = "http://www.dewen.io" + this.authorUrl;

		// 处理answerNum
		if (this.answerNum != null)
			this.answerNum = StringHandler.matchRightString(this.answerNum,
					"\\d+");
		if (this.answerNum == null)
			this.answerNum = "0";

		// 处理extractTime
		this.extractTime = DateHandler.getExtractTime();	

		// 处理pageMD5
		this.pageMD5 = DigestUtils.md5Hex(this.issueTitle + this.scanerNum
				+ this.attentionNum + this.commentNum + this.answerNum);
	}

	@Override
	public void validate(Page page) {
		if (StringHandler.isAtLeastOneBlank(this.issueTitle, this.authorUrl)) {
			page.setResultSkip(this, true);
			return;
		}

		if (!StringHandler.canFormatterInteger(this.issueId, this.scanerNum,
				this.attentionNum, this.commentNum, this.answerNum)) {
			page.setResultSkip(this, true);
			return;
		}

		if (!DateHandler.canFormatToDate(this.extractTime, this.postTime))
			page.setResultSkip(this, true);	
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getIssueUrl() {
		return issueUrl;
	}

	public void setIssueUrl(String issueUrl) {
		this.issueUrl = issueUrl;
	}

	public String getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}

	public String getIssueDetail() {
		return issueDetail;
	}

	public void setIssueDetail(String issueDetail) {
		this.issueDetail = issueDetail;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getScanerNum() {
		return scanerNum;
	}

	public void setScanerNum(String scanerNum) {
		this.scanerNum = scanerNum;
	}

	public String getAttentionNum() {
		return attentionNum;
	}

	public void setAttentionNum(String attentionNum) {
		this.attentionNum = attentionNum;
	}

	public String getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public String getPageMD5() {
		return pageMD5;
	}

	public void setPageMD5(String pageMD5) {
		this.pageMD5 = pageMD5;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getHistory() {
		return history;
	}

	public void setHistory(int history) {
		this.history = history;
	}

	public String getExtractTime() {
		return extractTime;
	}

	public void setExtractTime(String extractTime) {
		this.extractTime = extractTime;
	}

	public String getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(String answerNum) {
		this.answerNum = answerNum;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

}
