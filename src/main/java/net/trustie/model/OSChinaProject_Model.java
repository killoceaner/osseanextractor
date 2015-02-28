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

@ExtractBy("//*div[@id='OSC_Content']/div[@class='ProjectPage']/div[@class='ProjectMain']/div[@class='Project']")
public class OSChinaProject_Model implements AfterExtractor, ValidateExtractor {

	private String projectUrl;

	private String pageMD5;

	private String exteactTime;

	private String history = "0";

	private String projectLanguage;

	private String projectLicenses;

	private String projectOS;

	private String IncludedTime;

	@ExtractBy("//h1[@class='PN']/a/u/text()")
	private String projectShortName;

	@ExtractBy(value = "//*div[@id='OSC_Banner']/div[1]/dl/dt[2]/a[2]/text()", source = Source.RawHtml)
	private String projectCategory;

	@ExtractBy("//h1[@class='PN']/a/allText()")
	private String projectTitle;

	@ExtractBy("//div[@id='Body']/div[@id='p_fullcontent']/allText()")
	private String projectDesc;

	@ExtractBy("//div[@id='Body']/div[@id='p_fullcontent']/p[1]/a/text()")
	private String advert;

	@ExtractBy("//div[@id='toolbar_wrapper']/div[@class='toolbar']/div[@class='options']/div[@class='soft_used']/span/text()")
	private String usedNum;

	@ExtractBy(value = "//*div[@class='ProjectPage']/div[@class='ProjectRight']/div[@class='TopUsers']/div[@class='RightTitle']/em[@id='attentent_count']/text()", source = Source.RawHtml)
	private String housedNum;

	@ExtractBy("//div[@id='Body']/ul[@class='attrs']/li/html()")
	private List<String> attrs;

	@Override
	public void afterProcess(Page page) {
		// 处理projectUrl
		this.projectUrl = page.getPageUrl();		

		// 处理projectShortName
		if (StringUtils.isNotBlank(this.projectShortName))
			this.projectShortName = projectShortName.replace("»", "").trim();

		// 处理exteactTime
		this.exteactTime = DateHandler.getExtractTime();

		// 处理projectDesc
		if (this.advert != null)
			this.projectDesc = StringHandler.subString(this.projectDesc,
					this.advert);

		// 处理usedNum
		this.usedNum = StringHandler.matchRightString(this.usedNum, "\\d+");

		// 处理pageMD5
		this.pageMD5 = DigestUtils.md5Hex(this.projectTitle + this.housedNum
				+ this.usedNum);

		// 处理attrs
		for (String s : attrs) {
			if (s.contains("授权协议")) {
				this.projectLicenses = StringHandler.extractHtml(s,
						"//allText()");
				this.projectLicenses = StringHandler.subString(projectLicenses,
						"授权协议：");
			}

			else if (s.contains("开发语言")) {
				List<String> languages = StringHandler.extractHtmlList(s,
						"//a/allText()");
				this.projectLanguage = StringHandler.combineTags(languages)
						.replace(",<查看源码»>", "");
			}

			else if (s.contains("操作系统")) {
				List<String> osystems = StringHandler.extractHtmlList(s,
						"//a/allText()");
				this.projectOS = StringHandler.combineTags(osystems);
			}

			else if (s.contains("收录时间")) {
				this.IncludedTime = StringHandler.matchRightString(
						StringHandler.extractHtml(s, "//allText()"),
						"\\d+年\\d+月\\d+日");

				this.IncludedTime = DateHandler
						.formatAllTypeDate(this.IncludedTime,page.getTime());
			}
		}
	}

	@Override
	public void validate(Page page) {
		if (StringHandler.isAtLeastOneBlank(this.projectTitle,
				this.projectCategory)) {
			page.setResultSkip(this, true);
			return;
		}

		if (!StringHandler.canFormatterInteger(this.usedNum, this.housedNum)) {
			page.setResultSkip(this, true);
			return;
		}

		if (!DateHandler.canFormatToDate(this.IncludedTime, this.exteactTime))
			page.setResultSkip(this, true);
	}

	public String getProjectUrl() {
		return projectUrl;
	}

	public String getPageMD5() {
		return pageMD5;
	}

	public String getExteactTime() {
		return exteactTime;
	}

	public String getHistory() {
		return history;
	}

	public String getProjectShortName() {
		return projectShortName;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public String getProjectLanguage() {
		return projectLanguage;
	}

	public String getIncludedTime() {
		return IncludedTime;
	}

	public String getUsedNum() {
		return usedNum;
	}

	public String getHousedNum() {
		return housedNum;
	}

	public String getProjectCategory() {
		return projectCategory;
	}

	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}

	public void setPageMD5(String pageMD5) {
		this.pageMD5 = pageMD5;
	}

	public void setExteactTime(String exteactTime) {
		this.exteactTime = exteactTime;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public void setProjectShortName(String projectShortName) {
		this.projectShortName = projectShortName;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public void setProjectLanguage(String projectLanguage) {
		this.projectLanguage = projectLanguage;
	}

	public void setIncludedTime(String includedTime) {
		IncludedTime = includedTime;
	}

	public void setUsedNum(String usedNum) {
		this.usedNum = usedNum;
	}

	public void setHousedNum(String housedNum) {
		this.housedNum = housedNum;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	public String getProjectLicenses() {
		return projectLicenses;
	}

	public void setProjectLicenses(String projectLicenses) {
		this.projectLicenses = projectLicenses;
	}

	public String getAdvert() {
		return advert;
	}

	public void setAdvert(String advert) {
		this.advert = advert;
	}

	public String getProjectOS() {
		return projectOS;
	}

	public List<String> getAttrs() {
		return attrs;
	}

	public void setProjectOS(String projectOS) {
		this.projectOS = projectOS;
	}

	public void setAttrs(List<String> attrs) {
		this.attrs = attrs;
	}
}
