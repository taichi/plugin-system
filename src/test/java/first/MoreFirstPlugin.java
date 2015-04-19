package first;

import multibindings.Plugin;

public class MoreFirstPlugin implements Plugin {

	boolean initialized = false;

	@Override
	public String name() {
		return "more-first-plugin";
	}

	@Override
	public void initialize() {
		this.initialized = true;
	}

}