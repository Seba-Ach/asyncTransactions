# Async Transactions with DB lock

This is a project I made to test out a few concepts and learn about @Transactional, @Async and @Lock annotations used together to allow synchronization of DB entities

The controller has expose 3 endpoints:

localhost:8080/user/1

localhost:8080/user/2

and 

localhost:8080/user/setup

First run the setup endpoint to populate the database with entries

/user/1 = Fetches a UserEntity, increments its appointment number and updates it in DB, then it creates an AppointmentEntity in the DB, but if more than 5 exist it will throw an exception which will rollback all the changes.

/user/2 = Here we use @Async, @Transactional and @Lock mechanisms to create 5 different threads that will execute changeAppointmentsToCurrentThreadId().
UserRepository.findAll() has been annotated with the @Lock with Pessimistic_Write value that wont allow the other transactions running in other threads to read the values from DB until that transaction has finished and released the lock.
