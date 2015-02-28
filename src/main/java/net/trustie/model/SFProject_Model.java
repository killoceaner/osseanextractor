package net.trustie.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.trustie.utils.DateHandler;
import net.trustie.utils.StringHandler;
import net.trustie.utils.VanishTime;

import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import core.AfterExtractor;
import core.Page;
import us.codecraft.webmagic.model.annotation.ExtractBy;

@ExtractBy("//body[@id='pg_project']/div[@id='page-body']/article[@id='project']")
// 限定抽取区域
public class SFProject_Model implements AfterExtractor {
	// @ExtractBy("//div[@id='project-header']/section[@id='project-title']/h1/allText() | //div[@id='project-header']/div[@class='content-group']/h1[@class='project-name']/text()")
	private String name = "";
	// @ExtractBy("//div[@id='project-header']/section[@id='project-title']/p[@itemprop='author']/a/allText()")
	private String maintainers = "";
	// @ExtractBy("//section[@id='main-content']/section[@id='call-to-action']/section[@id='counts-sharing']/section[@id='project-info']/section[@class='content']/a[@title='Browse reviews']/text()")
	private float stars = 0;
	private String downloadCount = "";
	// @ExtractBy("//section[@id='main-content']/section[@id='call-to-action']/section[@id='counts-sharing']/section[@id='last-updated']/section[@class='content']/time[@class='dateUpdated']/@datetime | //div[@id='project-header']/div[@class='content-group']/div[@class='project-rating']/time[@class='dateUpdated']/@datetime")
	private String lastUpdate = "";
	// @ExtractBy("//article[@id='project']/section[@id='main-content']/section[@id='call-to-action']/section[@id='download_button']/section[@class='project-info']/allText() | //div[@id='project-header']/div[@class='content-group']/h1[@class='download-os']/allText()")
	private String platform = "";
	// @ExtractBy("//article[@id='project']/section[@id='main-content//']/section[@id='project-description']/p/allText() | //section[@id='overview']/div[@class='content-group']/section[@class='primary-content']/p[@id='project-description']/allText()")
	private String desc = "";
	// @ExtractBy("//article[@id='project']/section[@id='main-content']/section[@id='project-categories-and-license']/div/section[1]/a/allText()")
	private String categories = "";
	// @ExtractBy("//article[@id='project']/section[@id='main-content']/section[@id='project-categories-and-license']/div/section[2]/section[@class='project-info']/section[@class='content']/allText()")
	private String license = "";
	// @ExtractBy("//article[@id='project']/section[@id='main-content']/section[@id='project-features']/div[@class='content editable']/allText() | //section[@id='overview']/div[@class='content-group']/section[@class='primary-content']/p[@id='project-features']/div[@class='content editable']/allText()")
	private String feature = "";
	// additional project details
	private String language = "";
	private String intendedAudience = "";
	private String userInterface = "";
	private String programmingLanguage = "";
	private String registeredTime = "";
	private String collectTime;
	// @ExtractByUrl()
	private String url;
	private String urlMd5;
	private String pageMd5;
	private int history = 0;

	// private String html;

