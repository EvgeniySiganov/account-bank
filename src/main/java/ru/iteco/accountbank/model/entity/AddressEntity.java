package ru.iteco.accountbank.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses", schema = "ad")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String country;
    private String city;
    private String street;
    private String home;

    @OneToOne(mappedBy = "address")
    private UserEntity user;
}
