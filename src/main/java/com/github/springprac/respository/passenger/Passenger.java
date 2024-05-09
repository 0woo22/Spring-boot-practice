package com.github.springprac.respository.passenger;

import com.github.springprac.respository.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "passengerId")
@Builder
@Entity
@Table(name = "passenger")
public class Passenger {
    @Id
    @Column(name = "passenger_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer passengerId;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , unique = true , nullable = false) // 패신저는 무조건 유저엔티티가 있어야하므로 null 안됨
    private UserEntity user;


    @Column(name = "passport_num", length = 50)
    private String passportNum;
}