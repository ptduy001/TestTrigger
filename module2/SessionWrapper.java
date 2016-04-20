package com.timo.dmz.user.auth.service;

import com.google.common.collect.Maps;
import com.timo.dmz.common.DesiredConditions;
import com.timo.dmz.common.Times;
import com.timo.dmz.db.jooq.core.exception.DBException;
import com.timo.dmz.user.auth.exception.AuthExceptions;
import com.timo.dmz.user.auth.repository.IAccessTokenRepo;
import com.timo.dmz.user.auth.repository.model.tables.records.TblaccesstokenRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by ptduy on 3/18/16.
 */
public class SessionWrapper{
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionWrapper.class);
    private static final Map<String, Integer> ecommerceSessionMap = Maps.newHashMap();
    private String ecommerceSessionId;
    private TblaccesstokenRecord token;
    private IAccessTokenRepo tokenRepo;
    private Integer otpFailedAttempts;
    private SessionType sessionType;
    private ISessionAction actionHandler;

    private SessionWrapper(){} //who dare to initialize me??

    public static SessionWrapper wrapToken(TblaccesstokenRecord token, IAccessTokenRepo tokenRepo){
        DesiredConditions.checkNotNull(token, () -> new IllegalStateException("invalid token"));
        DesiredConditions.checkNotNull(tokenRepo, () -> new IllegalStateException("invalid tokenRepo"));

        return new SessionWrapper().setToken(token)
                .setTokenRepo(tokenRepo)
                .setSessionType(SessionType.USER_ACCESS)
                .setOtpFailedAttempts(token.getOtpfailedattempts())
                .setActionHandler(new ISessionAction() {
                    @Override
                    public void increaseFailedAttempts() {
                        token.setOtpfailedattempts(token.getOtpfailedattempts() + 1);
                        token.setModifiedat(Times.currentTimestamp());

                        try {
                            tokenRepo.update(token);
                        } catch (DBException.SQLError e) {
                            LOGGER.error(e.getMessage(), e);
                            throw new AuthExceptions.RepositoryError(e.getMessage(), e);
                        }
                    }

                    @Override
                    public void resetFailedAttempts() {
                        token.setOtpfailedattempts(0);
                        token.setModifiedat(Times.currentTimestamp());

                        try {
                            tokenRepo.update(token);
                        }catch (DBException.SQLError e){
                            LOGGER.error(e.getMessage(), e);
                            throw new AuthExceptions.RepositoryError(e.getMessage(), e);
                        }
                    }
                });
    }

    public static SessionWrapper wrapEcommerce(String ecommerceSessionId){ //TODO improve ecommerce
        DesiredConditions.checkNotEmpty(ecommerceSessionId, () -> new IllegalStateException("invalid ecommerceSessionId"));

        ecommerceSessionMap.putIfAbsent(ecommerceSessionId, 0);
        return new SessionWrapper().setEcommerceSessionId(ecommerceSessionId)
                .setSessionType(SessionType.ECOMMERCE)
                .setOtpFailedAttempts(ecommerceSessionMap.get(ecommerceSessionId))
                .setActionHandler(new ISessionAction() {
                    @Override
                    public void increaseFailedAttempts() {
                        ecommerceSessionMap.computeIfPresent(ecommerceSessionId, (s, v) -> ecommerceSessionMap.put(s, v + 1));
                    }

                    @Override
                    public void resetFailedAttempts() {
                        ecommerceSessionMap.computeIfPresent(ecommerceSessionId, (s, v) -> ecommerceSessionMap.put(s, 0));
                    }
                });
    }

    private SessionWrapper setActionHandler(ISessionAction actionHandler) {
        this.actionHandler = actionHandler;
        return this;
    }

    private SessionWrapper setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
        return this;
    }

    private SessionWrapper setToken(TblaccesstokenRecord token) {
        this.token = token;
        return this;
    }

    private SessionWrapper setTokenRepo(IAccessTokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
        return this;
    }

    private SessionWrapper setOtpFailedAttempts(Integer otpFailedAttempts) {
        this.otpFailedAttempts = otpFailedAttempts;
        return this;
    }

    private SessionWrapper setEcommerceSessionId(String ecommerceSessionId) {
        this.ecommerceSessionId = ecommerceSessionId;
        return this;
    }

    public Integer getOtpfailedattempts() {
        return otpFailedAttempts;
    }

    public SessionWrapper increaseOtpFailedAttempts(){
        actionHandler.increaseFailedAttempts();
        return this;
    }

    public SessionWrapper resetFailedAttempts(){
        actionHandler.resetFailedAttempts();
        return this;
    }

    private enum SessionType{
        USER_ACCESS,
        ECOMMERCE
    }

    private interface ISessionAction {
        void increaseFailedAttempts();
        void resetFailedAttempts();
    }

}
