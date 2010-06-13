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

package com.senacor.wbs.web.core;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.util.string.Strings;

public class ResourceInlineFrame extends WebMarkupContainer implements IResourceListener {
	WebResource webResource;

	public ResourceInlineFrame(String id, WebResource webResource) {
		super(id);
		this.webResource = webResource;
	}

	public void onResourceRequested() {
		webResource.onResourceRequested();
	}

	/**
	 * Handles this frame's tag.
	 * 
	 * @param tag
	 *            the component tag
	 * @see org.apache.wicket.Component#onComponentTag(ComponentTag)
	 */
	protected final void onComponentTag(final ComponentTag tag) {
		checkComponentTag(tag, "iframe");
		// Set href to link to this frame's frameRequested
		// method
		CharSequence url = getURL();
		// generate the src attribute
		tag.put("src", Strings.replaceAll(url, "&", "&amp;"));
		super.onComponentTag(tag);
	}

	/**
	 * Gets the url to use for this link.
	 * 
	 * @return The URL that this link links to
	 */
	protected CharSequence getURL() {
		return urlFor(IResourceListener.INTERFACE);
	}
}