	public void afterProcess(Page page) {
		// long start = System.currentTimeMillis();
		this.url = page.getPageUrl();
		// justify it's enterprise or bluesteel user
		// this.html = page.getHtml().toString();
		this.urlMd5 = DigestUtils.md5Hex(page.getPageUrl());
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		this.collectTime = bartDateFormat.format(new Date());
		this.pageMd5 = DigestUtils.md5Hex(urlMd5 + lastUpdate + feature
				+ downloadCount + stars);
		Document doc = page.getHtml().getDocument();
		Elements bodyEles = doc.select("body");

		if (bodyEles.size() > 0) {
			Element body = bodyEles.get(0);
			String bodyType = body.attr("id");
			if ("pg_project".equals(bodyType)) {
				String type = body.attr("class");

				if (type.equals("bluesteel user")) {

					// bluesteel user
					extractPageBluesteelUser(doc);

				} else if (type.equals("enterprise user")) {
					// enterprise user
					extractPageEnterpriseUser(doc);

				} else {
					// others
				}

				if (lastUpdate.contains("ago")) {
					this.lastUpdate = getTime(lastUpdate);
				}
				if (registeredTime.contains("ago")) {
					this.registeredTime = getTime((registeredTime));
				}

				if (lastUpdate.equals("")) {
					this.lastUpdate = "0000-00-00 00:00:00";
				}
				if (registeredTime.equals("")) {
					this.registeredTime = "0000-00-00 00:00:00";
				}
			} else {
				// name
				Elements nameEles = body
						.select("div#proj_header div.proj-title h2");
				this.name = nameEles.text();

				// desc
				Elements descEles = body
						.select("div#top_left div#home_intro div#proj-overview p");
				this.desc = descEles.text();

				// features
				Elements featuresEles = body
						.select("div#top_left div#home_intro div#proj-overview ul");
				this.feature = featuresEles.text();
			}
		
			    this.lastUpdate = DateHandler.formatAllTypeDate(lastUpdate,page.getTime());
				this.registeredTime = DateHandler.formatAllTypeDate(registeredTime, page.getTime());
	
		}

		// long end = System.currentTimeMillis();
		// System.out.println(end-start);

		// System.out.println(this.toString());
		// System.out.println(types.get(0).attr("class"));
		// Document doc=page.getHtml().getDocument();

	}

	private void extractPageEnterpriseUser(Document doc) {
		// name
		Elements nameElements = doc
				.select("div#project-header div.content-group h1.project-name a");
		name = nameElements.text();

		// maintainers

		// stars
		Elements starsElements = doc
				.select("div#project-header div.content-group div.project-rating span:not(.rating-count)");
		for (int i = 0; i < starsElements.size(); i++) {
			String attr = starsElements.get(i).attr("class");
			if (attr.equals("rating star")) {
				stars += 1;
			} else if (attr.equals("rating star_half")) {
				stars += 0.5;
			} else {
				stars += 0;
			}
		}

		// download count
		Elements downloadElements = doc
				.select("div#project-header div.content-group div.group a.download-stats span.data");
		if (downloadElements.size() > 0) {
			String strDownloadCount = downloadElements.get(0).text();
			strDownloadCount = strDownloadCount.replaceAll("[^\\d]", "");
			this.downloadCount = strDownloadCount;
		}

		// last update
		Elements lastUpdateElements = doc
				.select("div#project-header div.content-group div.project-rating time.dateUpdated");
		if (lastUpdateElements.size() > 0) {
			lastUpdate = lastUpdateElements.get(0).attr("datetime");
		}

		// platform
		Elements downloadOSElements = doc
				.select("div#project-header div.content-group div.download-os");
		platform = downloadOSElements.text();

		// desc
		Elements descElements = doc
				.select("section#overview div.content-group section.primary-content p#project-description");
		desc = descElements.text();

		// categories
		// license

		// feature
		Elements featureElements = doc
				.select("section#overview div.content-group section.primary-content section#project-features div[class=content editable]");
		feature = featureElements.text();
		// language
		// intended audience
		// user interface
		// program language
		// registered time
		// additional detail
		Elements enterpriseAddtionalElements = doc
				.select("section#overview div.content-group section.primary-content aside#additional-details section.content section.project-info");
		for (int i = 0; i < enterpriseAddtionalElements.size(); i++) {
			Element element = enterpriseAddtionalElements.get(i);
			// System.out.println(element.html());
			// System.out.println("*************************************");
			Elements tags = element.select("header");
			if (tags.size() > 0) {
				String tag = tags.text();
				if (tag.equals("Languages")) {
					language = element.select("section.content").text();
				} else if (tag.equals("Intended Audience")) {
					intendedAudience = element.select("section.content").text();
				} else if (tag.equals("User Interface")) {
					userInterface = element.select("section.content").text();
				} else if (tag.equals("Programming Language")) {
					programmingLanguage = element.select("section.content")
							.text();
				} else if (tag.equals("Registered")) {
					registeredTime = element.select("section.content").text();
				} else if (tag.equals("Last Updated")) {
					lastUpdate = element.select("section.content").text();
				} else if (tag.equals("Maintainers")) {
					maintainers = element.select("a").text();
				} else if (tag.equals("License")) {
					license = element.select("section.content").text();
				} else if (tag.equals("Categories")) {
					categories = element.select("a").text();
				} else {

				}
			}

		}
	}

