package ru.hibernate.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profile {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
//    @JoinColumn(name = "user_id")
    @PrimaryKeyJoinColumn //ссылкой является первичный ключ
    private User user;

    private String street;

    private String language;

    public void setUser(User user) {
        this.user = user;
        this.id = user.getId();
    }
}
