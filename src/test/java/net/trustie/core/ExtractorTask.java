package net.trustie.core;


import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;

public class ExtractorTask implements Task {
	private Site site;

	public ExtractorTask(){
		this.site= Site.me().setRetryTimes(5).setCycleRetryTimes(5).setTimeOut(2000).setSleepTime(2000).setCharset("utf-8");
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
    
	@Override
	public String getUUID() {
		// TODO Auto-generated method stub
		return null;
	}

}
