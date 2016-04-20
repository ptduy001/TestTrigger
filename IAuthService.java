package com.timo.dmz.user.auth.service;

import com.timo.dmz.db.jooq.core.exception.DBException;
import com.timo.dmz.user.auth.common.dto.AuthPayload;
import com.timo.dmz.common.dto.CommonResponse;
import com.timo.dmz.user.auth.common.dto.TokenPayload;
import org.jose4j.lang.JoseException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ptduy on 8/14/15.
 * Declare methods for IAuthService
 */
public interface IAuthService {

    /**
     * authenticate input info
     * @param authPayload
     * @return
     */
    CommonResponse authenticate(AuthPayload authPayload);

    /**
     * verify login token
     * @param payload
     * @return
     */
    CommonResponse verifyToken(AuthPayload payload);

    /**
     * expire login token
     * @param payload
     * @return
     */
    CommonResponse expireToken(AuthPayload payload);

    /**
     * authorize input info
     * @param authPayload
     * @return
     */
    CommonResponse authorize(AuthPayload authPayload);

    /**
     * create user authentication info
     * @param payload
     * @return
     */
    CommonResponse registerUser(AuthPayload payload);



    /**
     * expire all access tokens
     *
     * @return number of affected rows
     * @throws DBException.SQLError
     */
    int expireAllAccessToken() throws DBException.SQLError;

    /**
     * update existed user
     *
     * @param payload
     * @return
     */
    CommonResponse updateUser(AuthPayload payload);

    CommonResponse upgradePassword(AuthPayload payload);

    CommonResponse updatePassword(AuthPayload payload);

    CommonResponse commitUpdatePassword(AuthPayload payload);

    CommonResponse updateEmail(AuthPayload payload);

    CommonResponse commitUpdateEmail(AuthPayload payload);

    CommonResponse createInvitationToken(AuthPayload payload);

    CommonResponse verifyInvitationToken(AuthPayload payload);

    CommonResponse expireInvitationToken(AuthPayload payload);

    CommonResponse requestExtendToken(AuthPayload payload);

    CommonResponse confirmExtendToken(AuthPayload payload);

    int increaseExtendTokenFailedAttempts(Long userId);
}
