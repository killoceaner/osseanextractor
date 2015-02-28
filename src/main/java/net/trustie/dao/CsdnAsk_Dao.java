package net.trustie.dao;

import net.trustie.model.CsdnAsk_Model;

import org.apache.ibatis.annotations.Insert;

public interface CsdnAsk_Dao {
	@Insert("insert into csdn_ask"
			+ "(`issueId`,`issueUrl`,`issueTitle`,`tag`,`issueContent`,`voteNum`,`answerNum`,`postTime`,`extractTime`,`author`,`authorUrl`,`pageMD5`,`history`)"
			+ " values (#{issueId},#{issueUrl},#{issueTitle},#{tag},#{issueContent},#{voteNum},#{answerNum},#{postTime},#{extractTime},#{author},#{authorUrl},#{pageMD5},#{history})")
	public int add(CsdnAsk_Model ask_Model);
}
