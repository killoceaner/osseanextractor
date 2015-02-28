package net.trustie.dao;

import net.trustie.model.IteyeAsk_Model;

import org.apache.ibatis.annotations.Insert;

public interface IteyeAsk_Dao {
	@Insert("insert into iteye_ask"
			+ "(`questionId`,`questionUrl`,`questionTitle`,`tag`,`categories`,`questionContent`,`voteUp`,`voteDown`,`questionScore`,`answerNum`,`interestNum`,`postTime`,`extractTime`,`author`,`authorUrl`,`pageMD5`,`history`)"
			+ " values (#{questionId},#{questionUrl},#{questionTitle},#{tag},#{categories},#{questionContent},#{voteUp},#{voteDown},#{questionScore},#{answerNum},#{interestNum},#{postTime},#{extractTime},#{author},#{authorUrl},#{pageMD5},#{history})")
	public int add(IteyeAsk_Model ask_Model);
}
