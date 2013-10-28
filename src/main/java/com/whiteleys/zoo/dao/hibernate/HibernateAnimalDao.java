package com.whiteleys.zoo.dao.hibernate;

import com.whiteleys.zoo.dao.AnimalDao;
import com.whiteleys.zoo.domain.Animal;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class HibernateAnimalDao extends HibernateDaoSupport implements AnimalDao {


    @SuppressWarnings("unchecked")
    public List<Animal> findAll() {
        return getHibernateTemplate().loadAll(Animal.class);
    }

	public Animal findAnimalById(Long id) {
		List<Animal> animal = getHibernateTemplate().find("FROM Animal WHERE id = ?", id);
		if (animal.size() == 1) {
			return animal.get(0);
		}
		return null;
	}
}