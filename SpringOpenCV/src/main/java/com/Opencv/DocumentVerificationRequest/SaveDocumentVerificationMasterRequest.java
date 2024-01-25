package com.Opencv.DocumentVerificationRequest;

import java.util.List;
import java.util.Map;

import com.Opencv.commonrequest.CommonAPIDataRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveDocumentVerificationMasterRequest extends CommonAPIDataRequest {


    @JsonProperty("invoice_number")
    private String invoiceNumber;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_type")
    private Integer userType;

    @JsonProperty("business_id")
    private String businessId;

    @JsonProperty("payment_link")
    private String paymentLink;

    @JsonProperty("target_user_id")
    private String targetUserId;

    @JsonProperty("target_user_type")
    private Integer targetUserType;

    @JsonProperty("target_user_info")
    private Map<String, Object> targetUserinfo;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("note")
    private String note;

    @JsonProperty("expiry_date")
    private Long expiryDate;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("product_info")
    private List<Map<String, Object>> productInfo;

    @JsonProperty("tax_info")
    private List<Map<String, Object>> taxInfo;

    @JsonProperty("reference_txn_id")
    private String referenceTxnId;

    public Boolean checkBadRequest() {
//        if (StringUtils.isEmpty(this.getCompany_id())) {
//            return true;
//        }
//        if (StringUtils.isEmpty(this.getPaymentLink())) {
//            return true;
//        }
//        if (StringUtils.isEmpty(this.getTargetUserId())) {
//            return true;
//        }
//        if (Objects.isNull(this.getTargetUserType())) {
//            return true;
//        }
//        if (StringUtils.isEmpty(this.getBusinessId())) {
//            return true;
//        }
//        if (StringUtils.isEmpty(this.getNote())) {
//            return true;
//        }
//        if (Objects.isNull(this.getAmount())) {
//            return true;
//        }
        return false;
    }
}
