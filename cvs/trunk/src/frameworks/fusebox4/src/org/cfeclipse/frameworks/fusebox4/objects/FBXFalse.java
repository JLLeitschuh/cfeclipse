package org.cfeclipse.frameworks.fusebox4.objects;

import org.cfeclipse.frameworks.fusebox4.util.PluginImages;

public class FBXFalse extends FBXObject {

	private String icon = PluginImages.ICON_FBX_FALSE;
	
	public String getIcon() {
		return this.icon;
	}

	public String getName() {
		return "False";
	}

	public String toString() {
		return getName();
	}
}
