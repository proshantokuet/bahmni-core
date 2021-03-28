package org.bahmni.module.bahmnicore.contract.patient.search;

import org.apache.commons.lang.StringEscapeUtils;

import static org.apache.commons.lang.StringUtils.isEmpty;

public class PatientIdentifierQueryHelper {

	private String identifier;
	private final Boolean filterOnAllIdentifiers;

	public PatientIdentifierQueryHelper(String identifier, Boolean filterOnAllIdentifiers) {
		this.identifier = identifier;
		this.filterOnAllIdentifiers = filterOnAllIdentifiers;
	}

	public String appendToJoinClause(String join) {
		if (isEmpty(identifier)) {
			join = join.replace("_IDENTIFIER_", " ");
			return join;
		}
		String IdentifierQUery = " where pi.voided IS FALSE AND pi.identifier LIKE '%" +StringEscapeUtils.escapeSql(identifier)+ "%' ";
		String joinWithVisit = join.replace("_IDENTIFIER_", IdentifierQUery);
		//String extraIdentifierQuery = filterOnAllIdentifiers ? ", 'bahmni.extraPatientIdentifierTypes'":"";
//		String query = " JOIN (" +
//				"SELECT pi.patient_id " +
//				"FROM patient_identifier pi " +
//				" where pi.voided IS FALSE AND pi.identifier LIKE '%" +StringEscapeUtils.escapeSql(identifier)+ "%' GROUP BY pi.patient_id) " +
//				" AS matched_patient ON matched_patient.patient_id = p.person_id";
		return joinWithVisit;
	}
}
