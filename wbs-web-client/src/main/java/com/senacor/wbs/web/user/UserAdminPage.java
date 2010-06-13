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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.senacor.domain.user.User;
import com.senacor.domain.user.UserManager;

public class UserAdminPage extends BaseAdminPage {
	UserListPanel userListPanel;
	@SpringBean()
	private UserManager userManager;
	private final List<User> foundUser = new ArrayList<User>();

	private boolean startsWith(final String a, final String b) {
		return StringUtils.isBlank(b) || a.startsWith(b) || a.toLowerCase().startsWith(b.toLowerCase());
	}

	public UserAdminPage() {
		SearchUserPanel searchUserPanel = new SearchUserPanel(middleColumn.getListPanel().newChildId()) {
			@Override
			protected void doSearch(final AjaxRequestTarget target, final User userCriteria) {
				// foundUser = userManager.find(userCriteria);
				// Workaround bis userManager.find(userCriteria)
				// funktioniert
				userListPanel.setCurrentPage(0);
				foundUser.clear();
				for (User user : userManager.findAll()) {
					if (startsWith(user.getVorname(), userCriteria.getVorname()) && startsWith(user.getName(), userCriteria.getName())
							&& startsWith(user.getUsername(), userCriteria.getUsername())) {
						foundUser.add(user);
					}
				}
				target.addComponent(userListPanel);
			}

			@Override
			protected Iterator<User> doGetChoices(final String input) {
				if (StringUtils.isBlank(input)) {
					return new ArrayList<User>().iterator();
				} else {
					String stripped = input.replaceAll("\\(|\\)", "");
					String[] inputParts = StringUtils.split(stripped);
					String criteria = StringUtils.join(inputParts, "* ");
					criteria = criteria + "*";
					System.out.println("criteria: " + criteria);
					List<User> userList = userManager.findPerIndex(criteria);
					return userList.iterator();
				}
			}
		};
		middleColumn.getListPanel().add(searchUserPanel);
		IModel userDataModel = new AbstractReadOnlyModel() {
			@Override
			public Object getObject() {
				return foundUser;
			}
		};
		userListPanel = new UserListPanel(middleColumn.getListPanel().newChildId(), new UserDataSource(userDataModel));
		userListPanel.setOutputMarkupId(true);
		userListPanel.setRenderBodyOnly(false);
		middleColumn.getListPanel().add(userListPanel);
	}
}
