/**
 * Copyright 2009 Roland Foerther, Carl-Eric-Menzel, Olaf Siefart
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.senacor.wbs.web.gmap;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

import com.senacor.wbs.web.project.EditableTreeTablePanel;

public class GMapPanel extends Panel implements IHeaderContributor {
	private static final String WBS_API_KEY = "ABQIAAAAaV4drxsyP-t-EzEM5l4M2RS30Ne5aftXEmg6IYYVKEPkVymK_hTQKDBcJ0RlqDiePFMtCFwOTtXJGA";
	private static final String GOOGLE_MAPS_API = "http://maps.google.com/maps?file=api&amp;v=2&amp;key=";
	// We have some custom Javascript.
	private static final ResourceReference ADD = new ResourceReference(EditableTreeTablePanel.class, "res/add.png");
	private static final ResourceReference WICKET_GMAP_JS = new JavascriptResourceReference(GMapPanel.class, "res/GMapJS.js");

	public GMapPanel(String id) {
		super(id);
	}

	/**
	 * Einbindung der Google-Bibliothek
	 */
	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(GOOGLE_MAPS_API + WBS_API_KEY);
		response.renderJavascriptReference(WICKET_GMAP_JS);
		response.renderOnEventJavascript("window", "load", "showAddress ('Nürnberg')");
		response.renderOnEventJavascript("window", "unload", "GUnload()");
	}
}
