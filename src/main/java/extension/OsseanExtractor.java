package extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.ConsolePipeline;
import core.ModelPageProcessor;
import core.ModelPipeline;
import core.PageModelPipeline;
import core.Pipeline;
import core.Site;

public class OsseanExtractor extends TimerTask {
	private Site site;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String uuid;

	private PageDownloader downloader;

	private Timer timer;

	private ReadCursor readCursor;

	private ModelPipeline modelPipeline;

	protected List<Pipeline> pipelines = new ArrayList<Pipeline>();

	private ModelPageProcessor modelPageProcessor;

	private String cursorPath;

	public String defaultPath = "./cursor/";

	public List<String> modelName = new ArrayList<String>();

	private PageErrorOutPut pageErrorOutPut;

	private int recordId = 0;

	protected OsseanExtractor(ModelPageProcessor modelPageProcessor) {
		this.modelPageProcessor = modelPageProcessor;
	}

	public OsseanExtractor(Site site, PageModelPipeline<?> pageModelPipeline,
			Class<?>... pageModels) {
		this(ModelPageProcessor.create(pageModels));
		this.site = site;
		this.modelPipeline = new ModelPipeline();
		this.addPipeline(modelPipeline);
		for (Class<?> pageModel : pageModels) {  
			modelName.add(pageModel.getCanonicalName());
			if (pageModelPipeline != null) {
				this.modelPipeline.put(pageModel, pageModelPipeline);
			}
		}
	}

	public static OsseanExtractor create(Site site,
			PageModelPipeline<?> modelPipeline, Class<?>... pageModels) {
		return new OsseanExtractor(site, modelPipeline, pageModels);
	}

	public OsseanExtractor setDownloader(PageDownloader downloader) {
		this.downloader = downloader;
		return this;
	}

	public OsseanExtractor setUUID(String uuid) {
		this.uuid = uuid;
		return this;
	}

	public OsseanExtractor setPageErrorOutPut(PageErrorOutPut errorOutPut) {
		this.pageErrorOutPut = errorOutPut;
		return this;
	}

	public OsseanExtractor setCursorPath(String cursorPath) {
		this.cursorPath = cursorPath;
		return this;
	}

	public OsseanExtractor addPipeline(Pipeline pipeline) {
		this.pipelines.add(pipeline);
		return this;
	}

	public OsseanExtractor setPipelines(List<Pipeline> pipelines) {
		this.pipelines = pipelines;
		return this;
	}

	public OsseanExtractor clearPipeline() {
		pipelines = new ArrayList<Pipeline>();
		return this;
	}

	@Override
	public void run() {
		logger.info("One Extraction Task Started!");

		if (readCursor.getValue(uuid) != null)
			recordId = Integer.parseInt(readCursor.getValue(uuid).trim());
		else
			recordId = downloader.getMinId();

		List<RawPage> rawList = this.downloader.downloadPages(recordId,
				recordId + this.site.getResultNum());

		createPageList(rawList);
		for (RawPage rawPage : rawList) {
			if (rawPage.getPage() != null
					&& !rawPage.getPage().getResultItems().isSkip()) {
				try {
					modelPageProcessor.process(rawPage.getPage());
					rawPage.setExtracted(true);

					if (!rawPage.getPage().getResultItems().isSkip()) {

						for (Pipeline pipeline : pipelines)
							pipeline.process(
									rawPage.getPage().getResultItems(), null);

						if (!rawPage.getPage()
								.isAllResultSkip(
										modelName.toArray(new String[modelName
												.size()]))) {
							logger.info(rawPage.toString()
									+ " Extracted And Stored Successed!");
							rawPage.setStored(true);
						}
					}
					if (!rawPage.isExtracted() || !rawPage.isStored())
						pageErrorOutPut.returnErrorPage(rawPage,
								"May Caused By Model Problem! Or Page Error!");

				} catch (Exception e) {
					pageErrorOutPut.returnErrorPage(rawPage, e);
				}
			}
		}
		if (rawList.size() > 0)
			readCursor
					.setValue(uuid, String.valueOf(rawList.get(
							rawList.size() - 1).getId() + 1));
	}

	public void start() {
		init();
		logger.info(this.uuid + " Extractor Started!");
		timer.schedule(this, site.getFirstTime(), site.getPeriod());
	}

	protected void init() {
		if (downloader == null) {
			logger.error("No Page Source!");
			System.exit(0);
		}
		if (timer == null) {
			timer = new Timer();
		}
		if (cursorPath == null) {
			this.cursorPath = defaultPath+uuid+".txt";
		}
		if (readCursor == null) {
			readCursor = new ReadCursor(cursorPath);
		}

		if (pipelines.isEmpty()) {
			pipelines.add((Pipeline) new ConsolePipeline());
		}

		if (pageErrorOutPut == null) {
			pageErrorOutPut = new DefaultPageErrorOutPut();
		}
	}

	protected void createPageList(List<RawPage> rawPages) {
		for (RawPage rawPage : rawPages) {
			rawPage.generatPage();
		}
	}
}
