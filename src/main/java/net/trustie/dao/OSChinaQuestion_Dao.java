package net.trustie.dao;

import net.trustie.model.OSChinaQuestion_Model;

import org.apache.ibatis.annotations.Insert;

public interface OSChinaQuestion_Dao {
	@Insert("insert into oschina_question"
			+ "(`questionId`,`questionUrl`,`questionTitle`,`tag`,`questionType`,`questionContent`,`housedNum`,`laudNum`,`replyNum`,`viewNum`,`postTime`,`extractTime`,`author`,`authorUrl`,`pageMD5`,`history`)"
			+ " values (#{questionId},#{questionUrl},#{questionTitle},#{tag},#{questionType},#{questionContent},#{housedNum},#{laudNum},#{replyNum},#{viewNum},#{postTime},#{extractTime},#{author},#{authorUrl},#{pageMD5},#{history})")
	public int add(OSChinaQuestion_Model chinaQuestion_Model);
}
