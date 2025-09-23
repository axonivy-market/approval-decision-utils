package com.axonivy.utils.approvaldecision.repository.service;

import java.util.List;

import ch.ivyteam.ivy.environment.Ivy;

/**
 * @author pdminh
 *
 * @param <T>
 */
public abstract class BaseRepository<T> {
	private static int DEFAULT_SEARCH_LIMIT = 5000;

	abstract protected Class<T> getType();
	

	public T findById(String id) {
		T obj = (T) Ivy.repo().find(id, getType());
		if (obj != null) {
			return obj;
		}
		return null;
	}

	public T save(T obj) {
		Ivy.repo().save(obj);
		return obj;
	}

	public void delete(T obj) {
		Ivy.repo().delete(obj);
	}

	public void deleteById(String id) {
		Ivy.repo().deleteById(id);

	}

	public List<T> findAll() {
		return Ivy.repo().search(getType()).limit(DEFAULT_SEARCH_LIMIT).execute().getAll();
	}

	public List<T> saveAll(List<T> objs) {
		for (T t : objs) {
			save(t);
		}

		return findAll();
	}

	public void deleteAll(List<T> objs) {
		for (T t : objs) {
			delete(t);
		}
	}

}
