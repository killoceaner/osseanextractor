package net.trustie.model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.trustie.utils.DateHandler;
import net.trustie.utils.Seperator;
import net.trustie.utils.StringHandler;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import core.AfterExtractor;
import core.Page;
import core.ValidateExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;

@ExtractBy("//div[@id='projects_show_page']")
public class OpenHubProject_Model implements AfterExtractor, ValidateExtractor {
	// private static String indefiniteArticleA = "a";
	// private static String indefiniteArticleAn = "an";
	private static String mostWrittenHeader = "mostly written in";
	private static String commentsPercentageTail = "number of source code comments";
	private static String codebaseStatusHeaderTail = "codebase";
	private static String teamScaleTail = "development team";
	private static String commitStatusTail = "commits";
	private static String estimateEffortTimeTail = "of effort";
	private static String firstCommitTimeHeader = "first commit in";
	private static String lastCommitTimeHeader = "most recent commit";

	@ExtractBy("//div[@class='project_title']/h1[@itemprop='name']/a/text()")
	private String name = "";
	@ExtractBy("//div[@id='widgets']/div[@id='project_header_activity_indicator']/div/text()")
	private String activity = "";

	@ExtractBy("////div[@id='widgets']/div[@itemprop='interactionCount']/div[@class='float_right']/div[@class='use_count']/a/text()")
	private String strUseCount = "";

	private int useCount = 0;


	@ExtractBy("//div[@class='span6']/div[@id='project_summary']/p/text()")
	private String description = "";

	private String tags = "";

	private String organization = "";

	// quick ref
	private String projectLinks = "";
	private String codeLocation = "";
	private String licenses = "";
	private String similarProjects = "";
	private String managers = "";

	// nut shell
	@ExtractBy("//div[@class='span6']/div[@class='well']/ul[@id='factoids']/li/div[@class='indent']")
	private List<String> codeInfos =new ArrayList<String>() ;
	private int commitNum = 0;
	private int contributorNum = 0;
	private int codeLinesNum = 0;
	private String mostWrittenIn = "";
	private String commentsPercentage = "";
	private String codebaseStatus = "";
	private String teamScale = "";
	private String commitStatus = "";
	private String estimateEffortTime = "";
	private Date firstCommitTime = new Date();
	private Date lastCommitTime = new Date();

	// languages
	@ExtractBy("//div[@class='span6']/table[@class=table]/tbody/tr[@class='float_left']/td[@style='width: 120px']")
	private List<String> languages = new ArrayList<String>();
	@ExtractBy("//div[@class='span6']/table[@class=table]/tbody/tr[@class='float_left']/td[@style='width: 20px']/span/span[@itemprop='ratingValue']")
	private List<String> percentages = new ArrayList<String>();
	private String languagePercentages = "";

	// activity
	@ExtractBy("//div[@class='span6']/div[@class='well']/table[@id='activity_table']/tbody/tr/td/small[@class='summary_timespan thirty_day']/allText()")
	private String activityDayTime = "";
	@ExtractBy("//div[@class='span6']/div[@class='well']/table[@id='activity_table']/tbody/tr/td/ul[@id='thirty_day_summary']/li/big/text()")
	private List<String> dayActivityInfos = new ArrayList<String>();
	private int daysCommitNumber = 0;
	private int daysContributorNumber = 0;
	@ExtractBy("//div[@class='span6']/div[@class='well']/table[@id='activity_table']/tbody/tr/td/ul[@id='thirty_day_summary']/li/span[@class='clear small']/a/allText()")
	private String newContributor = "";
	private int newContriNum = 0;

	@ExtractBy("//div[@class='span6']/div[@class='well']/table[@id='activity_table']/td/small[@class='summary_timespan']/allText()")
	private String activityMonthTime = "";
	@ExtractBy("//div[@class='span6']/div[@class='well']/table[@id='activity_table']/tbody/tr/td/ul[@class='unstyled nutshell']/li/big/text()")
	private List<String> monthActivityInfos = new ArrayList<String>();
	private int monthsCommitNumber = 0;
	private int monthsContributorNumber = 0;
	@ExtractBy("//div[@class='span6']/div[@class='well']/table[@id='activity_table']/tbody/tr/td/ul[@class='unstyled nutshell']/li/span[@class='small clear']/allText()")
	private List<String> allTrend = new ArrayList<String>();
	private String theCommitTrend = "";

