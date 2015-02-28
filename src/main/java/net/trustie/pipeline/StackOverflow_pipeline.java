package net.trustie.pipeline;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;
import net.trustie.dao.StackOverflow_Dao;
import net.trustie.model.StackOverflow_Model;
import core.PageModelPipeline;

@Component("stackOverFlowPipeline")
public class StackOverflow_pipeline implements
		PageModelPipeline<StackOverflow_Model> {
	@Resource
	private StackOverflow_Dao overflow_Dao;

	@Override
	public void process(StackOverflow_Model overflow_Model, Task task) {
		overflow_Dao.add(overflow_Model);

	}

}
