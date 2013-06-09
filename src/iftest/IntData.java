package iftest;

public class IntData implements IData {

	private int data;

	public IntData() {
		data = 0;
	}

	@Override
	public int get() {
		return data;
	}

	@Override
	public void set(int n) {
		data = n;
	}

	@Override
	public void printOn() {
		System.out.println(data);
	}
}
