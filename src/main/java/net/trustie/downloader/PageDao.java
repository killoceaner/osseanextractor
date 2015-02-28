package net.trustie.downloader;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import extension.RawPage;

public interface PageDao {

	@Select("select id,url,html,crawledTime from ${table} where id>= ${begin_id} and id< ${end_id}")
	public List<RawPage> selectScourcePage(@Param("table") String table_name,
			@Param("begin_id") int begin_id, @Param("end_id") int end_id);

	@Select("select Min(id) from ${table_name}")
	public int getMinId(@Param("table_name") String table_name);	
}
