package extension;

public class DefaultPageErrorOutPut implements PageErrorOutPut {

	@Override
	public void returnErrorPage(RawPage rawPage, String message) {
		rawPage.printLogInfo(message);
	}

	@Override
	public void returnErrorPage(RawPage rawPage, Throwable throwable) {
		rawPage.printLogInfo(throwable);		
	}	
}