	private String theContriTrend = "";

	// community
	@ExtractBy("//div[@class='proj_community_ratings']/div/span[@style='margin-left: 8px']/allText()")
	private String rateInfo = "";

	@ExtractBy("//div[@class='proj_community_ratings']/div/div[@class='clear']/span[@class='float_left']/allText()")
	private String rateLevel = "";
	@ExtractBy("//table[@id='recent_committers_table']/tbody/tr/td[@class='recent_committers']/a/allText()")
	private List<String> RecenctContributors = new ArrayList<String>();
	private String ReContributor = "";

	// buttom informations
//	@ExtractBy("//div[@id='projects_show_page']/div[@class='full-width mezzo margin_left_20 margin_right_20 margin_top_15']/div[@class='bottom-nav sidebar_project']/div[@class='actions']")
//	private List<String>linkInfos = null;
//	private List<String> firstLinks = null;
//	private String newsLink = null;
//	private String langLink = null;
//	private String commitsLink = null;
//	@ExtractBy("//div[@id='projects_show_page']/div[@class='full-width mezzo margin_left_20 margin_right_20 margin_top_15']/div[@class='bottom-nav sidebar_project']/div[@class='actions']/ul[@class='nav nav-stacked nav-pills']/li[@class=' ']/a/text()")
//	private List<String> otherLinks = null;
//	private String settingLink = null;
//	private String sharingwidgetsLink = null;
//	private String relatedprojectsLink = null;
//	private String costestLink = null;
//	private String contributorLink = null;
//	
	/**
	 * just for test
	 */
	
    private String ansTmp = null;
	public String getAnsTmp() {
		return ansTmp;
	}

	public void setAnsTmp(String ansTmp) {
		this.ansTmp = ansTmp;
	}
//
//	private Elements links = null ;
//	public Elements getLinks() {
//		return links;
//	}
//
//	public void setLinks(Elements links) {
//		this.links = links;
//	}

	private String collectTime;
	// @ExtractByUrl()
	private String url;
	private String urlMd5;
	private String pageMd5;
	private int history = 0;
	private String crawlerTime = "";
	private String projectUrl ="" ;
	
