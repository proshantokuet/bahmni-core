package org.bahmni.module.bahmnicore.dao;

import org.bahmni.module.bahmnicore.contract.patient.response.PatientResponse;
import org.openmrs.Patient;
import org.openmrs.RelationshipType;

import java.util.List;

public interface PatientDao {

    public List<PatientResponse> getPatients(String identifier, String name, String customAttribute,
                                             String addressFieldName, String addressFieldValue, Integer length, Integer offset,
                                             String[] patientAttributes, String programAttribute, String programAttributeField,
                                             String[] addressSearchResultFields, String[] patientSearchResultFields, String loginLocationUuid, Boolean filterPatientsByLocation, Boolean filterOnAllIdentifiers);

    List<PatientResponse> getPatientsUsingLuceneSearch(String identifier, String name, String customAttribute,
                                                       String addressFieldName, String addressFieldValue, Integer length,
                                                       Integer offset, String[] customAttributeFields, String programAttributeFieldValue,
                                                       String programAttributeFieldName, String[] addressSearchResultFields,
                                                       String[] patientSearchResultFields, String loginLocationUuid, Boolean filterPatientsByLocation, Boolean filterOnAllIdentifiers);

    public Patient getPatient(String identifier);

    public List<Patient> getPatients(String partialIdentifier, boolean shouldMatchExactPatientId);

    public List<RelationshipType> getByAIsToB(String aIsToB);
    
    public int updatePatientAttributeInfoInPerson(String uic,String registrationDate,String mobileNo,String motherName,int personId,String identifier);
}
