package first;

import multibindings.Plugin;

public class FirstPlugin implements Plugin {

	boolean initialized = false;

	@Override
	public void initialize() {
		this.initialized = true;
	}

}