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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.RenderedDynamicImageResource;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.senacor.domain.project.ProjectManager;

public class ProjectStatisticsPanel extends Panel {
	private static final int HEIGHT = 400;
	private static final int WIDTH = 400;
	@SpringBean
	private ProjectManager projectManager;

	public ProjectStatisticsPanel(final String id) {
		super(id);
		add(new Image("openlast30", new RenderedDynamicImageResource(WIDTH, HEIGHT) {
			@Override
			protected boolean render(final Graphics2D graphics) {
				List<Integer> openLast30 = projectManager.getLast30DaysOpenTaskCount();
				int max = Collections.max(openLast30);
				prepareGraphics(graphics);
				int xStep = 10;
				double yScale = 1.0 * HEIGHT / max;
				int x = 0, y = (int) (yScale * openLast30.get(0));
				int xOld = x, yOld = y;
				for (Integer tasks : openLast30) {
					y = (int) (yScale * tasks);
					graphics.setColor(Color.BLACK);
					graphics.drawLine(xOld, yOld, x, y);
					graphics.setColor(Color.RED);
					graphics.drawLine(x - 2, y - 2, x + 2, y + 2);
					graphics.drawLine(x - 2, y + 2, x + 2, y - 2);
					xOld = x;
					yOld = y;
					x += xStep;
				}
				return true;
			}

			private void prepareGraphics(final Graphics2D graphics) {
				graphics.setColor(Color.WHITE);
				graphics.fillRect(0, 0, WIDTH, HEIGHT);
				graphics.setColor(Color.BLACK);
				graphics.translate(50, HEIGHT);
				graphics.scale(1, -1.0);
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}
		}));
	}
}
