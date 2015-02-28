package net.trustie.pipeline;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;
import net.trustie.model.FreeCode_Model;
import core.PageModelPipeline;
import net.trustie.dao.FreeCode_Dao;

@Component("freeCodePipeline")
public class FreeCode_pipeline implements PageModelPipeline<FreeCode_Model> {
	@Resource
	private FreeCode_Dao freeCodeDao;
	@Override
	public void process(FreeCode_Model fCode_Model, Task arg1) {
		// TODO Auto-generated method stub
		freeCodeDao.add(fCode_Model);
	}
}
