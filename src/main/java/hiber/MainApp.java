package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);


      User vasya = new User("Ivaan", "Ivanov", "Ivanov@mail.ru");
      User sidr = new User("Sidr", "Sidorov", "Sidorov@mail.ru");
      User petr = new User("Petr", "Petrov", "Petrov@mail.ru");

      Car mercedes = new Car("Mercedes", 120);
      Car bmw = new Car("BMW", 320);
      Car kamaz = new Car("Kamaz", 2122);

      userService.add(vasya.setCar(mercedes).setUser(vasya));
      userService.add(sidr.setCar(bmw).setUser(sidr));
      userService.add(petr.setCar(kamaz).setUser(petr));

      //    userService.deleteAllUsers(); // очистка всех таблиц

//      userService.drop(); // удаление таблицы Car


      System.out.println(userService.getUserByCar("BMW", 320));
      System.out.println(userService.getUserByCar("Kamaz", 2122));

      context.close();
   }
}