package com.Opencv.commonrequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonAPIDataRequest {

    public String company_id;
    public String request_id;
    public String ip_address;
    public String language;
    private String token_user_id;
}