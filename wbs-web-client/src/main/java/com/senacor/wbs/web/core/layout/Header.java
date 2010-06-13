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

package com.senacor.wbs.web.core.layout;

import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

public class Header extends Panel {
	public Header(final String id, final IModel titleModel, final IModel subTitleModel) {
		super(id);
		setRenderBodyOnly(true);
		add(new Label("title", titleModel));
		add(new Label("subtitle", subTitleModel));
		Link deLink = new Link("langde") {
			@Override
			public void onClick() {
				getSession().setLocale(Locale.GERMANY);
			}
		};
		deLink.add(new AttributeModifier("title", true, new Model(Locale.GERMANY.getDisplayName(Locale.GERMANY))));
		add(deLink);
		Link usLink = new Link("langus") {
			@Override
			public void onClick() {
				getSession().setLocale(Locale.US);
			}
		};
		usLink.add(new AttributeModifier("title", true, new Model(Locale.US.getDisplayName(Locale.US))));
		add(usLink);
		Link frLink = new Link("langfr") {
			@Override
			public void onClick() {
				getSession().setLocale(Locale.FRANCE);
			}
		};
		frLink.add(new AttributeModifier("title", true, new Model(Locale.FRANCE.getDisplayName(Locale.FRANCE))));
		frLink.setEnabled(false);
		Image inactiveFrImage = new Image("imgFrance");
		frLink.add(inactiveFrImage);
		add(frLink);
		try {
			add(new AuthenticationStatePanel("loginPanel"));
		} catch (IllegalStateException e) {
			add(new Label("loginPanel"));
		}
		add(new ExternalLink("contactLink", new Model("http://www.senacor.de"), new ResourceModel("contact.link.label")));
		add(new ExternalLink("imprintLink", new Model("http://www.senacor.de"), new ResourceModel("imprint.link.label")));
	}
}
