package net.trustie.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.trustie.utils.DateHandler;
import net.trustie.utils.StringHandler;
import core.AfterExtractor;
import core.Page;
import core.ValidateExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;

public class FreeCode_Model implements AfterExtractor, ValidateExtractor {
	private int projectId = -1;

	private String projectUrl = "";

	private String extractTime;

	private String tag = "";

	private String license = "";

	private String operateSystem = "";

	private String implementation = "";

	private int history = 0;

	private String crawlerTime = null;
	
	

	@ExtractBy("//*div[@class ='clearfix']/h1/text()")
	private String projectTitle;

	@ExtractBy("//*div[@class='project-detail']/p/text()")
	private String projectDesc;

	@ExtractBy("//*[@class='sbblock']/div[@class='sidebar']/div[@class='sidebar-content']/div[@class='submitter clearfix']/ul/li/p/a[@class='submitter']/allText()")
	private String author;

	@ExtractBy("//*[@class='sbblock']/div[@class='sidebar']/div[@class='sidebar-content']/div[@class='submitter clearfix']/ul/li/p/a[@class='submitter']/@href")
	private String authorUrl;

	@ExtractBy("//*[@class='sbblock']/div[@class='sidebar']/div[@class='sidebar-content']/div[@class='submitter clearfix']/ul/li/p/span/text()")
	private String postDate;

	@ExtractBy("//*div[@class='project-detail']/table[@id='project-tag-cloud']/tbody/tr[1]/td/a/allText()")
	private List<String> tags;

	@ExtractBy("//*div[@class='project-detail']/table[@id='project-tag-cloud']/tbody/tr[2]/td/a/allText()")
	private List<String> licenses;

	@ExtractBy("//*div[@class='project-detail']/table[@id='project-tag-cloud']/tbody/tr[3]/td/a/allText()")
	private List<String> operateSystems;

	@ExtractBy("//*div[@class='project-detail']/table[@id='project-tag-cloud']/tbody/tr[4]/td/a/allText()")
	private List<String> implementations;

	@ExtractBy("//*div[@class='clearfix project-cta']/div[@id='more_info_download']/div[@class='downloadlink']/a/@href")
	private String projectDownloads;
	
	@ExtractBy("//*div[@class='clearfix project-cta']/div[@id='more_info_download']/div[@class='moreinfolink']/a/@href")
	private String projectWebsite;
	
	@Override
	public void afterProcess(Page page) {

		// 处理帖子的tags licenses operateSystems implementations
		tag = StringHandler.combineTags(this.tags);
		license = StringHandler.combineTags(this.licenses);
		operateSystem = StringHandler.combineTags(this.operateSystems);
		implementation = StringHandler.combineTags(this.implementations);

		// 处理发帖的时间
		/*this.postDate = StringHandler.matchRightString(this.postDate,
				"[0-9]{4,}-.*");
        */
		
		// 处理id，url(不过freecode的项目都没有id)；
		this.projectUrl = page.getPageUrl();
		String id = StringHandler.matchRightString(this.projectUrl, "[0-9]+");
		if (id != null) {
			projectId = Integer.parseInt(id);
		}
		
		this.postDate = DateHandler.handleFreeCodePostTime(this.postDate);
		
		// 处理extractorTime	
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		this.extractTime = dateFormat.format(new Date());
		
		//处理标题
		this.projectTitle = this.projectTitle.trim();
		
		//处理爬虫时间
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.crawlerTime = s.format(page.getTime());
		
	}

	@Override
	public void validate(Page page) {
		//查看抽取的结果；
		/*System.out.println("projectUrl:" + projectUrl + "\n" + "extractTime:"
				+ extractTime + "\n" +"tag:"+tag+"\n"+ "license:" + license + "\n"
				+ "operateSystem:" + operateSystem + "\n" + "implementation:"+implementation
				+"\n"+"projectTitle:"+projectTitle+"\n"+"projectDesc:"+ projectDesc+"\n"+"author:"+author+"\n"+"authorUrl:"+authorUrl+"postDate:"+postDate);
        */
		// TODO Auto-generated method stub
		
		if (!page.getResultItems().isSkip()) {
			if (!StringHandler.isAllNotBlank(this.projectTitle,
					this.projectDesc, this.author,this.postDate)) {
				page.setSkip(true);
				return;
			}
//			if (!StringHandler.canFormatterDate(this.postDate)) {
//				page.setSkip(true);
//				return;
//			}
		}		
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectUrl() {
		return projectUrl;
	}

	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}

	public String getExtractTime() {
		return extractTime;
	}

	public void setExtractTime(String extractTime) {
		this.extractTime = extractTime;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getHistory() {
		return history;
	}

	public void setHistory(int history) {
		this.history = history;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	
	public String getCrawlerTime() {
		return crawlerTime;
	}

	public void setCrawlerTime(String crawlerTime) {
		this.crawlerTime = crawlerTime;
	}
	
	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getLicenses() {
		return licenses;
	}

	public void setLicenses(List<String> licenses) {
		this.licenses = licenses;
	}

	public List<String> getOperatSystems() {
		return operateSystems;
	}

	public void setOperatSystems(List<String> operatSystems) {
		this.operateSystems = operatSystems;
	}

	public List<String> getImplementations() {
		return implementations;
	}

	public void setImplementations(List<String> implementations) {
		this.implementations = implementations;
	}

	public String getImplementation() {
		return implementation;
	}

	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}

	public String getOperateSystem() {
		return operateSystem;
	}

	public void setOperateSystem(String operateSystem) {
		this.operateSystem = operateSystem;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getAuthor() {
		return author;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public List<String> getOperateSystems() {
		return operateSystems;
	}

	public String getProjectDownloads() {
		return projectDownloads;
	}

	public String getProjectWebsite() {
		return projectWebsite;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public void setOperateSystems(List<String> operateSystems) {
		this.operateSystems = operateSystems;
	}

	public void setProjectDownloads(String projectDownloads) {
		this.projectDownloads = projectDownloads;
	}

	public void setProjectWebsite(String projectWebsite) {
		this.projectWebsite = projectWebsite;
	}	
	
}
