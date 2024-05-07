package com.github.springprac.respository.passenger;

public interface PassengerRepository {
    Passenger findPassengerByUserId(Integer userId);

}
