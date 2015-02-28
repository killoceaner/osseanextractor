package net.trustie.pipeline;

import javax.annotation.Resource;

import net.trustie.dao.CNblogsQ_Dao;
import net.trustie.model.CNblogsQ_Model;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;

import core.PageModelPipeline;

@Component("cnblogsQPipeline")
public class CNblogsQ_pipeline implements PageModelPipeline<CNblogsQ_Model> {
	@Resource
	private CNblogsQ_Dao cNblogsQ_Dao;

	@Override
	public void process(CNblogsQ_Model cModel, Task task) {
		cNblogsQ_Dao.add(cModel);
	}
}
