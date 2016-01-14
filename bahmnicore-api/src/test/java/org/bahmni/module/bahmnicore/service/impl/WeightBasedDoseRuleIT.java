package org.bahmni.module.bahmnicore.service.impl;

import org.bahmni.module.bahmnicore.BaseIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class WeightBasedDoseRuleIT extends BaseIntegrationTest{

    @Autowired
    private WeightBasedDoseRule bMIRule;

    @Before
    public void setUp() throws Exception {
        executeDataSet("RuleTestData.xml");
    }

    @Test
    public void shouldThrowExceptionWeightNotAvailableWhenWeightObsDoesNotExist() {
        Double calculatedDoseForRule;
        try {
            calculatedDoseForRule = bMIRule.getDose("person_1032_uuid", 5.0);
        } catch (Exception e) {
            calculatedDoseForRule = null;
            assertEquals(e.getMessage(), "Weight is not available");
        }
        assertEquals(calculatedDoseForRule, null);
    }
}