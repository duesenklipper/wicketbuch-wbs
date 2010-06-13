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

package com.senacor.core.manager;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.senacor.core.bo.BusinessObject;

public abstract class BaseManagerImpl<T extends BusinessObject> extends ManagerImpl implements BaseManager<T> {
	protected final Class<T> boClass;

	public BaseManagerImpl() {
		Type[] types = findParameterizedSuperclass().getActualTypeArguments();
		boClass = (Class<T>) types[0];
	}

	private ParameterizedType findParameterizedSuperclass() {
		Class c = this.getClass();
		Type t;
		do {
			t = c.getGenericSuperclass();
			c = c.getSuperclass();
		} while ((t == null) || !(t instanceof ParameterizedType));
		return (ParameterizedType) t;
	}

	public T save(final T bo) {
		Session session = getSession();
		session.save(bo);
		return bo;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public T saveOrUpdate(final T bo) {
		Session session = getSession();
		session.saveOrUpdate(bo);
		return bo;
	}

	public T retrieve(final long id) {
		Session session = getSession();
		return (T) session.get(boClass, id);
	}

	public void delete(final T t) {
		Session session = getSession();
		session.delete(t);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Session session = getSession();
		return session.createCriteria(boClass).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).addOrder(Order.asc("id")).list();
	}

	public List<T> find(final T t) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(boClass);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(boClass);
		BeanWrapper beanWrapper = new BeanWrapperImpl(t);
		for (PropertyDescriptor descriptor : descriptors) {
			if (!descriptor.getName().equals("class") && !descriptor.getName().equals("id")) {
				Object value = beanWrapper.getPropertyValue(descriptor.getName());
				if (value != null) {
					criteria.add(Restrictions.eq(descriptor.getName(), value));
				}
			}
		}
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}

	protected <A extends BusinessObject> A reattach(final A objectToLock) {
		getSession().lock(objectToLock, LockMode.NONE);
		return objectToLock;
	}
}
