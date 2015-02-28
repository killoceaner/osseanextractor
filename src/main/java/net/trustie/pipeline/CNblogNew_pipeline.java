package net.trustie.pipeline;

import javax.annotation.Resource;

import net.trustie.dao.CNblogNews_Dao;
import net.trustie.model.CNblogsNews_Model;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;

import core.PageModelPipeline;

@Component("cnBlogNewsPipeline")
public class CNblogNew_pipeline implements PageModelPipeline<CNblogsNews_Model>{
	@Resource
	private CNblogNews_Dao cNews_Dao;

	@Override
	public void process(CNblogsNews_Model cbNews_Model, Task task) {
		// TODO Auto-generated method stub
		cNews_Dao.add(cbNews_Model);
	}	
}
