package net.trustie.dao;

import org.apache.ibatis.annotations.Insert;

import net.trustie.model.FreeCode_Model;


public interface FreeCode_Dao {
	@Insert("insert into freecode_projects"
			+"(`project_url`,`extract_time`,`tag`,`license`,`operate_system`,`implementation`,`project_title`,`project_des`,`author`,`author_url`,`post_date`,`project_downloads`,`project_website`,`crawlerTime`)"
			+" values (#{projectUrl},#{extractTime},#{tag},#{license},#{operateSystem},#{implementation},#{projectTitle},#{projectDesc},#{author},#{authorUrl},#{postDate},#{projectDownloads},#{projectWebsite},#{crawlerTime})") 
	public int add(FreeCode_Model fmodel);
}
