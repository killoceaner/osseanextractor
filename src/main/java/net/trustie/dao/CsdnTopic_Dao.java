package net.trustie.dao;

import net.trustie.model.CsdnTopic_Model;

import org.apache.ibatis.annotations.Insert;

public interface CsdnTopic_Dao {

	@Insert("insert into csdn_topics"
			+ "(`topicId`,`topicTitle`,`topicUrl`,`topicContent`,`tags`,`topicScore`,`replyNum`,`postTime`,`extractTime`,`pageMD5`,`history`,`author`,`author_url`)"
			+ "values (#{topicId},#{topicTitle},#{topicUrl},#{topicContent},#{tag},#{topicScore},#{replyNum},#{postTime},#{extractTime},#{pageMD5},#{history},#{author},#{author_url})")
	public int add(CsdnTopic_Model cModel);
}
