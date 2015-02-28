package net.trustie.model;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import net.trustie.utils.DateHandler;
import net.trustie.utils.StringHandler;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractBy.Source;
import core.AfterExtractor;
import core.Page;
import core.ValidateExtractor;

@ExtractBy("//div[@class='main']/div[@class='persion_section']")
public class CsdnAsk_Model implements AfterExtractor, ValidateExtractor {
	private String issueId = "0";

	private String issueUrl = "";

	private String pageMD5;

	private String history = "0";

	private String tag;

	private String extractTime;

	@ExtractBy("//*div[@class='questions_detail_con']/dl/dt/text()")
	private String issueTitle = "";

	@ExtractBy("//*div[@class='questions_detail_con']/dl/dd[@id='cut_intro']/allText()")
	private String issueContent = "";

	@ExtractBy("//*a[@class='approve']/text()")
	private String voteNum;

	@ExtractBy(value = "//div[@class='questions_detail_con']/dl/div[@class='tags']/a/allText()")
	private List<String> tags;

	@ExtractBy(value = "//*div[@class='persion_article']/div[@class='mod_user_info']/div[@class='user_info']/div[@class='info_box clearfix']/p/text()", source = Source.RawHtml)
	private String postTime = "";

	@ExtractBy(value = "//*div[@class='persion_article']/div[@class='mod_user_info']/div[@class='user_info']/div[@class='info_box clearfix']/dl[@class='person_info']/dt/a/text()", source = Source.RawHtml)
	private String author = "";

	@ExtractBy(value = "//*dl[@class='person_info']/dt/a/@href", source = Source.RawHtml)
	private String authorUrl;

	@ExtractBy("//*div[@class='q_operate']/p/text()")
	private String answerNum;

	public void afterProcess(Page page) {
		// 处理issueUrl
		this.issueUrl = page.getPageUrl();

		// 处理issueId
		this.issueId = StringHandler
				.matchRightString(page.getPageUrl(), "\\d+");

		// 处理tag
		this.tag = StringHandler.combineTags(tags);

		// 处理extractTime
		this.extractTime = DateHandler.getExtractTime();

		// 处理postTime
		this.postTime = StringHandler.subString(this.postTime, "创建自");
		this.postTime = DateHandler.formatAllTypeDate(this.postTime,page.getTime());

		// 处理answerNum
		if (StringUtils.isNotBlank(this.answerNum))
			this.answerNum = StringHandler.matchRightString(this.answerNum,
					"\\d+");
		else
			this.answerNum = "0";

		this.pageMD5 = DigestUtils.md5Hex(this.issueTitle + this.answerNum
				+ this.voteNum);

	}

	@Override
	public void validate(Page page) {
		if (StringHandler.isAtLeastOneBlank(this.issueTitle, this.author,
				this.authorUrl)) {
			page.setResultSkip(this, true);
			return;
		}
		
		if (!StringHandler.canFormatterInteger(this.issueId, this.voteNum,
				this.answerNum)) {
			page.setResultSkip(this, true);
			return;
		}
		
		if (!DateHandler.canFormatToDate(this.extractTime, this.postTime))
			page.setResultSkip(this, true);	
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}

	public String getIssueContent() {
		return issueContent;
	}

	public void setIssueContent(String issueContent) {
		this.issueContent = issueContent;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVoteNum() {
		return voteNum;
	}

	public void setVoteNum(String voteNum) {
		this.voteNum = voteNum;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getIssueUrl() {
		return issueUrl;
	}

	public void setIssueUrl(String issueUrl) {
		this.issueUrl = issueUrl;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
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

	public String getExtractTime() {
		return extractTime;
	}

	public void setExtractTime(String extractTime) {
		this.extractTime = extractTime;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public String getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(String answerNum) {
		this.answerNum = answerNum;
	}
}
