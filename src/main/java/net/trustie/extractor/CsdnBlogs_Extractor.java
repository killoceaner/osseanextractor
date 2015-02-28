package net.trustie.extractor;

import java.sql.SQLException;

import net.trustie.downloader.DataBasePageErrorOutPut;
import net.trustie.downloader.GenerateRawPage;
import net.trustie.model.CsdnBlogs_Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import core.PageModelPipeline;
import core.Site;
import extension.OsseanExtractor;

@Component
public class CsdnBlogs_Extractor {

	@SuppressWarnings("rawtypes")
	@Qualifier("csdnBlogPipeline")
	@Autowired
	private PageModelPipeline modelPipeline;

	@Qualifier("generateRawPage")
	@Autowired
	private GenerateRawPage generateRawPage;

	@Qualifier("errorPageToDB")
	@Autowired
	private DataBasePageErrorOutPut dbPageErrorOutPut;

	public void begin() {
		generateRawPage.setTable("csdn_blog_html_detail");
		dbPageErrorOutPut.setTableName("csdn_blog_error_page");

		OsseanExtractor
				.create(Site.me().setResultNum(100), modelPipeline,
						CsdnBlogs_Model.class).setUUID("csdn_blogs")
				.setDownloader(generateRawPage)
				.setPageErrorOutPut(dbPageErrorOutPut).start();
	}

	public static void main(String[] args) throws SQLException {
		ApplicationContext aContext = new ClassPathXmlApplicationContext(
				"classpath:/spring/applicationContext*.xml");

		final CsdnBlogs_Extractor extractor = aContext
				.getBean(CsdnBlogs_Extractor.class);

		extractor.begin();
	}
}