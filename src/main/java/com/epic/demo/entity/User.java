package com.epic.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    public String  id;

    public String username;
    public String email;
    public String password;
}
