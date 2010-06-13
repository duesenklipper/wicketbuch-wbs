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

package com.senacor.domain.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.springframework.util.CollectionUtils;

import com.senacor.core.bo.BusinessObject;

@Entity
@Table
@Indexed
public class User extends BusinessObject {
	public final static String NAME = "name";
	public final static String VORNAME = "vorname";
	public final static String USERNAME = "username";
	// Simple Attributes
	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String name;
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String vorname;
	private Date birthDate;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String username;
	private String passwort;
	private Boolean senacorEmployee;
	@CollectionOfElements(fetch = FetchType.EAGER)
	private Set<Knowledge> knowledges = new HashSet<Knowledge>();
	// Complex Attributes
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Role> roles = new HashSet<Role>();
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ContactData> contactData = new HashSet<ContactData>();
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Address address = new Address();

	public ContactData getMainContact() {
		if (CollectionUtils.isEmpty(getContactData())) {
			return null;
		}
		return getContactData().iterator().next();
	}

	// Getter / Setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<ContactData> getContactData() {
		if (contactData.isEmpty()) {
			// Privat
			contactData.add(new ContactData());
			// Geschäftlich
			contactData.add(new ContactData());
		}
		return contactData;
	}

	public void setContactData(Set<ContactData> contactData) {
		this.contactData = contactData;
	}

	public Address getAddress() {
		if (address == null) {
			address = new Address();
		}
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Boolean getSenacorEmployee() {
		if (senacorEmployee == null) {
			senacorEmployee = Boolean.FALSE;
		}
		return senacorEmployee;
	}

	public void setSenacorEmployee(Boolean senacorEmployee) {
		this.senacorEmployee = senacorEmployee;
	}

	public Set<Knowledge> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(Set<Knowledge> knowledges) {
		this.knowledges = knowledges;
	}
}
