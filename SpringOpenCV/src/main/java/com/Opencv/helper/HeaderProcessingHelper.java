package com.Opencv.helper;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.springframework.http.HttpHeaders;

import com.Opencv.constant.HeaderConstants;
import com.Opencv.utils.Utils;

public class HeaderProcessingHelper {

    public static <T> void setRequestHeaders(T requestObject, HttpHeaders headers) {

        Map<String, String> mapForHeader = Utils.getHeaders(headers);

        String companyId = mapForHeader.get(HeaderConstants.COMPANY_ID);
        String requestId = mapForHeader.get(HeaderConstants.REQUEST_ID);
        String ip_address = mapForHeader.get(HeaderConstants.REMOTE_ADDRESS);
        String tokenUserId = mapForHeader.get(HeaderConstants.TOKEN_USER_ID);

        try {

            if (mapForHeader.containsKey(HeaderConstants.COMPANY_ID)) {
                requestObject.getClass().getSuperclass().getDeclaredMethod("setCompany_id", String.class)
                        .invoke(requestObject, companyId);
            }

            if (mapForHeader.containsKey(HeaderConstants.TOKEN_USER_ID)) {
                requestObject.getClass().getSuperclass().getDeclaredMethod("setToken_user_id"
                        , String.class).invoke(requestObject, tokenUserId);
            }

            if (mapForHeader.containsKey(HeaderConstants.REQUEST_ID)) {
                requestObject.getClass().getSuperclass().getDeclaredMethod("setRequest_id", String.class)
                        .invoke(requestObject, requestId);
            }

            if (mapForHeader.containsKey(HeaderConstants.REMOTE_ADDRESS)) {
                requestObject.getClass().getSuperclass().getDeclaredMethod("setIp_address", String.class)
                        .invoke(requestObject, ip_address);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
           // Logger.error("Error : " + ExceptionUtils.getStackTrace(e));
        }
    }
}
