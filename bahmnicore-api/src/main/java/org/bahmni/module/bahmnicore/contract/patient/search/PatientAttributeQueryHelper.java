package org.bahmni.module.bahmnicore.contract.patient.search;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.openmrs.api.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientAttributeQueryHelper {
	private String customAttribute;
	private List<Integer> personAttributeTypeIds;
	private List<Integer> personAttributeResultsIds;

	public PatientAttributeQueryHelper(String customAttribute, List<Integer> personAttributeTypeIds, List<Integer> attributeIds) {
		this.customAttribute = customAttribute;
		this.personAttributeTypeIds = personAttributeTypeIds;
		this.personAttributeResultsIds = attributeIds;
	}

	public String selectClause(String select){
		String selectClause = "''";

		if(personAttributeResultsIds.size() > 0)
			selectClause =
		"concat('{',group_concat(DISTINCT (coalesce(concat('\"',attrt_results.name,'\":\"', REPLACE(REPLACE(coalesce(pattr_results.value),'\\\\','\\\\\\\\'),'\"','\\\\\"'),'\"'))) SEPARATOR ','),'}')";

		return String.format("%s,%s as customAttribute", select, selectClause);
	}

	public String appendToJoinClause(String join){
		    if(personAttributeTypeIds.size() > 0)
				join += " LEFT OUTER JOIN person_attribute pattrln on pattrln.person_id = p.person_id and pattrln.person_attribute_type_id in ("+ StringUtils.join(personAttributeTypeIds, ',')+") ";
		    if(personAttributeResultsIds.size() > 0)
				join +=
				" LEFT OUTER JOIN person_attribute pattr_results on pattr_results.person_id = p.person_id and pattr_results.person_attribute_type_id in ("+ StringUtils.join(personAttributeResultsIds, ',')+") " +
				" LEFT OUTER JOIN person_attribute_type attrt_results on attrt_results.person_attribute_type_id = pattr_results.person_attribute_type_id and pattr_results.voided = 0 ";

		    return join;
	}

	public String appendToWhereClause(String where){
		if(StringUtils.isEmpty(customAttribute) || personAttributeTypeIds.size() == 0){
			return where;
		}
		return combine(where, "and", enclose(" p.contact_no like "+ "'%" + StringEscapeUtils.escapeSql(customAttribute) + "%'"+" OR p.uic like "+ "'%" + StringEscapeUtils.escapeSql(customAttribute) + "%'"+" OR p.mothers_name like "+ "'%" + StringEscapeUtils.escapeSql(customAttribute) + "%' "));
	}

	public Map<String,Type> addScalarQueryResult(){
		Map<String,Type> scalarQueryResult = new HashMap<>();
		scalarQueryResult.put("customAttribute", StandardBasicTypes.STRING);
		return scalarQueryResult;
	}

	private String combine(String query, String operator, String condition) {
		return String.format("%s %s %s", query, operator, condition);
	}

	private String enclose(String value) {
		return String.format("(%s)", value);
	}
}
