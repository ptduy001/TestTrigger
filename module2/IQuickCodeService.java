package com.timo.dmz.user.auth.service;

import com.timo.dmz.common.dto.CommonResponse;
import com.timo.dmz.user.auth.common.dto.QuickCodePayload;

/**
 * Created by ptduy on 10/1/15.
 */
public interface IQuickCodeService {

    /**
     * create new quick code for user
     * replace existing quick code
     *
     * @param payload contain access token
     * @return
     */
    CommonResponse setQuickCode(QuickCodePayload payload);

    /**
     * verify quick code
     *
     * @param payload contain access token and quick code
     * @return
     */
    CommonResponse verifyQuickCode(QuickCodePayload payload);

    /**
     * forget quick code
     *
     * @param payload contain access token and quick code
     * @return
     */
    CommonResponse forgetQuickCode(QuickCodePayload payload);

    /**
     * check quick code existence
     *
     * @param payload
     * @return
     */
    CommonResponse checkExistence(QuickCodePayload payload);

}
