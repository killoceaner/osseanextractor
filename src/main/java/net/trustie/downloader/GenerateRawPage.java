package net.trustie.downloader;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import extension.PageDownloader;
import extension.RawPage;

@Component("generateRawPage")
public class GenerateRawPage implements PageDownloader{

	@Resource
	private PageDao pagedao;

	private String tableName;

	@Override
	public List<RawPage> downloadPages(int begin_id, int end_id) {
		return pagedao.selectScourcePage(this.tableName, begin_id, end_id);
	}	

	@Override
	public int getMinId() {
		return pagedao.getMinId(this.tableName);
	}

	public String getTableName() {
		return tableName;
	}

	public GenerateRawPage setTable(String table) {
		this.tableName = table;
		return this;
	}

}
