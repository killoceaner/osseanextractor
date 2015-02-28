package extension;

public interface PageErrorOutPut {
	
	public void returnErrorPage(RawPage rawPage,String message);
	
	public void returnErrorPage(RawPage rawPage,Throwable throwable);

}
