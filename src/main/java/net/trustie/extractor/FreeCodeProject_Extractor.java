package net.trustie.extractor;

import java.sql.SQLException;

import net.trustie.downloader.DataBasePageErrorOutPut;
import net.trustie.downloader.GenerateRawPage;
import net.trustie.model.FreeCode_Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import core.PageModelPipeline;
import core.Site;
import extension.OsseanExtractor;

@Component
public class FreeCodeProject_Extractor {
	@SuppressWarnings("rawtypes")
	@Qualifier("freeCodePipeline")
	@Autowired
	private PageModelPipeline modelPipeline;
	
	@Qualifier("generateRawPage")
	@Autowired
	private GenerateRawPage generateRawPage;
	
	@Qualifier("errorPageToDB")
	@Autowired
	private DataBasePageErrorOutPut dbPageErrorOutPut;	

	public void begin() {
		generateRawPage.setTable("freecode_html_detail");
		dbPageErrorOutPut.setTableName("freecode_error_page");		
		OsseanExtractor
				.create(Site.me().setResultNum(100), modelPipeline,
						FreeCode_Model.class).setUUID("freecode")
				.setDownloader(generateRawPage).start();
	}
	
	public static void main(String[] args) throws SQLException {
		ApplicationContext aContext = new ClassPathXmlApplicationContext(
				"classpath:/spring/applicationContext*.xml");

		final FreeCodeProject_Extractor extractor = aContext
				.getBean(FreeCodeProject_Extractor.class);

		extractor.begin();
	}
}
