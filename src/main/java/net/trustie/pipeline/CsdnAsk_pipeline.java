package net.trustie.pipeline;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;
import net.trustie.dao.CsdnAsk_Dao;
import net.trustie.model.CsdnAsk_Model;
import core.PageModelPipeline;

@Component("csdnAskPipeline")
public class CsdnAsk_pipeline implements PageModelPipeline<CsdnAsk_Model> {
	@Resource
	private CsdnAsk_Dao ask_Dao;

	@Override
	public void process(CsdnAsk_Model csdnQ_Model, Task task) {
		ask_Dao.add(csdnQ_Model);
	}

}
