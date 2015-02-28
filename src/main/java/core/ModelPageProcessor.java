package core;

import java.util.ArrayList;
import java.util.List;

public class ModelPageProcessor implements PageProcessor {
	private List<PageModelExtractor> pageModelExtractorList = new ArrayList<PageModelExtractor>();

	public static ModelPageProcessor create(Class<?>... clazzs) {
		ModelPageProcessor modelPageProcessor = new ModelPageProcessor();
		for (Class<?> clazz : clazzs) {
			modelPageProcessor.addPageModel(clazz);
		}
		return modelPageProcessor;
	}

	public ModelPageProcessor addPageModel(Class<?> clazz) {
		PageModelExtractor pageModelExtractor = PageModelExtractor
				.create(clazz);
		pageModelExtractorList.add(pageModelExtractor);
		return this;
	}

	public void process(Page page) {
		// TODO Auto-generated method stub
		for (PageModelExtractor pageModelExtractor : pageModelExtractorList) {
			Object process = pageModelExtractor.process(page);
			if (process == null
					|| (process instanceof List && ((List<?>) process).size() == 0)) {
				continue;
			}
			page.putField(pageModelExtractor.getClazz().getCanonicalName(),
					process);
		}
		if (page.getResultItems().getAll().size() == 0) {
			page.getResultItems().setSkip(true);
		}
	}
}