	private void extractPageBluesteelUser(Document doc) {
		// if the project type is
		// bluesteel
		// name
		Elements nameElements = doc
				.select("div#project-header section#project-title h1[itemprop=name]");
		if (nameElements.size() > 0) {
			name = nameElements.get(0).text();
		}

		// maintainers
		Elements maintainersElements = doc
				.select("div#project-header section#project-title p#maintainers a");
		maintainers = maintainersElements.text();

		// stars
		Elements starElements = doc
				.select("article#project section#main-content section#call-to-action section#counts-sharing section.project-info section.content a[title=Browse reviews]");
		if (starElements.size() > 0) {
			String strStar = starElements.get(0).text();
			strStar = strStar.replaceAll("[^\\d\\.]", "");
			stars = Float.parseFloat(strStar);
		}

		// downloadCount
		Elements downloadElements = doc
				.select("article#project section#main-content section#call-to-action section#counts-sharing section#download-stats section.content a[title=Downloads This Week]");
		if (downloadElements.size() > 0) {
			String strDownloadCount = downloadElements.get(0).text();
			strDownloadCount = strDownloadCount.replaceAll("[^\\d]", "");
			downloadCount = strDownloadCount;
		}

		// last update
		Elements lastUpdateElements = doc
				.select("article#project section#main-content section#call-to-action section#counts-sharing section#last-updated section.content time.dateUpdated");
		if (lastUpdateElements.size() > 0) {
			lastUpdate = lastUpdateElements.get(0).attr("datetime");
		}

		// platform
		Elements platformElements = doc
				.select("article#project section#main-content section#call-to-action section#download_button section.project-info");
		if (platformElements.size() > 0) {
			platform = platformElements.text();
		}

		// desc
		Elements descElements = doc
				.select("article#project section#main-content section#project-description p");
		desc = descElements.text();

		// categories
		Elements categoriesElements = doc
				.select("article#project section#main-content section#project-categories-and-license div.project-container section:has(header:contains(Categories)) a");
		categories = categoriesElements.text();

		// license
		Elements licenseElements = doc
				.select("article#project section#main-content section#project-categories-and-license div.project-container section:has(section.project-info header:contains(License)");
		if (licenseElements.size() > 0) {
			license = licenseElements.get(0)
					.select("section.project-info section.content").text();
		}
		// license = licenseElements.html();

		// feature
		Elements featureElements = doc
				.select("article#project section#main-content section#project-features div[class=content editable]");
		feature = featureElements.text();

		// language+intended audience+user interface+programming
		// language+registered time
		Elements addtionalElements = doc
				.select("article#project section#main-content section#project-additional-trove div.project-container section.project-info");
		// System.out.println(addtionalElements.html());
		for (int i = 0; i < addtionalElements.size(); i++) {
			Element element = addtionalElements.get(i);
			// System.out.println(element.html());
			// System.out.println("*************************************");
			Elements tags = element.select("header");
			if (tags.size() > 0) {
				String tag = tags.text();
				if (tag.equals("Languages")) {
					language = element.select("section.content").text();
				} else if (tag.equals("Intended Audience")) {
					intendedAudience = element.select("section.content").text();
				} else if (tag.equals("User Interface")) {
					userInterface = element.select("section.content").text();
				} else if (tag.equals("Programming Language")) {
					programmingLanguage = element.select("section.content")
							.text();
				} else if (tag.equals("Registered")) {
					registeredTime = element.select("section.content").text();
				} else {

				}
			}

		}
	}

