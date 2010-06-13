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

package com.senacor.wbs.web.chat;

import java.util.Vector;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

public abstract class ChatPanel extends Panel {
	public ChatPanel(String id) {
		super(id);
		setOutputMarkupId(true);
		add(new Form("chatForm") {
			{
				final Vector<String> nachrichten = new Vector<String>();
				final TextArea textArea = new TextArea("nachrichten", new Model(nachrichten)) {
					@Override
					protected void onBeforeRender() {
						nachrichten.addAll(receiveMessages());
					}
				};
				textArea.add(new AjaxSelfUpdatingTimerBehavior(Duration.milliseconds(15000)));
				textArea.setEnabled(false);
				textArea.setOutputMarkupId(true);
				add(textArea);
				final TextField textField = new TextField("neueNachricht", new Model("deine Nachricht"));
				textField.setOutputMarkupId(true);
				add(textField);
				add(new AjaxButton("senden") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form form) {
						nachrichten.add(textField.getInput());
						target.addComponent(textArea);
						textField.setModel(new Model(""));
						textField.clearInput();
						target.addComponent(textField);
					}
				});
			}
		});
	}

	protected abstract Vector<String> receiveMessages();

	protected abstract void sendMessage(String message);
}