package ru.hibernate.course;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import ru.hibernate.course.converter.BirthdayConverter;
import ru.hibernate.course.entity.User;

@UtilityClass
public class HibernateUtil {
    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAttributeConverter(new BirthdayConverter(), Boolean.TRUE);
        configuration.registerTypeOverride(new JsonBinaryType());

        configuration.addAnnotatedClass(User.class);

        configuration.configure();
        return configuration.buildSessionFactory();
    }
}
