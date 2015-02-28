package net.trustie.pipeline;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;
import net.trustie.dao.IteyeAsk_Dao;
import net.trustie.model.IteyeAsk_Model;
import core.PageModelPipeline;

@Component("itEyeAskPipeline")
public class IteyeAsk_pipeline implements PageModelPipeline<IteyeAsk_Model> {
	@Resource
	private IteyeAsk_Dao iteyeAsk_Dao;

	@Override
	public void process(IteyeAsk_Model ask_Model, Task task) {
		iteyeAsk_Dao.add(ask_Model);
	}

}
