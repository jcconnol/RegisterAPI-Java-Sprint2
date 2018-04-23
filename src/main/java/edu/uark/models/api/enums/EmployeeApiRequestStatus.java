package edu.uark.models.api.enums;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeApiRequestStatus {
	OK(0),
	INVALID_INPUT(1),
	UNKNOWN_ERROR(2),
	NOT_FOUND(3),
	EMPLOYEE_ID_ALREADY_EXISTS(4),
	INVALID_EMPLOYEE_ID_CHANGE(5);
	
	public int getValue() {
		return value;
	}

	public static EmployeeApiRequestStatus map(int key) {
		if (valueMap == null) {
			valueMap = new HashMap<Integer, EmployeeApiRequestStatus>();

			for (EmployeeApiRequestStatus status : EmployeeApiRequestStatus.values()) {
				valueMap.put(status.getValue(), status);
			}
		}
		
		return (valueMap.containsKey(key) ? valueMap.get(key) : EmployeeApiRequestStatus.UNKNOWN_ERROR);
	}
	
	private int value;

	private static Map<Integer, EmployeeApiRequestStatus> valueMap = null;

	private EmployeeApiRequestStatus(int value) {
		this.value = value;
	}
}