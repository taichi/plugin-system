package second;

import multibindings.Plugin;

public class SecondPlugin implements Plugin {

	boolean initialized = false;

	@Override
	public String name() {
		return "second-plugin";
	}

	@Override
	public void initialize() {
		this.initialized = true;
	}

}