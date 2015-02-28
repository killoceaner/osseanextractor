package net.trustie.dao;

import net.trustie.model.SFProject_Model;
import org.apache.ibatis.annotations.Insert;

public interface SFProject_Dao {
	@Insert("insert into SFProject"
			+ "(`name`,`maintainers`,`stars`,`downloadCount`,`lastUpdate`,`platform`,`desc`,`categories`,`license`,`feature`,`language`,`intendedAudience`,`userInterface`,`programmingLanguage`,`registeredTime`,`collectTime`,`url`,`urlMd5`,`pageMd5`,`history`)"
			+ "values(#{name},#{maintainers},#{stars},#{downloadCount},#{lastUpdate},#{platform},#{desc},#{categories},#{license},#{feature},#{language},#{intendedAudience},#{userInterface},#{programmingLanguage},#{registeredTime},#{collectTime},#{url},#{urlMd5},#{pageMd5},#{history})")
	public int add(SFProject_Model sfp);
}
