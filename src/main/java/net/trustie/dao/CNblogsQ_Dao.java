package net.trustie.dao;
import net.trustie.model.CNblogsQ_Model;
import org.apache.ibatis.annotations.Insert;

public interface CNblogsQ_Dao {
	
	@Insert("insert into cnblog_question"
			+ "(`questionId`,`questionUrl`,`questionTitle`,`tag`,`questionContent`,`voteNum`,`viewNum`,`scoreBean`,`answerNum`,`questionType`,`postTime`,`extractTime`,`author`,`authorUrl`,`pageMD5`,`history`)"
			+ " values (#{questionId},#{questionUrl},#{questionTitle},#{tag},#{questionContent},#{voteNum},#{viewNum},#{scoreBean},#{answerNum},#{questionType},#{postTime},#{extractTime},#{author},#{authorUrl},#{pageMD5},#{history})")
	public int add(CNblogsQ_Model cNblogsQ_Model);

}
