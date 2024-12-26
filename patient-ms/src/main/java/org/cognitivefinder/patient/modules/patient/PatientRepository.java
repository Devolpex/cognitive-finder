package org.cognitivefinder.patient.modules.patient;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
    List<Patient> findAllByClientId(String clientId);
}