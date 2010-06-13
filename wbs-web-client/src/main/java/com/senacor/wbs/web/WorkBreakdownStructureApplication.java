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

package com.senacor.wbs.web;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Application;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadWebRequest;
import org.apache.wicket.protocol.http.SecondLevelCacheSessionStore;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycleProcessor;
import org.apache.wicket.protocol.http.pagestore.DiskPageStore;
import org.apache.wicket.protocol.http.request.WebRequestCodingStrategy;
import org.apache.wicket.request.IRequestCodingStrategy;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.authorization.permissions.ComponentPermission;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.security.swarm.actions.SwarmActionFactory;
import org.apache.wicket.session.ISessionStore;
import org.apache.wicket.session.pagemap.LeastRecentlyAccessedEvictionStrategy;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.convert.ConverterLocator;
import org.apache.wicket.util.lang.Bytes;

import com.senacor.domain.project.ProjectState;
import com.senacor.wbs.web.core.EnumConverter;
import com.senacor.wbs.web.core.security.WBSSession;
import com.senacor.wbs.web.gmap.GMapsPage;
import com.senacor.wbs.web.jmx.JmxPage;
import com.senacor.wbs.web.project.CurrencyConverter;
import com.senacor.wbs.web.project.ProjectDetailsPage;
import com.senacor.wbs.web.project.ProjectOverviewPage;
import com.senacor.wbs.web.project.ProjectStatisticsPage;
import com.senacor.wbs.web.user.AppSettingsPage;
import com.senacor.wbs.web.user.CreateUserPage;
import com.senacor.wbs.web.user.CreateUserPanelizedPage;
import com.senacor.wbs.web.user.CreateUserSimplePage;
import com.senacor.wbs.web.user.EditPasswordPage;
import com.senacor.wbs.web.user.EditUserPage;
import com.senacor.wbs.web.user.ListRolesPage;
import com.senacor.wbs.web.user.UserAdminPage;

public class WorkBreakdownStructureApplication extends SwarmWebApplication {
	private final boolean spring;
	private boolean statsPublic = true;

	public boolean isStatsPublic() {
		return statsPublic;
	}

	public void setStatsPublic(final boolean publicStats) {
		this.statsPublic = publicStats;
	}

	public WorkBreakdownStructureApplication() {
		this(true);
	}

	public WorkBreakdownStructureApplication(final boolean spring) {
		this.spring = spring;
	}

	@Override
	protected void init() {
		super.init();
		if (spring) {
			// THIS LINE IS IMPORTANT - IT INSTALLS THE COMPONENT
			// INJECTOR THAT WILL
			// INJECT NEWLY CREATED COMPONENTS WITH THEIR SPRING
			// DEPENDENCIES
			addComponentInstantiationListener(new SpringComponentInjector(this));
		}
		// Autolink-Verhalten grundsätzlich einschalten
		// getMarkupSettings().setAutomaticLinking(true);
		getApplicationSettings().setDefaultMaximumUploadSize(Bytes.megabytes(50));
		// Standard-Encoding für Markup-Files
		getMarkupSettings().setDefaultMarkupEncoding("utf-8");
		// Setzt im Response-Header Character encoding
		// d h.: Content-Type text/html;charset=<encoding>
		getRequestCycleSettings().setResponseRequestEncoding("utf-8");
		// getMarkupSettings().setStripWicketTags(true);
		// maximal 3 PageMaps verwalten (default 5)
		// eine PageMap pro Browserfenster
		getSessionSettings().setMaxPageMaps(3);
		getSessionSettings().setPageMapEvictionStrategy(new LeastRecentlyAccessedEvictionStrategy(3));
		getDebugSettings().setOutputMarkupContainerClassName(true);
		getDebugSettings().setLinePreciseReportingOnAddComponentEnabled(true);
		getDebugSettings().setLinePreciseReportingOnNewComponentEnabled(true);
		mountBookmarkablePage("/start", StartPage.class);
		mountBookmarkablePage("/gmap", GMapsPage.class);
		// Damit werden Komponentenresourcen wie Templates,
		// css, images, etc
		// zusätzlich unter <web-content-root>/html gesucht
		getResourceSettings().addResourceFolder("/html");
	}

