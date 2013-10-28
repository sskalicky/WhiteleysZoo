package com.whiteleys.zoo.service.support;

import com.whiteleys.zoo.domain.Animal;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.joda.time.LocalDate;
import com.whiteleys.zoo.service.UserService;
import com.whiteleys.zoo.domain.Sex;
import com.whiteleys.zoo.domain.User;
import com.whiteleys.zoo.dao.UserDao;

import java.util.*;

/**
 * The default implementation of the {@link com.whiteleys.zoo.service.UserService UserService}.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    /**
     * {@inheritDoc}
     */
    public User register(String username, String password, Sex sex, Date dob,
                         String postcode) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
	    user.setSex(sex);
        user.setDateOfBirth(dob);
        user.setPostcode(postcode);
        
        userDao.save(user);

        return user;
    }

    /**
     * {@inheritDoc}
     */
    public User getUser(String username, String password) {
        User user = userDao.find(username, password);

        if(user != null) return user; // a user was found

        // if no user was found we throw an exception
        throw new IllegalArgumentException("Invalid credentials");
    }

    /**
     * {@inheritDoc}
     */
    public boolean authenticate(String username, String password) {
        return userDao.find(username, password) != null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(String username) {
        return userDao.exists(username);
    }

	public List<Animal> getUsersFavouriteAnimals(User user){
		List<Animal> result = Collections.EMPTY_LIST;
		if(user != null){
			result = user.getFavouriteAnimals();
		}
		return result;
	}

	public Set<Long> getUsersFavouriteAnimalsIds(User user) {
		Set<Long> result = new HashSet<Long>();

		if(user != null){
			List<Animal> favouriteAnimals = user.getFavouriteAnimals();
			for(Animal animal: favouriteAnimals){
				result.add(animal.getId());
			}
		}

		return result;
	}

	public User addFavouriteAnimal(User user, Animal animal) {

		if(user != null && animal != null) {
			user.getFavouriteAnimals().add(animal);
			userDao.update(user);
		}

		return user;
	}

	public User removeFavouriteAnimal(User user, Long animalIdToDelete) {
		if(user != null && animalIdToDelete != null){
			for(Animal animal: user.getFavouriteAnimals()){
				if(animal.getId().equals(animalIdToDelete)){
					user.getFavouriteAnimals().remove(animal);
					userDao.update(user);
					break;
				}
			}
		}
		return user;
	}

	@Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
