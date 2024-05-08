package com.github.springprac.respository.passenger;

import java.util.Optional;

public interface PassengerRepository {
    Optional<Passenger> findPassengerByUserId(Integer userId);

}
