package net.trustie.downloader;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import extension.PageErrorOutPut;
import extension.RawPage;

@Component("errorPageToDB")
public class DataBasePageErrorOutPut implements PageErrorOutPut {

	@Resource
	private ErrorPageDao errPageDao;

	private String tableName;

	private String errorInfo = "Detail";

	@Override
	public void returnErrorPage(RawPage rawPage, String message) {
		rawPage.printLogInfo(message);
		handerErrorPage(rawPage);
	}

	@Override
	public void returnErrorPage(RawPage rawPage, Throwable throwable) {
		rawPage.printLogInfo(throwable);
		handerErrorPage(rawPage);
	}

	public void handerErrorPage(RawPage rawPage) {
		if (StringUtils.isNotBlank(tableName)) {
			errPageDao.insertErrorPage(tableName, rawPage.getUrl(),
					rawPage.getRawText(), errorInfo);
		}
	}

	public String getTableName() {
		return tableName;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public DataBasePageErrorOutPut setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public DataBasePageErrorOutPut setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
		return this;
	}
}
