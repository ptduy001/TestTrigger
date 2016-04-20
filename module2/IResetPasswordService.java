package com.timo.dmz.user.auth.service;

import com.timo.dmz.common.dto.CommonResponse;
import com.timo.dmz.user.auth.common.dto.ResetPasswordPayload;

/**
 * Created by ptduy on 9/23/15.
 */
public interface IResetPasswordService {

    /**
     * Request new token for reset password process
     *
     * @param payload contains request body
     * @return
     */
    CommonResponse requestToken(ResetPasswordPayload payload);

    /**
     * verify if token is valid or not
     *
     * @param payload
     * @param immediatelyExpire if this flag is true, then token will be expired immediately after a valid verification
     * @return
     */
    CommonResponse verifyToken(ResetPasswordPayload payload, boolean immediatelyExpire);

    /**
     * verify if token is valid or not
     *
     * @param payload
     * @return
     */
    CommonResponse verifyToken(ResetPasswordPayload payload);

    /**
     * submit modified password
     * immediately expired after a success submission
     *
     * @param payload
     * @return
     */
    CommonResponse submitPassword(ResetPasswordPayload payload);
}
