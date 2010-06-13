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

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.authentication.LoginException;

import com.senacor.domain.user.User;

public class WBSSession extends WaspSession {
	private AuthenticationContext authenticationContext;
	private long loginTime = 0;

	public WBSSession(final WaspApplication application, final Request request) {
		super(application, request);
		try {
			super.login(new GuestAuthenticationContext());
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void login(final Object context) throws LoginException {
		login((AuthenticationContext) context);
	}

	public void login(final AuthenticationContext context) throws LoginException {
		super.login(context);
		authenticationContext = context;
		loginTime = System.currentTimeMillis();
	}

	public boolean isAuthenticated() {
		return authenticationContext != null;
	}

	public User getUser() {
		return authenticationContext.getUser();
	}

	@Override
	public boolean logoff(final Object context) {
		loginTime = 0;
		return super.logoff(context);
	}

	public static WBSSession get() {
		final Session session = Session.get();
		if (session == null) {
			return null;
		} else if (session instanceof WBSSession) {
			return (WBSSession) session;
		} else {
			throw new IllegalStateException("No WBSSession found!");
		}
	}

	public long getLoginTime() {
		return loginTime;
	}
}