package billingSystem.dataFormat.xml;

import billingSystem.dataFormat.IData;

public class Element implements IData {

	private String data;

	public Element(String data) {
		this.data = data;
	}

	@Override
	public String getData() {
		return data;
	}

}
