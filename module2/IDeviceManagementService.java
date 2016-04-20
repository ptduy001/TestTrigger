package com.timo.dmz.user.auth.service;

import com.timo.dmz.db.jooq.core.exception.DBException;
import com.timo.dmz.user.auth.common.Devices;
import com.timo.dmz.user.auth.common.dto.DevicePayload;
import com.timo.dmz.user.auth.exception.AuthExceptions;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ptduy on 10/6/15.
 *
 * device management service
 * handle all client devices status
 *
 */
public interface IDeviceManagementService {

    /**
     * register new device into system
     * if device has already existed, update appVersion, osVersion if necessary
     * update relationship between user and device
     *
     * @param userId
     * @param payload
     * @return timoDeviceid
     * @throws DBException.SQLError
     * @throws AuthExceptions.BadRequest
     */
    String register(Long userId, DevicePayload payload) throws DBException.SQLError, AuthExceptions.BadRequest;

    @Transactional
    Integer getComparableVersion(DevicePayload payload);

    @Transactional
    Devices.Status checkAppVersion(String appVersion, String osPlatform);

    /**
     * check app version
     *
     * @param timoDeviceId
     * @return
     */
    Devices.Status checkAppVersionByTimoDeviceId(String timoDeviceId);
}
