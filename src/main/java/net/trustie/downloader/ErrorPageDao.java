package net.trustie.downloader;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface ErrorPageDao {

	@Insert("insert into ${table}(`url`,`html`,`type`) values(#{url},#{html},#{type})")
	public void insertErrorPage(@Param("table") String table,
			@Param("url") String url, @Param("html") String html,
			@Param("type") String type);
}
