package net.trustie.dao;

import net.trustie.model.DeWenQ_Model;

import org.apache.ibatis.annotations.Insert;

public interface DeWenQ_Dao {
	
	@Insert("insert into dewen_question"
			+ "(`issueId`,`issueUrl`,`issueTitle`,`tag`,`issueDetail`,`scanerNum`,`attentionNum`,`commentNum`,`answerNum`,`extractTime`,`postTime`,`author`,`authorUrl`,`pageMD5`,`history`)"
			+ " values (#{issueId},#{issueUrl},#{issueTitle},#{tag},#{issueDetail},#{scanerNum},#{attentionNum},#{commentNum},#{answerNum},#{extractTime},#{postTime},#{author},#{authorUrl},#{pageMD5},#{history})")
	public int add(DeWenQ_Model deWenQ_Model);

}
