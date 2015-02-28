package net.trustie.dao;

import net.trustie.model.OpenHubProject_Model;

import org.apache.ibatis.annotations.Insert;

public interface OpenHubProject_Dao {
	@Insert("insert into openhub_project_copy"
			+"(`name`,`projectUrl`,`activity`,`useCount`,`description`,`tags`,`organization`,`projectLinks`,`codeLocation`,`licenses`,`similarProjects`,`managers`,`commitNum`,`contributorNum`,`codeLinesNum`,`mostWrittenIn`,`commentsPercentage`,`codebaseStatus`,`teamScale`,`commitStatus`,`estimateEffortTime`,`firstCommitTime`,`lastCommitTime`,`languagePercentages`,`activityDayTime`,`daysCommitNumber`,`daysContributorNumber`,`newContributor`,`newContriNum`,`activityMonthTime`,`monthsCommitNumber`,`monthsContributorNumber`,`theCommitTrend`,`theContriTrend`,`rateLevel`,`crawlerTime`)"
			+" values (#{name},#{projectUrl},#{activity},#{useCount},#{description},#{tags},#{organization},#{projectLinks},#{codeLocation},#{licenses},#{similarProjects},#{managers},#{commitNum},#{contributorNum},#{codeLinesNum},#{mostWrittenIn},#{commentsPercentage},#{codebaseStatus},#{teamScale},#{commitStatus},#{estimateEffortTime},#{firstCommitTime},#{lastCommitTime},#{languagePercentages},#{activityDayTime},#{daysCommitNumber},#{daysContributorNumber},#{newContributor},#{newContriNum},#{activityMonthTime},#{monthsCommitNumber},#{monthsContributorNumber},#{theCommitTrend},#{theContriTrend},#{rateLevel},#{crawlerTime})") 
	public int add(OpenHubProject_Model OpHmodel);
}
