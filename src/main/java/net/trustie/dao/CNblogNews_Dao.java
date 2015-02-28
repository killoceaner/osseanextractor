package net.trustie.dao;

import net.trustie.model.CNblogsNews_Model;
import org.apache.ibatis.annotations.Insert;

public interface CNblogNews_Dao {

	@Insert("insert into cnblog_news"
			+ "(`newsId`,`newsUrl`,`newsTitle`,`tag`,`newsCategorie`,`newsContent`,`comeFrom`,`originFrom`,`relativeTime`,`extractTime`,`newsAuthor`,`newsAuthorUrl`,`pageMD5`,`history`)"
			+ " values (#{newsId},#{newsUrl},#{newsTitle},#{tag},#{newsCategorie},#{newsContent},#{comeFrom},#{originFrom},#{relativeTime},#{extractTime},#{newsAuthor},#{newsAuthorUrl},#{pageMD5},#{history})")
	public int add(CNblogsNews_Model cNews_Model);
}
