package net.trustie.pipeline;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import net.trustie.dao.OSChinaProject_Dao;
import net.trustie.model.OSChinaProject_Model;
import core.PageModelPipeline;

@Component("oschinaProjectPipeline")
public class OSChinaProject_pipeline implements PageModelPipeline<OSChinaProject_Model> {

	@Resource
	private OSChinaProject_Dao chinaProject_Dao;
	@Override
	public void process(OSChinaProject_Model project_Model, Task task) {
		chinaProject_Dao.add(project_Model);
	}

}
