package extension;

import java.util.List;

public interface PageDownloader {

	public List<RawPage> downloadPages(int begin_id, int end_id);

	public int getMinId();
	
}
