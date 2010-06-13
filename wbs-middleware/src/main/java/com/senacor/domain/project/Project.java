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

package com.senacor.domain.project;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.senacor.core.bo.BusinessObject;
import com.senacor.domain.user.User;

@Entity
@Table
public class Project extends BusinessObject {
	public String getKuerzel() {
		return kuerzel;
	}

	public void setKuerzel(final String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

	public Date getEnde() {
		return ende;
	}

	public void setEnde(final Date ende) {
		this.ende = ende;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(final List<User> users) {
		this.users = users;
	}

	public Long getBudget() {
		return budget;
	}

	public void setBudget(final Long budget) {
		this.budget = budget;
	}

	public ProjectState getState() {
		return state;
	}

	public void setState(final ProjectState state) {
		this.state = state;
	}

	public void setTasks(final List<Task> tasks) {
		this.tasks = tasks;
	}

	public BigDecimal getCostPerHour() {
		return costPerHour;
	}

	public void setCostPerHour(final BigDecimal costPerHour) {
		this.costPerHour = costPerHour;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@Column(name = "kuerzel", length = 5)
	private String kuerzel;
	@Column(name = "name", length = 50)
	private String name;
	@Temporal(TemporalType.DATE)
	private Date start;
	@Column(name = "ende")
	@Temporal(TemporalType.DATE)
	private Date ende;
	@OneToMany(fetch = FetchType.EAGER)
	@OrderBy("username")
	private List<User> users = new ArrayList<User>();
	@Column(name = "budget")
	private Long budget;
	@Column(name = "cost")
	private BigDecimal costPerHour;
	@OneToMany(fetch = FetchType.LAZY)
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@OrderBy("name")
	private List<Task> tasks;
	@Enumerated(EnumType.STRING)
	private ProjectState state;
	@Column(name = "progress")
	@Enumerated(EnumType.STRING)
	private ProgressState progress = ProgressState.YELLOW;

	public ProgressState getProgress() {
		return progress;
	}

	public void setProgress(final ProgressState progress) {
		this.progress = progress;
	}

	@Override
	public Long getId() {
		return id;
	}

	public List<Task> getTasks() {
		if (tasks == null) {
			tasks = new ArrayList<Task>();
		}
		return tasks;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public void removeUser(final User user) {
		users.remove(user);
	}

	public void addTask(final Task task) {
		getTasks().add(task);
	}

	public void addUser(final User user) {
		getUsers().add(user);
	}
}
