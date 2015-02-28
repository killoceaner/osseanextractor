package net.trustie.core;

import net.trustie.utils.StringHandler;
import core.Page;

public class GeneratePage {
	public static Page createPage(String url, String rawText) {
		Page page = new Page();
		if (StringHandler.isAllNotBlank(url, rawText)) {
			page.setRawText(rawText);
			page.setSkip(false);
			page.setPageUrl(url);
		}
		return page;
	}
}
