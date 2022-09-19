package ru.hibernate.course;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hibernate.course.entity.Company;
import ru.hibernate.course.entity.PersonalInfo;
import ru.hibernate.course.entity.Role;
import ru.hibernate.course.entity.User;
import ru.hibernate.course.entity.type.Birthday;

import java.time.LocalDate;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Company company = Company.builder()
                    .name("Google")
                    .build();
            User user = User.builder()
                    .username("ME1234")
                    .personalInfo(PersonalInfo.builder()
                            .firstName("Vania")
                            .lastName("Ivanov")
                            .age(28)
                            .birthDate(new Birthday(LocalDate.of(2102, 11, 21)))
                            .build())
                    .role(Role.ADMIN)
                    .company(company)
                    .build();
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.save(company);
                session1.save(user);
                User userFromDb = session1.get(User.class, user.getId());
                userFromDb.setRole(Role.USER); //при закрытии транзакции произойдет update тк сущность в persistent контексте
//            session.evict(userFromDb); //удалить сущность из кэша сесии
//            session.clear(); //почистить весь кэш сесии
//            session.close(); //закрыть сесию

                session1.getTransaction().commit();
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                session2.refresh(user); //Обновить сущность до состояния базы данных
                session2.merge(user); //Обновить базу до состояния user
                session2.getReference(User.class, 1); //Получает прокси для затычки ленивой коллекции

                session2.getTransaction().commit();
            }
        }
    }
}
