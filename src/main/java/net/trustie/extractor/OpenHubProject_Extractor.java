package net.trustie.extractor;

import java.sql.SQLException;

import net.trustie.downloader.DataBasePageErrorOutPut;
import net.trustie.downloader.GenerateRawPage;
import net.trustie.model.OpenHubProject_Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import core.PageModelPipeline;
import core.Site;
import extension.OsseanExtractor;


@Component
public class OpenHubProject_Extractor {
	@SuppressWarnings("rawtypes")	
	@Qualifier("OpenHubProjectPipeline")
	@Autowired
	private PageModelPipeline modelPipeline;
	
	@Qualifier("generateRawPage")
	@Autowired
	private GenerateRawPage generateRawPage;
	
	@Qualifier("errorPageToDB")
	@Autowired
	private DataBasePageErrorOutPut dbPageErrorOutPut;
	
	
	public void begin() {
		generateRawPage.setTable("openhub_html_detail");
		dbPageErrorOutPut.setTableName("openhub_error_page");

		OsseanExtractor
				.create(Site.me().setResultNum(100), modelPipeline,
						OpenHubProject_Model.class).setUUID("OpenHubProject")
				.setDownloader(generateRawPage)
				.setPageErrorOutPut(dbPageErrorOutPut).start();
	}

	public static void main(String[] args) throws SQLException {
		ApplicationContext aContext = new ClassPathXmlApplicationContext(
				"classpath:/spring/applicationContext*.xml");

		final OpenHubProject_Extractor extractor = aContext
				.getBean(OpenHubProject_Extractor.class);

		extractor.begin();
	}
	
}
