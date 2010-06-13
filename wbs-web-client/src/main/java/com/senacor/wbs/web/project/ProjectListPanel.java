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

package com.senacor.wbs.web.project;

import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ImageButton;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;
import org.apache.wicket.util.resource.IResourceStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.senacor.domain.project.Project;
import com.senacor.wbs.web.core.layout.BaseWBSPage;

public class ProjectListPanel extends Panel {
	private final DataView dataView;
	private Locale locale;

	public ProjectListPanel(final String id, final List<Project> projects) {
		super(id);
		this.locale = getLocale();
		SortableListDataProvider<Project> projectProvider = new SortableListDataProvider<Project>(projects) {
			@Override
			protected Locale getLocale() {
				return ProjectListPanel.this.getLocale();
			}

			public IModel model(final Object object) {
				return new CompoundPropertyModel(object);
			}
		};
		projectProvider.setSort("name", true);
		dataView = new DataView("projects", projectProvider, 4) {
			@Override
			protected void populateItem(final Item item) {
				Project project = (Project) item.getModelObject();
				PageParameters pageParameters = new PageParameters();
				pageParameters.put("projectId", project.getId());
				item.add(new BookmarkablePageLink("tasks", ProjectDetailsPage.class, pageParameters).add(new Label("id")));
				item.add(new Label("kuerzel"));
				item.add(new Label("titel", project.getName()));
				item.add(new Label("budget"));
				item.add(new Label("costPerHour"));
				item.add(new Label("start"));
				item.add(new Label("ende"));
				item.add(new Label("state"));
				// Alternieren der Farbe zwischen geraden und
				// ungeraden Zeilen
				item.add(new AttributeModifier("class", true, new AbstractReadOnlyModel() {
					@Override
					public Object getObject() {
						return (item.getIndex() % 2 == 1) ? "even" : "odd";
					}
				}));
			}
		};
		add(dataView);
		Form localeForm = new Form("localeForm");
		ImageButton deButton = new ImageButton("langde", new ResourceReference(BaseWBSPage.class, "de.png")) {
			@Override
			public void onSubmit() {
				ProjectListPanel.this.locale = Locale.GERMANY;
			}
		};
		localeForm.add(deButton);
		ImageButton usButton = new ImageButton("langus", new ResourceReference(BaseWBSPage.class, "us.png")) {
			@Override
			public void onSubmit() {
				ProjectListPanel.this.locale = Locale.US;
			}
		};
		localeForm.add(usButton);
		add(localeForm);
		final IResourceStream pdfResourceStream = new AbstractResourceStreamWriter() {
			public void write(final OutputStream output) {
				Document document = new Document();
				try {
					PdfWriter.getInstance(document, output);
					document.open();
					// document.add(new
					// Paragraph("WBS-Projektliste"));
					// document.add(new Paragraph(""));
					PdfPTable table = new PdfPTable(new float[] { 1f, 1f, 2f, 1f });
					PdfPCell cell = new PdfPCell(new Paragraph("WBS-Projektliste"));
					cell.setColspan(4);
					cell.setGrayFill(0.8f);
					table.addCell(cell);
					table.addCell("ID");
					table.addCell("Kürzel");
					table.addCell("Titel");
					table.addCell("Budget in PT");
					for (Project project : projects) {
						table.addCell("" + project.getId());
						table.addCell(project.getKuerzel());
						table.addCell(project.getName());
						table.addCell("" + project.getBudget());
					}
					document.add(table);
					document.close();
				} catch (DocumentException e) {
					throw new RuntimeException(e);
				}
			}

			public String getContentType() {
				return "application/pdf";
			}
		};
		WebResource projectsResource = new WebResource() {
			{
				setCacheable(false);
			}

			@Override
			public IResourceStream getResourceStream() {
				return pdfResourceStream;
			}

			@Override
			protected void setHeaders(final WebResponse response) {
				super.setHeaders(response);
				// response.setAttachmentHeader("projekte.pdf");
			}
		};
		WebResource projectsResourceDL = new WebResource() {
			{
				setCacheable(false);
			}

			@Override
			public IResourceStream getResourceStream() {
				return pdfResourceStream;
			}

			@Override
			protected void setHeaders(final WebResponse response) {
				super.setHeaders(response);
				response.setAttachmentHeader("projekte.pdf");
			}
		};
		ResourceLink pdfDownload = new ResourceLink("pdfDownload", projectsResourceDL);
		ResourceLink pdfPopup = new ResourceLink("pdfPopup", projectsResource);
		PopupSettings popupSettings = new PopupSettings(PopupSettings.STATUS_BAR);
		popupSettings.setWidth(500);
		popupSettings.setHeight(700);
		pdfPopup.setPopupSettings(popupSettings);
		Link pdfReqTarget = new Link("pdfReqTarget") {
			@Override
			public void onClick() {
				RequestCycle.get().setRequestTarget(new ResourceStreamRequestTarget(pdfResourceStream, "projekte.pdf"));
			}
		};
		add(pdfReqTarget);
		add(pdfDownload);
		add(pdfPopup);
		add(new OrderByBorder("orderByKuerzel", "kuerzel", projectProvider));
		add(new OrderByBorder("orderByName", "name", projectProvider));
		add(new OrderByBorder("orderByBudget", "budget", projectProvider));
		add(new OrderByBorder("orderByCostPerHour", "costPerHour", projectProvider));
		add(new OrderByBorder("orderByStart", "start", projectProvider));
		add(new OrderByBorder("orderByEnde", "ende", projectProvider));
		add(new OrderByBorder("orderByState", "state", projectProvider));
		add(new PagingNavigator("projectsNavigator", dataView));
	}

	@Override
	public Locale getLocale() {
		if (locale == null) {
			return super.getLocale();
		}
		return locale;
	}
}
