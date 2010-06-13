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

package com.senacor.wbs.web.core.security;

import java.io.Serializable;

import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.hive.authentication.DefaultSubject;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authentication.UsernamePasswordContext;
import org.apache.wicket.security.hive.authorization.SimplePrincipal;

import com.senacor.domain.security.AuthenticationManager;
import com.senacor.domain.user.Principal;
import com.senacor.domain.user.Role;
import com.senacor.domain.user.User;

public class AuthenticationContext extends UsernamePasswordContext implements Serializable {
	private transient AuthenticationManager authenticationManager;
	private User user;

	public AuthenticationContext(String username, String password, AuthenticationManager authenticationManager) {
		super(username, password);
		this.authenticationManager = authenticationManager;
	}

	/**
	 * @see org.apache.wicket.security.hive.authentication.UsernamePasswordContext#getSubject(String,
	 *      String)
	 */
	protected Subject getSubject(String username, String password) throws LoginException {
		// authenticate username, password, throw exception if
		// not found
		DefaultSubject userSubject = new DefaultSubject();
		// Einloggen gegen die Middleware
		user = authenticationManager.login(username, password);
		// Holen der Principals
		for (Role role : user.getRoles()) {
			for (Principal principal : role.getPrincipals()) {
				userSubject.addPrincipal(new SimplePrincipal(principal.getName().name()));
			}
		}
		return userSubject;
	}

	public User getUser() {
		return user;
	}
}
