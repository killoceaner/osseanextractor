package net.trustie.extractor;

import java.sql.SQLException;

import net.trustie.downloader.DataBasePageErrorOutPut;
import net.trustie.downloader.GenerateRawPage;
import net.trustie.model.CNblogsQ_Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import core.PageModelPipeline;
import core.Site;
import extension.OsseanExtractor;

@Component
public class CNblogQSolve_Extractor {
	@SuppressWarnings("rawtypes")
	@Qualifier("cnblogsQPipeline")
	@Autowired
	private PageModelPipeline modelPipeline;

	@Qualifier("generateRawPage")
	@Autowired
	private GenerateRawPage generateRawPage;

	@Qualifier("errorPageToDB")
	@Autowired
	private DataBasePageErrorOutPut dbPageErrorOutPut;

	public void begin() {
		generateRawPage.setTable("cnblogs_q_solved_html_detail");
		dbPageErrorOutPut.setTableName("cnblogs_q_solved_error_page");

		OsseanExtractor
				.create(Site.me().setResultNum(100), modelPipeline,
						CNblogsQ_Model.class).setUUID("cnblog_q_solve")
				.setDownloader(generateRawPage)
				.setPageErrorOutPut(dbPageErrorOutPut).start();
	}

	public static void main(String[] args) throws SQLException {
		ApplicationContext aContext = new ClassPathXmlApplicationContext(
				"classpath:/spring/applicationContext*.xml");

		final CNblogQSolve_Extractor extractor = aContext
				.getBean(CNblogQSolve_Extractor.class);

		extractor.begin();
	}

}
