package ru.hibernate.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hibernate.course.entity.type.Birthday;

import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PersonalInfo {
    private String firstName;
    private String lastName;
    //    @Convert(converter = BirthdayConverter.class) конвертер добавлен через configuration
    private Birthday birthDate;
    private Integer age;
}
