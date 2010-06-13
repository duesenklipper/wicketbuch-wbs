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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;

public class LayoutColumn extends Border {
	private static final long serialVersionUID = 1L;

	public static enum Position {
		LEFT, INTERMEDIATE, RIGHT
	};

	public static enum ColumnType {
		c50l, c25l, c33l, c38l, c66l, c75l, c62l, c50r, c25r, c33r, c38r, c66r, c75r, c62r
	};

	public LayoutColumn(String id, final ColumnType columnType, final Position position) {
		super(id);
		setRenderBodyOnly(true);
		WebMarkupContainer column = new WebMarkupContainer("column");
		column.add(new AttributeModifier("class", true, new Model<String>(columnType.toString())));
		add(column);
		WebMarkupContainer columnContent = new WebMarkupContainer("columnContent");
		columnContent.add(new AttributeModifier("class", true, new AbstractReadOnlyModel<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				String attrValue;
				switch (position) {
				case LEFT:
					attrValue = "subcl";
					break;
				case RIGHT:
					attrValue = "subcr";
					break;
				default:
					attrValue = "subc";
					break;
				}
				return attrValue;
			}
		}));
		column.add(columnContent);
		columnContent.add(getBodyContainer());
	}
}
