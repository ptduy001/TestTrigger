package com.timo.dmz.user.auth.service;

import com.timo.dmz.common.dto.CommonResponse;
import com.timo.dmz.user.auth.common.dto.OTPPayload;

/**
 * Created by ptduy on 9/22/15.
 */
public interface IOTPService {

    /**
     * create new OTP base on user access token
     *
     * @param payload
     * @return OTP code and refNo
     */
    CommonResponse createOTP(OTPPayload payload);

    /**
     * verify OTP base on user access token
     * @param payload
     * @return
     */
    CommonResponse verifyOTP(OTPPayload payload);


}
