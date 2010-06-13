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

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.tree.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation;
import org.apache.wicket.extensions.markup.html.tree.table.IColumn;
import org.apache.wicket.extensions.markup.html.tree.table.IRenderable;
import org.apache.wicket.extensions.markup.html.tree.table.PropertyTreeColumn;
import org.apache.wicket.extensions.markup.html.tree.table.TreeTable;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Alignment;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Unit;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.AbstractTree;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.senacor.domain.project.Project;
import com.senacor.domain.project.Task;
import com.senacor.domain.project.TaskManager;
import com.senacor.domain.project.TaskStatus;

public class EditableTreeTablePanel extends Panel {
	private static final ResourceReference ADD = new ResourceReference(EditableTreeTablePanel.class, "res/add.png");
	private static final ResourceReference GO_BUTTOM = new ResourceReference(EditableTreeTablePanel.class, "res/go-bottom.png");
	private static final ResourceReference GO_DOWN = new ResourceReference(EditableTreeTablePanel.class, "res/go-down.png");
	private static final ResourceReference GO_TOP = new ResourceReference(EditableTreeTablePanel.class, "res/go-top.png");
	private static final ResourceReference GO_UP = new ResourceReference(EditableTreeTablePanel.class, "res/go-up.png");
	private static final ResourceReference REMOVE = new ResourceReference(EditableTreeTablePanel.class, "res/remove.png");
	@SpringBean()
	private TaskManager taskManager;
	private TreeTable tree;
	private DefaultTreeModel defaultTreeModel;
	private final Project project;

