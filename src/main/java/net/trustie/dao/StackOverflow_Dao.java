package net.trustie.dao;

import net.trustie.model.StackOverflow_Model;
import org.apache.ibatis.annotations.Insert;

public interface StackOverflow_Dao {	
	@Insert("insert into stackoverflow_q"
			+ "(`questionId`,`url`,`questionTitle`,`tag`,`questionContent`,`voteNum`,`likeNum`,`answerNum`,`viewNum`,`activeTime`,`postTime`,`extractTime`,`author`,`authorUrl`,`pageMD5`,`history`)"
			+ " values (#{questionId},#{url},#{questionTitle},#{tag},#{questionContent},#{voteNum},#{likeNum},#{answerNum},#{viewNum},#{activeTime},#{postTime},#{extractTime},#{author},#{authorUrl},#{pageMD5},#{history})")
	public int add(StackOverflow_Model overflow_Model);
}
