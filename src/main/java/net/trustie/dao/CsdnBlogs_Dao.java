package net.trustie.dao;

import net.trustie.model.CsdnBlogs_Model;

import org.apache.ibatis.annotations.Insert;

public interface CsdnBlogs_Dao {
	@Insert("insert into csdn_blogs"
			+ "(`blogId`,`blogUrl`,`blogTitle`,`blogTag`,`blogCategory`,`blogContent`,`readNum`,`commentNum`,`supportNum`,`opposeNum`,`postTime`,`extractTime`,`blogPageMD5`,`author`,`authorUrl`,`history`)"
			+ "values (#{blogId},#{blogUrl},#{blogTitle},#{blogTag},#{blogCategory},#{blogContent},#{readNum},#{commentNum},#{supportNum},#{opposeNum},#{postTime},#{extractTime},#{blogPageMD5},#{author},#{authorUrl},#{history})")
	public int add(CsdnBlogs_Model csdnBlogsModel); 
}
