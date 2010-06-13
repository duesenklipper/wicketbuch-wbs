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

package com.senacor.wbs.web.user;

import org.apache.wicket.Page;
import org.apache.wicket.model.CompoundPropertyModel;

import com.senacor.domain.user.User;

public class EditPasswordPage extends ComponentDemoPage {
	public EditPasswordPage(User user, final Page returnPage) {
		this.setDefaultModel(new CompoundPropertyModel<User>(user));
		this.add(new UserPasswordPanel("userpassword", true) {
			@Override
			protected void onSubmit() {
				// update password here
				setResponsePage(returnPage);
			}
		});
	}
}