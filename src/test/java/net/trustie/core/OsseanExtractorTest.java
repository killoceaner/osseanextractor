package net.trustie.core;

import core.ModelPageProcessor;
import core.Page;
import core.Pipeline;

public class OsseanExtractorTest {
	private Pipeline pipeline;

	private ModelPageProcessor modelPageProcessor;

	protected OsseanExtractorTest(ModelPageProcessor modelPageProcessor) {
		this.modelPageProcessor = modelPageProcessor;
	}

	public OsseanExtractorTest(Pipeline pipeline, Class<?>... pageModels) {
		this(ModelPageProcessor.create(pageModels));
		this.pipeline = pipeline;
	}

	public static OsseanExtractorTest create(Pipeline pipeline,
			Class<?>... pageModels) {
		return new OsseanExtractorTest(pipeline, pageModels);
	}

	public void runExtractor(Page page) {
		modelPageProcessor.process(page);
		pipeline.process(page.getResultItems(), null);
	}
}
