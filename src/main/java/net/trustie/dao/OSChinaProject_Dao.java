package net.trustie.dao;

import net.trustie.model.OSChinaProject_Model;

import org.apache.ibatis.annotations.Insert;

public interface OSChinaProject_Dao {	
	@Insert("insert into oschina_project"
			+ "(`projectShortName`,`projectUrl`,`projectTitle`,`projectCategory`,`projectDesc`,`usedNum`,`housedNum`,`projectLicenses`,`projectLanguage`,`projectOS`,`IncludedTime`,`exteactTime`,`pageMD5`,`history`)"
			+ " values (#{projectShortName},#{projectUrl},#{projectTitle},#{projectCategory},#{projectDesc},#{usedNum},#{housedNum},#{projectLicenses},#{projectLanguage},#{projectOS},#{IncludedTime},#{exteactTime},#{pageMD5},#{history})")
	public int add(OSChinaProject_Model project_Model);
}
