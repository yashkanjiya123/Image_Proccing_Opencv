package com.Opencv.commonresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StateDetail {

    @JsonProperty("id")
    private String id;

    @JsonProperty("state_name")
    private String stateName;

    @JsonProperty("country_id")
    private String countryId;

    @JsonProperty("state_code")
    private String stateCode;

    @JsonProperty("created_date")
    private Long createdDate;

    @JsonProperty("is_active")
    private Boolean isActive;

//    public static StateDetail setStateDetail(StateMasterData stateMasterData) {
//        StateDetail stateDetail = new StateDetail();
//        stateDetail.setId(stateMasterData.getId());
//        stateDetail.setStateCode(stateMasterData.getCode());
//        stateDetail.setStateName(stateMasterData.getName());
//        stateDetail.setCountryId(stateMasterData.getCountryId());
//        stateDetail.setCreatedDate(stateMasterData.getCreatedDate());
//        stateDetail.setIsActive(stateMasterData.getIsActive());
//        return stateDetail;
//    }
}
