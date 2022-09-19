package ru.hibernate.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Type;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
//@Access(AccessType.PROPERTY) хибернейт начинает искать аннотации над гетерами вместо полей
public class User {

//    @EmbeddedId Аннотация для составного ключа

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator() для случаев если стратегия генерации ключа - сиквенс
    //Должен быть serializable
    private Long id;

    @Column(unique = true)
    private String username;

    @Embedded //не обязательная аннотация
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date")) //нужна если стратегия мапинга не покрывает (повторяемая)
    private PersonalInfo personalInfo;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Type(type = "jsonb") //Нужно еще дополнительно зарегестрировать в configuration
    private String info;

    @Transient // данное поле не будет сохраняться в базу данных
    @Temporal(TemporalType.TIMESTAMP) // указывается тип даты для старого формата
    private Date date;

//    @Formula и @ColumnTransformer нужны для изменения\шифрования информации при чтении и записи в базу

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    //cascadeType.ALL работает не так как перечисление всех и сначала сохранит вложенную сущность, а потом this
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Profile profile;

}
