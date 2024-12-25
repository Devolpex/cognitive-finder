package org.cognitivefinder.patient.modules.patient;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

    // Optional<Patient> findAllByClientId(String clientId);

    // This will return a list of patients matching the clientId
    List<Patient> findAllByClientId(String clientId);
    
}