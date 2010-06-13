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

package com.senacor.wbs.web.border;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.PackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

public class ToggleBorder extends Border {
	private static final long serialVersionUID = 1L;
	private boolean isExpanded = true;

	public ToggleBorder(String id, IModel titleModel) {
		super(id, titleModel);
		add(CSSPackageResource.getHeaderContribution(this.getClass(), "ToggleBorder.css"));
		setOutputMarkupId(true);
		PackageResource rollUp = PackageResource.get(ToggleBorder.this.getClass(), "up.png");
		Image rollUpImage = new Image("rollUpImage", rollUp) {
			@Override
			public boolean isVisible() {
				return isExpanded;
			}
		};
		PackageResource rollDown = PackageResource.get(ToggleBorder.this.getClass(), "down.png");
		Image rollDownImage = new Image("rollDownImage", rollDown) {
			@Override
			public boolean isVisible() {
				return !isExpanded;
			}
		};
		AbstractLink headerLink = new AjaxLink("header") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				isExpanded = !isExpanded;
				target.addComponent(ToggleBorder.this);
			}
		};
		headerLink.add(new Label("title", titleModel));
		headerLink.add(rollUpImage);
		headerLink.add(rollDownImage);
		add(headerLink);
		final WebMarkupContainer content = new WebMarkupContainer("content");
		content.add(getBodyContainer());
		content.add(new AttributeAppender("style", true, new AbstractReadOnlyModel() {
			@Override
			public String getObject() {
				return (isExpanded ? "display:block" : "display:none");
			}
		}, ";"));
		add(content);
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}
}