	public void validate(Page page) {
		if (StringHandler.isAtLeastOneBlank(this.name, this.platform,
				this.categories, this.url)) {
			page.setResultSkip(this, true);
			return;
		}

		if (!StringHandler.canFormatterInteger(this.downloadCount)) {
			page.setResultSkip(this, true);
			return;
		}
		;
		if (!DateHandler.canFormatToDate(this.lastUpdate, this.registeredTime,
				this.collectTime)) {
			page.setResultSkip(this, true);
		}

	}

	private String getTime(String strTime) {
		String time = null;
		int vanishTime = Integer.parseInt(strTime.replaceAll("[^\\d]", ""));
		String[] ways = strTime.split("\\d+");
		String unit = ways[1];
		if (unit.contains("seconds")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.SECOND)).toString();
		} else if (unit.contains("minutes")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.MINUTE)).toString();
		} else if (unit.contains("hours")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.HOUR)).toString();
		} else if (unit.contains("days")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.DAY)).toString();
		} else if (unit.contains("months")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.MONTH)).toString();
		} else if (unit.contains("years")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.YEAR)).toString();
		} else {
			SimpleDateFormat bartDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return bartDateFormat.format(new Date());
		}
		return time;
	}

	public String getName() {
		return name;
	}

	public String getMaintainers() {
		return maintainers;
	}

	public float getStars() {
		return stars;
	}

	public String getDownloadCount() {
		return downloadCount;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public String getPlatform() {
		return platform;
	}

	public String getDesc() {
		return desc;
	}

	public String getCategories() {
		return categories;
	}

	public String getLicense() {
		return license;
	}

	public String getFeature() {
		return feature;
	}

	public String getLanguage() {
		return language;
	}

	public String getIntendedAudience() {
		return intendedAudience;
	}

	public String getUserInterface() {
		return userInterface;
	}

	public String getProgrammingLanguage() {
		return programmingLanguage;
	}

	public String getRegisteredTime() {
		return registeredTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMaintainers(String maintainers) {
		this.maintainers = maintainers;
	}

	public void setStars(float stars) {
		this.stars = stars;
	}

	public void setDownloadCount(String downloadCount) {
		this.downloadCount = downloadCount;
	}
/*
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = DateHandler.formatAllTypeDate(lastUpdate,page.getTime());
	}*/

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setIntendedAudience(String intendedAudience) {
		this.intendedAudience = intendedAudience;
	}

	public void setUserInterface(String userInterface) {
		this.userInterface = userInterface;
	}

	public void setProgrammingLanguage(String programmingLanguage) {
		this.programmingLanguage = programmingLanguage;
	}

	public String getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		this.urlMd5 = DigestUtils.md5Hex(url);
	}

	public String getUrlMd5() {
		return urlMd5;
	}

	public void setUrlMd5(String urlMD5) {
		this.urlMd5 = urlMD5;
	}

	public String getPageMd5() {
		return pageMd5;
	}

	public void setPageMd5(String pageMD5) {
		this.pageMd5 = pageMD5;
	}

	public int getHistory() {
		return history;
	}

	public void setHistory(int history) {
		this.history = history;
	}

	// public String getHtml() {
	// return html;
	// }
	//
	// public void setHtml(String html) {
	// this.html = html;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SFProject [name=" + name + ", maintainers=" + maintainers
				+ ", stars=" + stars + ", downloadCount=" + downloadCount
				+ ", lastUpdate=" + lastUpdate + ", platform=" + platform
				+ ", desc=" + desc + ", categories=" + categories
				+ ", license=" + license + ", feature=" + feature
				+ ", language=" + language + ", intendedAudience="
				+ intendedAudience + ", userInterface=" + userInterface
				+ ", programmingLanguage=" + programmingLanguage
				+ ", registeredTime=" + registeredTime + ", collectTime="
				+ collectTime + ", url=" + url + ", urlMD5=" + urlMd5
				+ ", pageMD5=" + pageMd5 + ", history=" + history + "]";
	}

}
