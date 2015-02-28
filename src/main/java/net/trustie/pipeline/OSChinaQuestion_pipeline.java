package net.trustie.pipeline;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;
import net.trustie.dao.OSChinaQuestion_Dao;
import net.trustie.model.OSChinaQuestion_Model;
import core.PageModelPipeline;

@Component("oschinaQuestionPipeline")
public class OSChinaQuestion_pipeline implements
		PageModelPipeline<OSChinaQuestion_Model> {

	@Resource
	OSChinaQuestion_Dao chinaQuestion_Dao;

	@Override
	public void process(OSChinaQuestion_Model chinaQuestion_Model, Task task) {
		chinaQuestion_Dao.add(chinaQuestion_Model);
	}
}
