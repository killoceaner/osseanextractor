package net.trustie.pipeline;

import javax.annotation.Resource;
import net.trustie.dao.CsdnBlogs_Dao;
import net.trustie.model.CsdnBlogs_Model;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import core.PageModelPipeline;

@Component("csdnBlogPipeline")
public class CsdnBlogs_pipeline implements PageModelPipeline<CsdnBlogs_Model> {

	@Resource
	private CsdnBlogs_Dao cBlogs_Dao;

	public void process(CsdnBlogs_Model blog_Model, Task arg1) {		
		cBlogs_Dao.add(blog_Model);		
	}
}