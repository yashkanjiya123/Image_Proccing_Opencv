package com.Opencv.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.Opencv.commonresponse.CommonAPIDataResponse;
import com.Opencv.commonresponse.ErrorResponse;
import com.Opencv.commonresponse.SuccessResponse;
import com.Opencv.constant.HeaderConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.micrometer.common.util.StringUtils;


public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	

//	public static SaveDataResponse ConvertResponse(SaveDataRequest saveDataRequest) 
//	{
//       SaveDataResponse saveDataResponse = new SaveDataResponse();
//       saveDataResponse.set_id((String)saveDataRequest.get_id());
//       saveDataResponse.setCharge_category(saveDataRequest.getCharge_category());
//       saveDataResponse.setCharge_fix_amount(saveDataRequest.getCharge_fix_amount());
//       saveDataResponse.setCharge_name(saveDataRequest.getCharge_name());
//       saveDataResponse.setCharge_type(saveDataRequest.getCharge_type());
//       saveDataResponse.setCurrency_id(saveDataRequest.getCurrency_id());
//       saveDataResponse.setIs_include(saveDataRequest.isIs_include());
//       saveDataResponse.setUser_type(saveDataRequest.getUser_type());
//       saveDataResponse.setService_type_key(saveDataRequest.getService_type_key());
//       return saveDataResponse;
//    }
	
	
	public static ResponseEntity<JsonNode> getJsonNodeResponseEntity(CommonAPIDataResponse commonAPIDataResponse) 
	{
        logger.info("check valid");
        if (commonAPIDataResponse.isCheckValidationFailed()) {
            return new ResponseEntity<>(Utils.generateErrorResponse(commonAPIDataResponse.getValidationMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(Utils.generateSuccessResponse(commonAPIDataResponse), HttpStatus.OK);
    }
	

	public static JsonNode generateSuccessResponse(Object object) {
        SuccessResponse successResponse = new SuccessResponse();
        //SaveDataResponse saveDataResponse=(SaveDataResponse) object;
        successResponse.setSuccess(1);
        successResponse.setData(object);
        successResponse.setError(new ArrayList<>());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.convertValue(successResponse, JsonNode.class);
    }
	
    public static JsonNode generateErrorResponse(String message) 
    {
        ErrorResponse successResponse = new ErrorResponse();
        successResponse.setSuccess(0);
        successResponse.setData(new Object());
        ArrayList<String> errors = new ArrayList<>();
        errors.add(message);
        successResponse.setError(errors);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.convertValue(successResponse, JsonNode.class);
    }
    
    public static String replaceString(String keyword) {
        if (keyword.contains("+")) {
            return keyword.replace("+", "\\+");
        }
        if (keyword.contains("%20")) {
            return keyword.replace("%20", "\\s");
        }
        return keyword;
    }
	

    public static Query addCriteriaForDateAndSortingAndPaginationAndSearchKeyword(List<Criteria> criteriaArrayList,List<Criteria> searchKeywordCriteriaList, Long start_date, Long end_date, Integer skip
            , Integer limit, String sorting) 
    {
    	logger.info("data type"+start_date+" "+end_date+" "+skip+" "+limit+" "+sorting);
    	if(start_date!=null && end_date!=null) {
    		criteriaArrayList.add(Utils.setStartDateAndEndDate(start_date, end_date, "created_date"));
    	}

    	logger.info("this is added by all data");
    	for(Criteria i:criteriaArrayList) {
    		logger.info("this is for loop"+i.getCriteriaObject());
    	}

        Criteria criteria = new Criteria();
        if(!criteriaArrayList.isEmpty()) {
        	logger.info("a1");
        	criteria.andOperator(criteriaArrayList);
        }
        if (!Objects.isNull(searchKeywordCriteriaList) && !searchKeywordCriteriaList.isEmpty())
        {
        	logger.info("b1");
            criteria.orOperator(searchKeywordCriteriaList);
        }
        Query query = new Query(criteria);
        if(skip!=null && limit!=null) {
        	logger.info("skip");
        	Utils.setSkipAndLimit(skip, limit, query);
        }
        if(sorting!=null) {
        	Utils.sortBy(query, sorting);
        }
        return query;
    }
    
    public static Query addCriteriaForDateAndSortingAndPaginationAndSearchKeyword(List<Criteria> criteriaArrayList,List<Criteria> searchKeywordCriteriaList)
    {
//    	logger.info("data type"+start_date+" "+end_date+" "+skip+" "+limit+" "+sorting);
//    	if(start_date!=null && end_date!=null) {
//    		criteriaArrayList.add(Utils.setStartDateAndEndDate(start_date, end_date, "created_date"));
//    	}
//
//    	logger.info("this is added by all data");
//    	for(Criteria i:criteriaArrayList) {
//    		logger.info("this is for loop"+i.getCriteriaObject());
//    	}
//
//        Criteria criteria = new Criteria();
//        if(!criteriaArrayList.isEmpty()) {
//        	logger.info("a1");
//        	criteria.andOperator(criteriaArrayList);
//        }
//        if (!Objects.isNull(searchKeywordCriteriaList) && !searchKeywordCriteriaList.isEmpty())
//        {
//        	logger.info("b1");
//            criteria.orOperator(searchKeywordCriteriaList);
//        }
//        Query query = new Query(criteria);
//        if(skip!=null && limit!=null) {
//        	logger.info("skip");
//        	Utils.setSkipAndLimit(skip, limit, query);
//        }
//        if(sorting!=null) {
//        	Utils.sortBy(query, sorting);
//        }
//        return query;

    	//criteriaArrayList.add(Utils.setStartDateAndEndDate(start_date, end_date, "created_date"));

    	
        Criteria criteria = new Criteria();
        criteria.andOperator(criteriaArrayList);
        if (!Objects.isNull(searchKeywordCriteriaList) && !searchKeywordCriteriaList.isEmpty()) {
            criteria.orOperator(searchKeywordCriteriaList);
        }
        Query query = new Query(criteria);
        return query;
        
    }
    
    public static HashMap<String, String> getHeaders(HttpHeaders headers) {
        HashMap<String, String> map = new HashMap<>();
        List<String> headerOptional;
        String data = null;
        if (headers.containsKey(HeaderConstants.COMPANY_ID)) {
            headerOptional = headers.get(HeaderConstants.COMPANY_ID);
            if (!Objects.isNull(headerOptional) && !headerOptional.isEmpty()) {
                data = headerOptional.get(0);
            }
            //Logger.info("Company ID : " + data);
            map.put(HeaderConstants.COMPANY_ID, data);
        }
        if (headers.containsKey(HeaderConstants.AUTHORIZATION)) {
            headerOptional = headers.get(HeaderConstants.AUTHORIZATION);
            if (!Objects.isNull(headerOptional) && !headerOptional.isEmpty()) {
                data = headerOptional.get(0);
            }
            map.put(HeaderConstants.AUTHORIZATION, data);
        }
        if (headers.containsKey(HeaderConstants.REQUEST_ID)) {
            headerOptional = headers.get(HeaderConstants.REQUEST_ID);
            if (!Objects.isNull(headerOptional) && !headerOptional.isEmpty()) {
                data = headerOptional.get(0);
            }
            //Logger.info("Request ID : " + data);
            map.put(HeaderConstants.REQUEST_ID, data);
        }
        if (headers.containsKey(HeaderConstants.REMOTE_ADDRESS)) {
            headerOptional = headers.get(HeaderConstants.REMOTE_ADDRESS);
            if (!Objects.isNull(headerOptional) && !headerOptional.isEmpty()) {
                data = headerOptional.get(0);
            }
            //Logger.info("Remote Address : " + data);
            map.put(HeaderConstants.REMOTE_ADDRESS, data);
        }
        if (headers.containsKey(HeaderConstants.TOKEN_USER_ID)) {
            headerOptional = headers.get(HeaderConstants.TOKEN_USER_ID);
            if (!Objects.isNull(headerOptional) && !headerOptional.isEmpty()) {
                data = headerOptional.get(0);
            }
            //Logger.info("Remote Address : " + data);
            map.put(HeaderConstants.TOKEN_USER_ID, data);
        }
        if (headers.containsKey(HeaderConstants.LANGUAGE)) {
            headerOptional = headers.get(HeaderConstants.LANGUAGE);
            if (!Objects.isNull(headerOptional) && !headerOptional.isEmpty()) {
                data = headerOptional.get(0);
            }

            //Logger.info("Language : " + data);
            map.put(HeaderConstants.LANGUAGE, data);
        }
        return map;

    }
    
    
    
    
    public static Criteria setStartDateAndEndDate(Long startDate, Long endDate, String fieldName) {
        if (!Objects.isNull(startDate) && !Objects.isNull(endDate)) {
            return Criteria.where(fieldName).gte(startDate).lte(endDate);
        } else if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
            return Criteria.where(fieldName).gte(0L).lte(Instant.now().getEpochSecond());
        } else if (!Objects.isNull(startDate)) {
            return Criteria.where(fieldName).gte(0L).lte(startDate);
        } else {
            return Criteria.where(fieldName).gte(0L).lte(endDate);
        }
    }
    
    public static void setSkipAndLimit(Integer skip, Integer limit, Query query) {
        if (!Objects.isNull(skip) && !Objects.isNull(limit)) {
            query.skip(skip).limit(limit);
        } else if (!Objects.isNull(skip)) {
            query.skip(skip);
        } else if (!Objects.isNull(limit)) {
            query.limit(limit);
        }
    }

    
    public static Query sortBy(Query query, String sorting) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> queryFilterMap;
        if (StringUtils.isEmpty(sorting)) {
            return getDefaultSorting(query);
        }
        try {
            sorting = URLDecoder.decode(sorting, StandardCharsets.UTF_8);
            queryFilterMap = objectMapper.readValue(sorting, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (Exception e) {
            //Logger.error("Error : " + ExceptionUtils.getStackTrace(e));
            return getDefaultSorting(query);
        }

        if (Objects.isNull(queryFilterMap) || queryFilterMap.isEmpty()) {
            return getDefaultSorting(query);
        }

        for (String key : queryFilterMap.keySet()) {
            query.with(queryFilterMap.get(key).toString().equalsIgnoreCase("asc")
                    ? Sort.by(Sort.Direction.ASC, key) : Sort.by(Sort.Direction.DESC, key));
        }
        return query;
    }
    
    private static Query getDefaultSorting(Query query) {
        query.with(Sort.by(Sort.Direction.DESC, "created_date"));
        return query;
    }
    
    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return Long.toHexString(uuid.getMostSignificantBits())
                + Long.toHexString(uuid.getLeastSignificantBits());

    }
    
    
	
}