	/**
	 * @see org.apache.wicket.Application#newSessionStore()
	 */
	/*
	 * protected ISessionStore newSessionStore() { return new
	 * HttpSessionStore(this) { public IPageVersionManager
	 * newVersionManager(Page page) { // maximal 5 Versionen einer Seite
	 * verwalten (default // 20) return new UndoPageVersionManager(page, 2); }
	 * }; }
	 */
	@Override
	protected ISessionStore newSessionStore() {
		return new SecondLevelCacheSessionStore(this, new DiskPageStore((int) Bytes.megabytes(20).bytes(), (int) Bytes.megabytes(500).bytes(), 50));
	}

	@Override
	protected IRequestCycleProcessor newRequestCycleProcessor() {
		return new WebRequestCycleProcessor() {
			@Override
			protected IRequestCodingStrategy newRequestCodingStrategy() {
				return new WebRequestCodingStrategy();
				// return new CryptedUrlWebRequestCodingStrategy(new
				// WebRequestCodingStrategy());
			}
		};
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
		converterLocator.set(ProjectState.class, new EnumConverter(ProjectState.class));
		// BigDecimal's werden in erster Linie für
		// Währungsbeträge verwendet.
		converterLocator.set(BigDecimal.class, new CurrencyConverter());
		return converterLocator;
	}

	@Override
	public Class getHomePage() {
		return StartPage.class;
	}

	@Override
	public Session newSession(final Request request, final Response response) {
		return new WBSSession(this, request);
	}

	/**
	 * @see org.apache.wicket.Application#newSessionStore()
	 */
	/*
	 * protected ISessionStore newSessionStore() { return new
	 * SecondLevelCacheSessionStore(this, new SimpleSynchronousFilePageStore());
	 * }
	 */
	@Override
	protected Object getHiveKey() {
		return getServletContext().getContextPath();
	}

	@Override
	protected void setUpHive() {
		// create factory
		PolicyFileHiveFactory factory = new PolicyFileHiveFactory(new SwarmActionFactory(getHiveKey()));
		// this example uses 1 policy file but you can add
		// as many as you
		// like
		factory.addPolicyFile(getClass().getResource("/permissions.hive"));
		// Pages
		factory.setAlias("ComponentPermission", ComponentPermission.class.getName());
		factory.setAlias("home", StartPage.class.getName());
		factory.setAlias("project", ProjectOverviewPage.class.getName());
		factory.setAlias("stats", ProjectStatisticsPage.class.getName());
		factory.setAlias("tasks", ProjectDetailsPage.class.getName());
		factory.setAlias("user", UserAdminPage.class.getName());
		factory.setAlias("useredit", EditUserPage.class.getName());
		factory.setAlias("createUser", CreateUserPage.class.getName());
		factory.setAlias("createUser1", CreateUserSimplePage.class.getName());
		factory.setAlias("createUser2", CreateUserPanelizedPage.class.getName());
		factory.setAlias("createUser3", EditPasswordPage.class.getName());
		factory.setAlias("listRoles", ListRolesPage.class.getName());
		factory.setAlias("map", GMapsPage.class.getName());
		factory.setAlias("jmx", JmxPage.class.getName());
		factory.setAlias("settings", AppSettingsPage.class.getName());
		// register factory
		HiveMind.registerHive(getHiveKey(), factory);
	}

	public Class getLoginPage() {
		return StartPage.class;
	}

	/**
	 * für UploadProgressBar muss UploadWebRequest verwendet werden
	 */
	@Override
	protected WebRequest newWebRequest(final HttpServletRequest servletRequest) {
		return new UploadWebRequest(servletRequest);
	}

	public static WorkBreakdownStructureApplication get() {
		return (WorkBreakdownStructureApplication) Application.get();
	}
}
