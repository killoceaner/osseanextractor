package net.trustie.pipeline;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;
import net.trustie.dao.OpenHubProject_Dao;
import net.trustie.model.OpenHubProject_Model;
import core.PageModelPipeline;

@Component("OpenHubProjectPipeline")
public class OpenHubProject_pipeline implements PageModelPipeline<OpenHubProject_Model> {
	@Resource
	private OpenHubProject_Dao OpenHubPro_Dao;
	
	@Override
	public void process(OpenHubProject_Model OpenHubPro_Model, Task task) {
		// TODO Auto-generated method stub
		OpenHubPro_Dao.add(OpenHubPro_Model);
	}

}
