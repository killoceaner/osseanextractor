package net.trustie.extractor;

import java.sql.SQLException;
import net.trustie.downloader.DataBasePageErrorOutPut;
import net.trustie.downloader.GenerateRawPage;
import net.trustie.model.IteyeAsk_Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import core.PageModelPipeline;
import core.Site;
import extension.OsseanExtractor;

@Component
public class IteyeAsk_Extractor {
	@SuppressWarnings("rawtypes")
	@Qualifier("itEyeAskPipeline")
	@Autowired
	private PageModelPipeline modelPipeline;

	@Qualifier("generateRawPage")
	@Autowired
	private GenerateRawPage generateRawPage;

	@Qualifier("errorPageToDB")
	@Autowired
	private DataBasePageErrorOutPut dbPageErrorOutPut;
	
	public void begin() {
		generateRawPage.setTable("iteye_ask_html_detail");
		dbPageErrorOutPut.setTableName("iteye_ask_error_page");

		OsseanExtractor
				.create(Site.me().setResultNum(100), modelPipeline,
						IteyeAsk_Model.class).setUUID("iteye_ask")
				.setDownloader(generateRawPage)
				.setPageErrorOutPut(dbPageErrorOutPut).start();
	}

	public static void main(String[] args) throws SQLException {
		ApplicationContext aContext = new ClassPathXmlApplicationContext(
				"classpath:/spring/applicationContext*.xml");

		final IteyeAsk_Extractor extractor = aContext
				.getBean(IteyeAsk_Extractor.class);

		extractor.begin();
	}
	
}