	public void afterProcess(Page page) {

		this.urlMd5 = DigestUtils.md5Hex(page.getPageUrl());
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		this.collectTime = bartDateFormat.format(new Date());

		Document doc = page.getHtml().getDocument();

		// use_count
		Elements useCountLabel = doc
				.select("div#widgets div[itemprop=interactionCount] div.float_right div.use_count a");
		if (useCountLabel.size() > 0) {
			String strUseCount = useCountLabel.get(0).text();
			this.useCount = Integer.parseInt(StringUtils.remove(strUseCount,
					','));
		}

		// tags
		Elements tags = doc.select("div#project_tags p a");
		String tag = null;
		List<String> listTags = new ArrayList<String>();
		for (Element e : tags) {
			tag = e.text();
			listTags.add(tag);
		}
		this.tags = StringHandler.combineTags(listTags);

		// quick reference
		// Elements quickRefs = doc.select("div.span6 div.well dl.");
		Elements quickRefs = doc.select("div.span6 div.well dl");
		if (quickRefs.size() > 0) {
			Element quickRef = quickRefs.get(0);
			handleQuickRef(quickRef);
		}

		// nutshell
//		if (codeInfos.size() > 0) {
//			String nutShell0 = codeInfos.get(0);
//			handleNutShell0(nutShell0);
//			String nutShell1 = codeInfos.get(1);
//			handleNutShell1(nutShell1);
//			String nutShell2 = codeInfos.get(2);
//			handleNutShell2(nutShell2);
//			String nutShell3 = codeInfos.get(3);
//			handleNutShell3(nutShell3, page.getTime());
//		}
		
		int tmplength = codeInfos.size();
		if(tmplength>0){
			if(tmplength == 1){
				String nutShell0 = codeInfos.get(0);
				handleNutShell0(nutShell0);
			}else if(tmplength == 2){
				String nutShell0 = codeInfos.get(0);
				handleNutShell0(nutShell0);
				String nutShell1 = codeInfos.get(1);
				handleNutShell1(nutShell1);
			}else if(tmplength == 3){
				String nutShell0 = codeInfos.get(0);
				handleNutShell0(nutShell0);
				String nutShell1 = codeInfos.get(1);
				handleNutShell1(nutShell1);
				String nutShell2 = codeInfos.get(2);
				handleNutShell2(nutShell2);
			}else{
				String nutShell0 = codeInfos.get(0);
				handleNutShell0(nutShell0);
				String nutShell1 = codeInfos.get(1);
				handleNutShell1(nutShell1);
				String nutShell2 = codeInfos.get(2);
				handleNutShell2(nutShell2);
				String nutShell3 = codeInfos.get(3);
				handleNutShell3(nutShell3, page.getTime());
			}
		}
	    
		
		List<String> eList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		// language
		for (int i = 0; i < languages.size(); i++) {
			Element e = Jsoup.parse(languages.get(i));
			Element eValue = Jsoup.parse(percentages.get(i));
			// String strE = e.text();
			eList.add(e.text());
			// String strValue = eValue.text();
			valueList.add(eValue.text());
		}
		 this.languagePercentages = StringHandler.assemblyOSSEANMap(eList,
		 valueList);

		// activity
		String strtmp1 = "";
		String strtmp2 = "";
		String strtmp3 = "";
		String strtmp4 = "";
		// this.dCommitNumber = Integer.parseInt(dayActivityInfos.get(0));
		// this.dContributorNumber = Integer.parseInt(dayActivityInfos.get(1));
//		if (dayActivityInfos.size() > 0) {
//			strtmp1 = dayActivityInfos.get(0);
//			this.daysCommitNumber = getInt(strtmp1);
//			strtmp2 = dayActivityInfos.get(1);
//			this.daysContributorNumber = getInt(strtmp2);
//			strtmp3 = monthActivityInfos.get(0);
//			this.monthsCommitNumber = getInt(strtmp3);
//			strtmp4 = monthActivityInfos.get(1);
//			this.monthsContributorNumber = getInt(strtmp4);
//
//		}
		
		int length = dayActivityInfos.size();
		if(length > 0){
			if(length == 1){
				strtmp1 = dayActivityInfos.get(0);
				this.daysCommitNumber = getInt(strtmp1);
			}else{
				strtmp1 = dayActivityInfos.get(0);
				this.daysCommitNumber = getInt(strtmp1);
				strtmp2 = dayActivityInfos.get(1);
				this.daysContributorNumber = getInt(strtmp2);
			}
		}
		
		int length1 = monthActivityInfos.size();
		if(length1 > 0){
			if(length1==1){
				strtmp3 = monthActivityInfos.get(0);
				this.monthsCommitNumber = getInt(strtmp3);
			}else{
				strtmp3 = monthActivityInfos.get(0);
				this.monthsCommitNumber = getInt(strtmp3);
				strtmp4 = monthActivityInfos.get(1);
				this.monthsContributorNumber = getInt(strtmp4);
			}
		}
		
		this.newContriNum = getInt(this.newContributor);
//		this.theCommitTrend = allTrend.get(0);
//		this.theContriTrend = allTrend.get(1);
		
		if(allTrend.size()>0){
			if(allTrend.size() == 1){
				this.theCommitTrend = allTrend.get(0);
			}else{
				this.theCommitTrend = allTrend.get(0);
				this.theContriTrend = allTrend.get(1);
			}
		}
		
		// community
		// this.rateNum = getInt(this.rateInfo);
		this.ReContributor = StringHandler
				.combineTags(this.RecenctContributors);		
		// button informations
//		String headHref = "https://www.openhub.net";
//		Document docTmp = Jsoup.parse(this.linkInfos.get(0));
//		this.links = docTmp.getElementsByTag("a");
//		
//		for(Element link : links){
//			String url = headHref + link.attr("href");
//			ansTmp += url +"**";
//		}
//		
			

//		if (this.firstLinks.size() > 0) {
//
//			this.newsLink = tmphref + this.firstLinks.get(0);
//			this.langLink = tmphref + this.firstLinks.get(1);
//
//			this.commitsLink = tmphref + this.firstLinks.get(2);
//			this.settingLink = tmphref + this.otherLinks.get(0);
//			
//			this.sharingwidgetsLink = tmphref + this.otherLinks.get(1);
//			this.relatedprojectsLink = tmphref + this.otherLinks.get(2);
//			this.costestLink = tmphref + this.otherLinks.get(3);
//			this.contributorLink = tmphref + this.otherLinks.get(4);
//		}
		
		this.rateInfo = this.rateInfo.trim();
		this.rateLevel = this.rateLevel.trim();
		//专门处理projectlinks的前后空格
		this.projectLinks = this.projectLinks.trim();
		if(this.projectLinks.contains(" "))
		{
			this.projectLinks = this.projectLinks.replace(" ", " ");
			this.projectLinks = this.projectLinks.trim();
		}
		this.activity = this.activity.trim();
		
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.crawlerTime =s.format(page.getTime());
		this.projectUrl = page.getPageUrl();
		
	}



