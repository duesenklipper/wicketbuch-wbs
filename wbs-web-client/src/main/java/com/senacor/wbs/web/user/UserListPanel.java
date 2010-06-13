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
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.IGridSortState.Direction;
import com.inmethod.grid.column.AbstractColumn;
import com.inmethod.grid.column.CheckBoxColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.column.editable.EditablePropertyColumn;
import com.inmethod.grid.column.editable.SubmitCancelColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import com.senacor.domain.user.Country;
import com.senacor.domain.user.User;
import com.senacor.domain.user.UserManager;
import com.senacor.wbs.web.border.ToggleBorder;
import com.senacor.wbs.web.core.AjaxLabelLink;
import com.senacor.wbs.web.core.EditableDropDownPropertyColumn;
import com.senacor.wbs.web.core.EnumChoiceRenderer;

public class UserListPanel extends Panel {
	@SpringBean()
	private UserManager userManager;
	private Label selectionLabel;
	DataGrid grid;

	public UserListPanel(String id, IDataSource dataSource) {
		super(id);
		ToggleBorder border = new ToggleBorder("border", new ResourceModel("searchresultpanel.heading"));
		add(border);
		List<IGridColumn> columns = new ArrayList<IGridColumn>();
		columns.add(new CheckBoxColumn("checkBox"));
		// columns.add(new PropertyColumn(new
		// ResourceModel("id"), "id"));
		EditablePropertyColumn firstNameColumn = new EditablePropertyColumn(new ResourceModel("vorname"), "vorname", "vorname");
		firstNameColumn.setInitialSize(100);
		columns.add(firstNameColumn);
		EditablePropertyColumn nameColumn = new EditablePropertyColumn(new ResourceModel("name"), "name", "name");
		nameColumn.setInitialSize(130);
		columns.add(nameColumn);
		EditablePropertyColumn emailColumn = new EditablePropertyColumn(new ResourceModel("email"), "mainContact.email", "mainContact.email") {
			@Override
			protected void addValidators(FormComponent component) {
				component.add(EmailAddressValidator.getInstance());
			}
		};
		emailColumn.setInitialSize(190);
		columns.add(emailColumn);
		EditableDropDownPropertyColumn countryColumn = new EditableDropDownPropertyColumn(new ResourceModel("country"), "address.country", "address.country",
				Arrays.asList(Country.values()), new EnumChoiceRenderer());
		countryColumn.setInitialSize(90);
		columns.add(countryColumn);
		columns.add(new PropertyColumn(new ResourceModel("username"), "username", "username").setInitialSize(100));
		AbstractColumn passwordColumn = new AbstractColumn("password", new ResourceModel("password")) {
			@Override
			public Component newCell(final WebMarkupContainer parent, final String componentId, final IModel rowModel) {
				parent.setOutputMarkupId(true);
				return new AjaxLabelLink(componentId, new ResourceModel("password")) {
					@Override
					protected void onClick(AjaxRequestTarget target) {
						setResponsePage(new EditPasswordPage((User) rowModel.getObject(), getPage()));
					}
				};
			}
		};
		passwordColumn.setInitialSize(65);
		columns.add(passwordColumn);
		SubmitCancelColumn submitCancelColumn = new SubmitCancelColumn("esd", new ResourceModel("edit")) {
			@Override
			protected void onSubmitted(AjaxRequestTarget target, IModel rowModel, WebMarkupContainer rowComponent) {
				userManager.saveOrUpdate((User) rowModel.getObject());
				super.onSubmitted(target, rowModel, rowComponent);
			}
		};
		submitCancelColumn.setInitialSize(60);
		columns.add(submitCancelColumn);
		grid = new DefaultDataGrid("grid", dataSource, columns);
		border.add(grid);
		grid.setOutputMarkupId(true);
		grid.setCleanSelectionOnPageChange(true);
		grid.setAllowSelectMultiple(false);
		grid.setClickRowToSelect(true);
		grid.setRowsPerPage(4);
		// initial sort state
		grid.getSortState().setSortState("name", Direction.ASC);
	}

	public void setCurrentPage(int i) {
		grid.setCurrentPage(0);
	}
}
