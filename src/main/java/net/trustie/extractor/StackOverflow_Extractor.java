package net.trustie.extractor;

import java.sql.SQLException;

import net.trustie.downloader.DataBasePageErrorOutPut;
import net.trustie.downloader.GenerateRawPage;
import net.trustie.model.StackOverflow_Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import core.PageModelPipeline;
import core.Site;
import extension.OsseanExtractor;

@Component
public class StackOverflow_Extractor {
	@SuppressWarnings("rawtypes")
	@Qualifier("stackOverFlowPipeline")
	@Autowired
	private PageModelPipeline modelPipeline;

	@Qualifier("generateRawPage")
	@Autowired
	private GenerateRawPage generateRawPage;

	@Qualifier("errorPageToDB")
	@Autowired
	private DataBasePageErrorOutPut dbPageErrorOutPut;

	public void begin() {
		generateRawPage.setTable("stackoverflow_html_detail");
		dbPageErrorOutPut.setTableName("stackoverflow_error_page");

		OsseanExtractor
				.create(Site.me().setResultNum(100), modelPipeline,
						StackOverflow_Model.class).setUUID("stackoverflow_q")
				.setDownloader(generateRawPage)
				.setPageErrorOutPut(dbPageErrorOutPut).start();
	}

	public static void main(String[] args) throws SQLException {
		ApplicationContext aContext = new ClassPathXmlApplicationContext(
				"classpath:/spring/applicationContext*.xml");

		final StackOverflow_Extractor extractor = aContext
				.getBean(StackOverflow_Extractor.class);

		extractor.begin();
	}

}