	@Override
	public void validate(Page page) {
		// TODO Auto-generated method stub	
		if(StringHandler.isAtLeastOneBlank(this.name,this.activity,this.description,this.licenses)){
			page.setResultSkip(this, true);
			return;
		}
	}

	private void handleQuickRef(Element quickRef) {
		Elements itemNames = quickRef.select("dt");
		Elements itemValues = quickRef.select("dd");
		Element e = null;
		Element eValue = null;
		for (int i = 0; i < itemNames.size(); i++) {
			e = itemNames.get(i);
			eValue = itemValues.get(i);
			String refName = e.text();
			switch (refName) {
			case "Organization:": {
				this.organization = eValue.text();
				break;
			}
			case "Project Links:": {
				Elements links = eValue.select("a");
				String[] tmp = new String[links.size()];
				Element ele = null;
				for (int j = 0; j < links.size(); j++) {
					ele = links.get(j);
					tmp[j] = ele.text() + Seperator.SOURCE_SEPERATOR
							+ ele.attr("href");
				}
				this.projectLinks = StringUtils.join(tmp,
						Seperator.OSSEAN_SEPERATOR);
				break;
			}
			case "Code Locations:": {
				Elements locs = eValue.select("a");
				if (locs.size() == 0) {
					this.codeLocation = eValue.text();
				} else {
					Element link = locs.get(0);
					this.codeLocation = link.text()
							+ Seperator.SOURCE_SEPERATOR + link.attr("href");
				}

				break;
			}
			case "Licenses:": {
				Elements links = eValue.select("a");
				List<String> listLicenses = new ArrayList<String>();
				// String[] tmp = new String[links.size()];
				for (int j = 0; j < links.size(); j++) {
					listLicenses.add(links.get(j).text());
					// tmp[j] = links.get(j).text();
				}
				this.licenses = StringHandler.combineTags(listLicenses);
				break;
			}
			case "Similar Projects:": {
				// System.out.println(eValue);
				Elements projects = eValue.select("td[width=49%]");
				// System.out.println(projects.size());
				String[] tmp = new String[projects.size()];
				Element ele = null;
				for (int j = 0; j < projects.size(); j++) {
					ele = projects.get(j);
					Element project = ele.select("a").get(0);
					tmp[j] = project.text() + Seperator.SOURCE_SEPERATOR
							+ project.attr("href");
				}
				this.similarProjects = StringUtils.join(tmp,
						Seperator.OSSEAN_SEPERATOR);
				break;
			}
			case "Managers:": {
				if ("Become the first manager for BugSystem".equals(eValue
						.text())) {
					break;
				}
				Elements users = eValue.select("a");
				String[] tmp = new String[users.size()];
				Element ele = null;
				for (int j = 0; j < users.size(); j++) {
					ele = users.get(j);
					tmp[j] = ele.text() + Seperator.SOURCE_SEPERATOR
							+ ele.attr("href");
				}
				this.managers = StringUtils.join(tmp,
						Seperator.OSSEAN_SEPERATOR);
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	private Elements getAElements(String code) {
		Document doc = Jsoup.parse(code);
		Elements eles = doc.select("a");
		return eles;
	}

	private void handleNutShell0(String nutshell) {
		Elements eles = getAElements(nutshell);
		Element ele = null;
		ele = eles.get(0);
		this.commitNum = getInt(ele.text());
		ele = eles.get(1);
		this.contributorNum = getInt(ele.text());
		ele = eles.get(2);
		this.codeLinesNum = getInt(ele.text());
	}

	private void handleNutShell1(String nutshell) {
		Elements eles = getAElements(nutshell);
		Element ele = null;
		ele = eles.get(0);
		this.mostWrittenIn = StringHandler.removeHeader(ele.text(),
				OpenHubProject_Model.mostWrittenHeader).trim();
		if (eles.size() > 1) {
			ele = eles.get(1);
			// System.out.println(ele.text());
			this.commentsPercentage = StringHandler.removeTail(ele.text(),
					OpenHubProject_Model.commentsPercentageTail).trim();
			// System.out.println(this.commentsPercentage);
			this.commentsPercentage = StringHandler.removeIndefiniteArticles(
					this.commentsPercentage).trim();
			// System.out.println(this.commentsPercentage);
		}
	}

	private void handleNutShell2(String nutshell) {
		Elements eles = getAElements(nutshell);
		Element ele = null;
		if(eles.size()>0){
//		ele = eles.get(0);
//		this.codebaseStatus = ele.text();
//		ele = eles.get(1);
//		this.teamScale = ele.text();
//		ele = eles.get(2);
//		this.commitStatus = ele.text();
		if(eles.size()==1){
			ele = eles.get(0);
			this.codebaseStatus = ele.text();
		}else if(eles.size() ==2){
			ele = eles.get(0);
			this.codebaseStatus = ele.text();
			ele = eles.get(1);
			this.teamScale = ele.text();
		}else{
			ele = eles.get(0);
			this.codebaseStatus = ele.text();
			ele = eles.get(1);
			this.teamScale = ele.text();
			ele = eles.get(2);
			this.commitStatus = ele.text();
		}
		}
	}

	private void handleNutShell3(String nutshell,Date date) {
		Elements eles = getAElements(nutshell);
		Element ele = null;
		if(eles.size()>0){
			if(eles.size() == 1){
				ele = eles.get(0);
				this.estimateEffortTime = StringHandler.removeTail(ele.text(),
						OpenHubProject_Model.estimateEffortTimeTail).trim();
			}else if(eles.size() ==2){
				ele = eles.get(0);
				this.estimateEffortTime = StringHandler.removeTail(ele.text(),
						OpenHubProject_Model.estimateEffortTimeTail).trim();
				ele = eles.get(1);
				String firstCommitAt = StringHandler.removeHeader(ele.text(),
						OpenHubProject_Model.firstCommitTimeHeader).trim();
				ansTmp = handleDateAt(firstCommitAt).toString();
				this.firstCommitTime = handleDateAt(firstCommitAt);
			}else{
				ele = eles.get(0);
				this.estimateEffortTime = StringHandler.removeTail(ele.text(),
						OpenHubProject_Model.estimateEffortTimeTail).trim();
				ele = eles.get(1);
				String firstCommitAt = StringHandler.removeHeader(ele.text(),
						OpenHubProject_Model.firstCommitTimeHeader).trim();
				ansTmp = handleDateAt(firstCommitAt).toString();
				this.firstCommitTime = handleDateAt(firstCommitAt);
				ele = eles.get(2);
				String lastCommitAt = StringHandler.removeHeader(ele.text(),
						OpenHubProject_Model.lastCommitTimeHeader).trim();
				// System.out.println(lastCommitAt);
				lastCommitAt = StringHandler.removePreposition(lastCommitAt);
				// SimpleDateFormat simpleDateFormat = new
				// SimpleDateFormat("yyy-MM-dd HH:mm:ss");				
				//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
				this.lastCommitTime = DateHandler.stringToDate(DateHandler.formatAllTypeDate(lastCommitAt,date));//handleDateBefore(lastCommitAt);
			}
		}
	}

	private int getInt(String in) {
		return StringHandler.extractIntFromString(in);
	}

	private Date handleDateAt(String strDate) {
		String[] date = strDate.split(",");
		String strMonth = date[0];
		String strYear = date[1];
		switch (strMonth) {
		case "January": {
			strMonth = "01";
			break;
		}
		case "February": {
			strMonth = "02";
			break;
		}
		case "March": {
			strMonth = "03";
			break;
		}
		case "April": {
			strMonth = "04";
			break;
		}
		case "May": {
			strMonth = "05";
			break;
		}
		case "June": {
			strMonth = "06";
			break;
		}
		case "July": {
			strMonth = "07";
			break;
		}
		case "August": {
			strMonth = "08";
			break;
		}
		case "September": {
			strMonth = "09";
			break;
		}
		case "October": {
			strMonth = "10";
			break;
		}
		case "November": {
			strMonth = "11";
			break;
		}
		case "December": {
			strMonth = "12";
			break;
		}
		default: {
			strMonth = "00";
			break;
		}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String dateTmp = strYear + "-" + strMonth;
		Date rt = null;
		try {
			rt = sdf.parse(dateTmp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rt;
	}

	public static String getMostWrittenHeader() {
		return mostWrittenHeader;
	}

	public static void setMostWrittenHeader(String mostWrittenHeader) {
		OpenHubProject_Model.mostWrittenHeader = mostWrittenHeader;
	}

	public static String getCommentsPercentageTail() {
		return commentsPercentageTail;
	}

	public static void setCommentsPercentageTail(String commentsPercentageTail) {
		OpenHubProject_Model.commentsPercentageTail = commentsPercentageTail;
	}

	public static String getCodebaseStatusHeaderTail() {
		return codebaseStatusHeaderTail;
	}

	public static void setCodebaseStatusHeaderTail(
			String codebaseStatusHeaderTail) {
		OpenHubProject_Model.codebaseStatusHeaderTail = codebaseStatusHeaderTail;
	}

	public static String getTeamScaleTail() {
		return teamScaleTail;
	}

	public static void setTeamScaleTail(String teamScaleTail) {
		OpenHubProject_Model.teamScaleTail = teamScaleTail;
	}

	public static String getCommitStatusTail() {
		return commitStatusTail;
	}

	public static void setCommitStatusTail(String commitStatusTail) {
		OpenHubProject_Model.commitStatusTail = commitStatusTail;
	}

	public static String getEstimateEffortTimeTail() {
		return estimateEffortTimeTail;
	}

	public static void setEstimateEffortTimeTail(String estimateEffortTimeTail) {
		OpenHubProject_Model.estimateEffortTimeTail = estimateEffortTimeTail;
	}

	public static String getFirstCommitTimeHeader() {
		return firstCommitTimeHeader;
	}

	public static void setFirstCommitTimeHeader(String firstCommitTimeHeader) {
		OpenHubProject_Model.firstCommitTimeHeader = firstCommitTimeHeader;
	}

	public static String getLastCommitTimeHeader() {
		return lastCommitTimeHeader;
	}

	public static void setLastCommitTimeHeader(String lastCommitTimeHeader) {
		OpenHubProject_Model.lastCommitTimeHeader = lastCommitTimeHeader;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getStrUseCount() {
		return strUseCount;
	}

	public void setStrUseCount(String strUseCount) {
		this.strUseCount = strUseCount;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getProjectLinks() {
		return projectLinks;
	}

	public void setProjectLinks(String projectLinks) {
		this.projectLinks = projectLinks;
	}

	public String getCodeLocation() {
		return codeLocation;
	}

	public void setCodeLocation(String codeLocation) {
		this.codeLocation = codeLocation;
	}

	public String getLicenses() {
		return licenses;
	}

	public void setLicenses(String licenses) {
		this.licenses = licenses;
	}

	public String getSimilarProjects() {
		return similarProjects;
	}

	public void setSimilarProjects(String similarProjects) {
		this.similarProjects = similarProjects;
	}

	public String getManagers() {
		return managers;
	}

	public void setManagers(String managers) {
		this.managers = managers;
	}

	public List<String> getCodeInfos() {
		return codeInfos;
	}

	public void setCodeInfos(List<String> codeInfos) {
		this.codeInfos = codeInfos;
	}

	public int getCommitNum() {
		return commitNum;
	}

	public void setCommitNum(int commitNum) {
		this.commitNum = commitNum;
	}

	public int getContributorNum() {
		return contributorNum;
	}

	public void setContributorNum(int contributorNum) {
		this.contributorNum = contributorNum;
	}

	public int getCodeLinesNum() {
		return codeLinesNum;
	}

	public void setCodeLinesNum(int codeLinesNum) {
		this.codeLinesNum = codeLinesNum;
	}

	public String getMostWrittenIn() {
		return mostWrittenIn;
	}

	public void setMostWrittenIn(String mostWrittenIn) {
		this.mostWrittenIn = mostWrittenIn;
	}

	public String getCommentsPercentage() {
		return commentsPercentage;
	}

	public void setCommentsPercentage(String commentsPercentage) {
		this.commentsPercentage = commentsPercentage;
	}

	public String getCodebaseStatus() {
		return codebaseStatus;
	}

	public void setCodebaseStatus(String codebaseStatus) {
		this.codebaseStatus = codebaseStatus;
	}

	public String getTeamScale() {
		return teamScale;
	}

	public void setTeamScale(String teamScale) {
		this.teamScale = teamScale;
	}

	public String getCommitStatus() {
		return commitStatus;
	}

	public void setCommitStatus(String commitStatus) {
		this.commitStatus = commitStatus;
	}

	public String getEstimateEffortTime() {
		return estimateEffortTime;
	}

	public void setEstimateEffortTime(String estimateEffortTime) {
		this.estimateEffortTime = estimateEffortTime;
	}

	public Date getFirstCommitTime() {
		return firstCommitTime;
	}

	public void setFirstCommitTime(Date firstCommitTime) {
		this.firstCommitTime = firstCommitTime;
	}

	public Date getLastCommitTime() {
		return lastCommitTime;
	}

	public void setLastCommitTime(Date lastCommitTime) {
		this.lastCommitTime = lastCommitTime;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public List<String> getPercentages() {
		return percentages;
	}

	public void setPercentages(List<String> percentages) {
		this.percentages = percentages;
	}

	public String getLanguagePercentages() {
		return languagePercentages;
	}

	public void setLanguagePercentages(String languagePercentages) {
		this.languagePercentages = languagePercentages;
	}

	public String getActivityDayTime() {
		return activityDayTime;
	}

	public void setActivityDayTime(String activityDayTime) {
		this.activityDayTime = activityDayTime;
	}

	public List<String> getDayActivityInfos() {
		return dayActivityInfos;
	}

	public void setDayActivityInfos(List<String> dayActivityInfos) {
		this.dayActivityInfos = dayActivityInfos;
	}

	public int getDaysCommitNumber() {
		return daysCommitNumber;
	}

	public void setDaysCommitNumber(int daysCommitNumber) {
		this.daysCommitNumber = daysCommitNumber;
	}

	public int getDaysContributorNumber() {
		return daysContributorNumber;
	}

	public void setDaysContributorNumber(int daysContributorNumber) {
		this.daysContributorNumber = daysContributorNumber;
	}

	public String getNewContributor() {
		return newContributor;
	}

	public void setNewContributor(String newContributor) {
		this.newContributor = newContributor;
	}

	public int getNewContriNum() {
		return newContriNum;
	}

	public void setNewContriNum(int newContriNum) {
		this.newContriNum = newContriNum;
	}

	public String getActivityMonthTime() {
		return activityMonthTime;
	}

	public void setActivityMonthTime(String activityMonthTime) {
		this.activityMonthTime = activityMonthTime;
	}

	public List<String> getMonthActivityInfos() {
		return monthActivityInfos;
	}

	public void setMonthActivityInfos(List<String> monthActivityInfos) {
		this.monthActivityInfos = monthActivityInfos;
	}

	public int getMonthsCommitNumber() {
		return monthsCommitNumber;
	}

	public void setMonthsCommitNumber(int monthsCommitNumber) {
		this.monthsCommitNumber = monthsCommitNumber;
	}

	public int getMonthsContributorNumber() {
		return monthsContributorNumber;
	}

	public void setMonthsContributorNumber(int monthsContributorNumber) {
		this.monthsContributorNumber = monthsContributorNumber;
	}

	public List<String> getAllTrend() {
		return allTrend;
	}

	public void setAllTrend(List<String> allTrend) {
		this.allTrend = allTrend;
	}

	public String getTheCommitTrend() {
		return theCommitTrend;
	}

	public void setTheCommitTrend(String theCommitTrend) {
		this.theCommitTrend = theCommitTrend;
	}

	public String getTheContriTrend() {
		return theContriTrend;
	}

	public void setTheContriTrend(String theContriTrend) {
		this.theContriTrend = theContriTrend;
	}

	public String getRateInfo() {
		return rateInfo;
	}

	public void setRateInfo(String rateInfo) {
		this.rateInfo = rateInfo;
	}

	public String getRateLevel() {
		return rateLevel;
	}

	public void setRateLevel(String rateLevel) {
		this.rateLevel = rateLevel;
	}

	public List<String> getRecenctContributors() {
		return RecenctContributors;
	}

	public void setRecenctContributors(List<String> recenctContributors) {
		RecenctContributors = recenctContributors;
	}

	public String getReContributor() {
		return ReContributor;
	}

	public void setReContributor(String reContributor) {
		ReContributor = reContributor;
	}
	
//	public List<String> getFirstLinks() {
//		return firstLinks;
//	}
//
//	public void setFirstLinks(List<String> firstLinks) {
//		this.firstLinks = firstLinks;
//	}
//
//	public String getNewsLink() {
//		return newsLink;
//	}
//
//	public void setNewsLink(String newsLink) {
//		this.newsLink = newsLink;
//	}
//
//	public String getLangLink() {
//		return langLink;
//	}
//
//	public void setLangLink(String langLink) {
//		this.langLink = langLink;
//	}
//
//	public String getCommitsLink() {
//		return commitsLink;
//	}
//
//	public void setCommitsLink(String commitsLink) {
//		this.commitsLink = commitsLink;
//	}
//
//	public List<String> getOtherLinks() {
//		return otherLinks;
//	}
//
//	public void setOtherLinks(List<String> otherLinks) {
//		this.otherLinks = otherLinks;
//	}
//
//	public String getSettingLink() {
//		return settingLink;
//	}
//
//	public void setSettingLink(String settingLink) {
//		this.settingLink = settingLink;
//	}
//
//	public String getSharingwidgetsLink() {
//		return sharingwidgetsLink;
//	}
//
//	public void setSharingwidgetsLink(String sharingwidgetsLink) {
//		this.sharingwidgetsLink = sharingwidgetsLink;
//	}
//
//	public String getRelatedprojectsLink() {
//		return relatedprojectsLink;
//	}
//
//	public void setRelatedprojectsLink(String relatedprojectsLink) {
//		this.relatedprojectsLink = relatedprojectsLink;
//	}
//
//	public String getCostestLink() {
//		return costestLink;
//	}
//
//	public void setCostestLink(String costestLink) {
//		this.costestLink = costestLink;
//	}
//
//	public String getContributorLink() {
//		return contributorLink;
//	}
//
//	public void setContributorLink(String contributorLink) {
//		this.contributorLink = contributorLink;
//	}

	public String getProjectUrl() {
		return projectUrl;
	}

	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}

	public String getCrawlerTime() {
		return crawlerTime;
	}

	public void setCrawlerTime(String crawlerTime) {
		this.crawlerTime = crawlerTime;
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
	}

	public String getUrlMd5() {
		return urlMd5;
	}

	public void setUrlMd5(String urlMd5) {
		this.urlMd5 = urlMd5;
	}

	public String getPageMd5() {
		return pageMd5;
	}

	public void setPageMd5(String pageMd5) {
		this.pageMd5 = pageMd5;
	}

	public int getHistory() {
		return history;
	}

	public void setHistory(int history) {
		this.history = history;
	}
//	public List<String> getLinkInfos() {
//		return linkInfos;
//	}
//
//	public void setLinkInfos(List<String> linkInfos) {
//		this.linkInfos = linkInfos;
//	}
}
