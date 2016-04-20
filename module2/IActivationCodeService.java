package com.timo.dmz.user.auth.service;

import com.timo.dmz.common.dto.CommonResponse;
import com.timo.dmz.user.auth.common.dto.ActivationCodePayload;

/**
 * Created by ptduy on 10/1/15.
 */
public interface IActivationCodeService {

    CommonResponse createActivationCode(ActivationCodePayload payload);

    CommonResponse activate(ActivationCodePayload payload);
}