	public EditableTreeTablePanel(String id, Project project) {
		super(id);
		this.project = project;
		List<Task> tasks = taskManager.findTasks(project);
		IColumn columns[] = new IColumn[] {
				new PropertyTreeColumn(new ColumnLocation(Alignment.LEFT, 10, Unit.EM), "Tree Column", "userObject.id"),
				new EditableTextFieldColumn(new ColumnLocation(Alignment.LEFT, 6, Unit.EM), "Name", "userObject.name"),
				new EditableTextFieldColumn(new ColumnLocation(Alignment.MIDDLE, 30, Unit.PROPORTIONAL), "Beschreibung", "userObject.beschreibung"),
				new EditableDropDownChoiceColumn(new ColumnLocation(Alignment.RIGHT, 10, Unit.EM), "Status", "userObject.status", Arrays.asList(TaskStatus
						.values())), new AbstractColumn(new ColumnLocation(Alignment.RIGHT, 30, Unit.PX), "") {
					public IRenderable newCell(TreeNode node, int level) {
						return null;
					}

					public Component newCell(MarkupContainer parent, String id, final TreeNode node, int level) {
						return new LinkPanel(id, REMOVE, node) {
							@Override
							protected void handle(TreeNode treeNode, AjaxRequestTarget target) {
								// Node
								DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeNode;
								Task nodeTask = (Task) node.getUserObject();
								// Parent
								DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
								Task parentTask = (Task) parent.getUserObject();
								// Entferne den Task aus den Subtaks
								// parentTask.getSubtasks().remove(
								// defaultTreeModel
								// .getIndexOfChild(parent, node));
								// Entfernung des Knotens und neu rendern
								defaultTreeModel.removeNodeFromParent(node);
								tree.modelChanged();
								tree.updateTree(target);
							}

							@Override
							protected boolean show() {
								if (node.getParent() == null) {
									return false;
								}
								return true;
							}
						};
					}
				}, new AbstractColumn(new ColumnLocation(Alignment.RIGHT, 30, Unit.PX), "") {
					public IRenderable newCell(TreeNode node, int level) {
						return null;
					}

					public Component newCell(MarkupContainer parent, String id, final TreeNode node, int level) {
						return new LinkPanel(id, ADD, node) {
							@Override
							protected void handle(TreeNode treeNode, AjaxRequestTarget target) {
								// Node
								DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeNode;
								Task nodeTask = (Task) node.getUserObject();
								// Füge den Task zu den Subtasks hinzu
								Task newTask = new Task();
								nodeTask.getSubtasks().add(newTask);
								defaultTreeModel.insertNodeInto(new DefaultMutableTreeNode(newTask), node, 0);
								tree.getTreeState().expandNode(node);
								tree.getTreeState().selectNode(node, true);
								tree.invalidateAll();
								tree.modelChanged();
								tree.updateTree(target);
							}

							@Override
							protected boolean show() {
								return true;
							}
						};
					}
				}, new AbstractColumn(new ColumnLocation(Alignment.RIGHT, 30, Unit.PX), "") {
					public IRenderable newCell(TreeNode node, int level) {
						return null;
					}

					public Component newCell(MarkupContainer parent, String id, final TreeNode node, int level) {
						return new LinkPanel(id, GO_UP, node) {
							@Override
							protected void handle(TreeNode node, AjaxRequestTarget target) {
								DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) node;
								DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node1.getParent();
								int index = defaultTreeModel.getIndexOfChild(parent, node1);
								defaultTreeModel.removeNodeFromParent(node1);
								defaultTreeModel.insertNodeInto(node1, parent, index - 1);
								tree.getTreeState().expandNode(parent);
								tree.invalidateAll();
								tree.modelChanged();
								tree.updateTree(target);
							}

							@Override
							protected boolean show() {
								if (node.getParent() == null || defaultTreeModel.getIndexOfChild(node.getParent(), node) == 0) {
									return false;
								}
								return true;
							}
						};
					}
				}, new AbstractColumn(new ColumnLocation(Alignment.RIGHT, 30, Unit.PX), "") {
					public IRenderable newCell(TreeNode node, int level) {
						return null;
					}

					public Component newCell(MarkupContainer parent, String id, final TreeNode node, int level) {
						return new LinkPanel(id, GO_DOWN, node) {
							@Override
							protected void handle(TreeNode node, AjaxRequestTarget target) {
								DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) node;
								DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node1.getParent();
								int index = defaultTreeModel.getIndexOfChild(parent, node1);
								defaultTreeModel.removeNodeFromParent(node1);
								defaultTreeModel.insertNodeInto(node1, parent, index + 1);
								tree.getTreeState().expandNode(parent);
								tree.invalidateAll();
								tree.modelChanged();
								tree.updateTree(target);
							}

							@Override
							protected boolean show() {
								if (node.getParent() == null
										|| defaultTreeModel.getIndexOfChild(node.getParent(), node) == defaultTreeModel.getChildCount(node.getParent()) - 1) {
									return false;
								}
								return true;
							}
						};
					}
				} };
		Form form = new Form("form");
		form.setOutputMarkupId(true);
		add(form);
		//
		defaultTreeModel = createTreeModel(tasks);
		tree = new TreeTable("treeTable", defaultTreeModel, columns);
		tree.setOutputMarkupId(true);
		tree.getTreeState().collapseAll();
		form.add(tree);
		form.add(new AjaxButton("submit") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form1) {
				Task rootTask = getTaskStructure((DefaultMutableTreeNode) defaultTreeModel.getRoot());
				taskManager.saveTasks(EditableTreeTablePanel.this.project, rootTask.getSubtasks());
			}
		});
	}

	private Task getTaskStructure(DefaultMutableTreeNode treeNode) {
		// Zugehörigen Task ermittel
		Task task = (Task) ((DefaultMutableTreeNode) treeNode).getUserObject();
		// Die Kinder erst löschen
		task.getSubtasks().clear();
		// und dann neu aufbauen
		if (!treeNode.isLeaf()) {
			Enumeration<DefaultMutableTreeNode> children = treeNode.children();
			while (children.hasMoreElements()) {
				task.getSubtasks().add(getTaskStructure(children.nextElement()));
			}
		}
		return task;
	}

	/**
	 * @see BaseTreePage#getTree()
	 */
	protected AbstractTree getTree() {
		return tree;
	}

	/**
	 * Creates the model that feeds the tree.
	 * 
	 * @return New instance of tree model.
	 */
	protected DefaultTreeModel createTreeModel(List<Task> tasks) {
		return convertToTreeModel(tasks);
	}

	private DefaultTreeModel convertToTreeModel(List<Task> tasks) {
		DefaultTreeModel model = null;
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new Task());
		add(rootNode, tasks);
		model = new DefaultTreeModel(rootNode);
		return model;
	}

	private void add(DefaultMutableTreeNode parent, List<Task> tasks) {
		for (Task task : tasks) {
			if (!task.getSubtasks().isEmpty()) {
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(task);
				parent.add(child);
				add(child, task.getSubtasks());
			} else {
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(task);
				parent.add(child);
			}
		}
	}

	public static class EditableTextFieldColumn extends PropertyEditableColumn {
		public EditableTextFieldColumn(ColumnLocation location, String header, String propertyExpression) {
			super(location, header, propertyExpression);
		}

		@Override
		public Component newCell(MarkupContainer parent, String id, TreeNode node, int level) {
			return new EditablePanel(id, new PropertyModel(node, getPropertyExpression()));
		}
	}

	public static class EditableDropDownChoiceColumn extends PropertyEditableColumn {
		private final List values;

		public EditableDropDownChoiceColumn(ColumnLocation location, String header, String propertyExpression, List values) {
			super(location, header, propertyExpression);
			this.values = values;
		}

		@Override
		public Component newCell(MarkupContainer parent, String id, TreeNode node, int level) {
			return new DropDownBoxPanel(id, new PropertyModel(node, getPropertyExpression()), values);
		}
	}
}
