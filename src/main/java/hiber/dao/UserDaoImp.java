package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<Car> listCars() {
      TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car");
      return query.getResultList();
   }


   @Override
   @SuppressWarnings("unchecked")
   public void deleteAllUsers() {
      List<User> users = listUsers();
      for (User user : users) {
         sessionFactory.getCurrentSession().delete(user);
      }
   }

   @Override
   public void drop() {
      sessionFactory.getCurrentSession()
              .createQuery("Delete from Car ")
              .executeUpdate();
   }


   @Override
   @SuppressWarnings("unchecked")
   public List<User> getUserByCar(String model, int series) {
      TypedQuery<User> query = sessionFactory
              .getCurrentSession()
              .createQuery("from Car as car " +
                      " where user.car.model = :model and user.car.series = :series")
              .setParameter("model", model)
              .setParameter("series", series);
      ArrayList userCar = (ArrayList) query.getResultList();


      TypedQuery<User> query2 = sessionFactory
              .getCurrentSession()
              .createQuery("from User as user " +
                      " where user.car.model = :model and user.car.series = :series")
              .setParameter("model", model)
              .setParameter("series", series);
      ArrayList userCar2 = (ArrayList) query2.getResultList();

      userCar.addAll(userCar2);
      return userCar;
   }
}