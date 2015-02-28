package core;

public class Site {

	private int firstTime = 0;

	private long period = 1 * 10 * 1000;

	private int resultNum = 100;

	public static Site me() {
		return new Site();
	}

	public Site setResultNum(int num) {
		this.resultNum = num;
		return this;
	}

	public Site setFirstTime(int firstTime) {
		this.firstTime = firstTime;
		return this;
	}

	public Site setPeriod(long period) {
		this.period = period;
		return this;
	}

	public long getPeriod() {
		return this.period;
	}

	public int getFirstTime() {
		return this.firstTime;
	}

	public int getResultNum() {
		return this.resultNum;
	}

}
