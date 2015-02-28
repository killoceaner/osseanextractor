package net.trustie.pipeline;

import javax.annotation.Resource;

import net.trustie.dao.DeWenQ_Dao;
import net.trustie.model.DeWenQ_Model;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;

import core.PageModelPipeline;

@Component("deWenQPipeline")
public class DewWenQ_pipeline implements PageModelPipeline<DeWenQ_Model>{
	@Resource
	DeWenQ_Dao deWenQ_Dao;

	@Override
	public void process(DeWenQ_Model deQ_Model, Task task) {
		deWenQ_Dao.add(deQ_Model);
	}
	
}
