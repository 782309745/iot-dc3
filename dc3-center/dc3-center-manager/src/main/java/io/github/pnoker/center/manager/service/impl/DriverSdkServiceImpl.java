/*
 * Copyright 2016-present Pnoker All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.pnoker.center.manager.service.impl;

import io.github.pnoker.api.center.auth.feign.TenantClient;
import io.github.pnoker.center.manager.service.*;
import io.github.pnoker.common.bean.R;
import io.github.pnoker.common.bean.driver.DriverRegister;
import io.github.pnoker.common.exception.NotFoundException;
import io.github.pnoker.common.exception.ServiceException;
import io.github.pnoker.common.model.Driver;
import io.github.pnoker.common.model.DriverAttribute;
import io.github.pnoker.common.model.PointAttribute;
import io.github.pnoker.common.model.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DriverService Impl
 *
 * @author pnoker
 * @since 2022.1.0
 */
@Slf4j
@Service
public class DriverSdkServiceImpl implements DriverSdkService {

    @Resource
    private TenantClient tenantClient;

    @Resource
    private DriverService driverService;
    @Resource
    private DriverAttributeService driverAttributeService;
    @Resource
    private DriverInfoService driverInfoService;
    @Resource
    private PointAttributeService pointAttributeService;
    @Resource
    private PointInfoService pointInfoService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void driverRegister(DriverRegister driverRegister) {
        // register driver
        Driver driver = registerDriver(driverRegister);

        //register driver attribute
        registerDriverAttribute(driverRegister, driver);

        // register point attribute
        registerPointAttribute(driverRegister, driver);
    }

    /**
     * 注册驱动
     *
     * @param driverRegister DriverRegister
     */
    private Driver registerDriver(DriverRegister driverRegister) {
        // check tenant
        R<Tenant> tenantR = tenantClient.selectByName(driverRegister.getTenant());
        if (!tenantR.isOk()) {
            throw new ServiceException("Invalid {}, {}", driverRegister.getTenant(), tenantR.getMessage());
        }

        // register driver
        Driver driver = driverRegister.getDriver().setTenantId(tenantR.getData().getId());
        log.info("Register driver {}", driver);
        try {
            Driver byServiceName = driverService.selectByServiceName(driver.getServiceName());
            log.debug("Driver already registered, updating {} ", driver);
            driver.setId(byServiceName.getId());
            driver = driverService.update(driver);
        } catch (NotFoundException notFoundException1) {
            log.debug("Driver does not registered, adding {} ", driver);
            try {
                Driver byHostPort = driverService.selectByHostPort(driver.getType(), driver.getHost(), driver.getPort(), driver.getTenantId());
                throw new ServiceException("The port(" + driver.getPort() + ") is already occupied by driver(" + byHostPort.getServiceName() + "/" + byHostPort.getName() + ")");
            } catch (NotFoundException notFoundException2) {
                driver = driverService.add(driver);
            }
        }
        return driver;
    }

    /**
     * 注册驱动属性
     *
     * @param driverRegister DriverRegister
     * @param driver         Driver
     */
    private void registerDriverAttribute(DriverRegister driverRegister, Driver driver) {
        Map<String, DriverAttribute> newDriverAttributeMap = new HashMap<>(8);
        if (null != driverRegister.getDriverAttributes() && !driverRegister.getDriverAttributes().isEmpty()) {
            driverRegister.getDriverAttributes().forEach(driverAttribute -> newDriverAttributeMap.put(driverAttribute.getName(), driverAttribute));
        }

        Map<String, DriverAttribute> oldDriverAttributeMap = new HashMap<>(8);
        try {
            List<DriverAttribute> byDriverId = driverAttributeService.selectByDriverId(driver.getId());
            byDriverId.forEach(driverAttribute -> oldDriverAttributeMap.put(driverAttribute.getName(), driverAttribute));
        } catch (NotFoundException ignored) {
            // nothing to do
        }

        for (Map.Entry<String, DriverAttribute> entry : newDriverAttributeMap.entrySet()) {
            String name = entry.getKey();
            DriverAttribute info = newDriverAttributeMap.get(name).setDriverId(driver.getId());
            if (oldDriverAttributeMap.containsKey(name)) {
                info.setId(oldDriverAttributeMap.get(name).getId());
                log.debug("Driver attribute registered, updating: {}", info);
                driverAttributeService.update(info);
            } else {
                log.debug("Driver attribute does not registered, adding: {}", info);
                driverAttributeService.add(info);
            }
        }

        for (Map.Entry<String, DriverAttribute> entry : oldDriverAttributeMap.entrySet()) {
            String name = entry.getKey();
            if (!newDriverAttributeMap.containsKey(name)) {
                try {
                    driverInfoService.selectByAttributeId(oldDriverAttributeMap.get(name).getId());
                    throw new ServiceException("The driver attribute(" + name + ") used by driver info and cannot be deleted");
                } catch (NotFoundException notFoundException) {
                    log.debug("Driver attribute is redundant, deleting: {}", oldDriverAttributeMap.get(name));
                    driverAttributeService.delete(oldDriverAttributeMap.get(name).getId());
                }
            }
        }
    }

    /**
     * 注册位号属性
     *
     * @param driverRegister DriverRegister
     * @param driver         Driver
     */
    private void registerPointAttribute(DriverRegister driverRegister, Driver driver) {
        Map<String, PointAttribute> newPointAttributeMap = new HashMap<>(8);
        if (null != driverRegister.getPointAttributes() && !driverRegister.getPointAttributes().isEmpty()) {
            driverRegister.getPointAttributes().forEach(pointAttribute -> newPointAttributeMap.put(pointAttribute.getName(), pointAttribute));
        }

        Map<String, PointAttribute> oldPointAttributeMap = new HashMap<>(8);
        try {
            List<PointAttribute> byDriverId = pointAttributeService.selectByDriverId(driver.getId());
            byDriverId.forEach(pointAttribute -> oldPointAttributeMap.put(pointAttribute.getName(), pointAttribute));
        } catch (NotFoundException ignored) {
            // nothing to do
        }

        for (Map.Entry<String, PointAttribute> entry : newPointAttributeMap.entrySet()) {
            String name = entry.getKey();
            PointAttribute attribute = newPointAttributeMap.get(name).setDriverId(driver.getId());
            if (oldPointAttributeMap.containsKey(name)) {
                attribute.setId(oldPointAttributeMap.get(name).getId());
                log.debug("Point attribute registered, updating: {}", attribute);
                pointAttributeService.update(attribute);
            } else {
                log.debug("Point attribute registered, adding: {}", attribute);
                pointAttributeService.add(attribute);
            }
        }

        for (Map.Entry<String, PointAttribute> entry : oldPointAttributeMap.entrySet()) {
            String name = entry.getKey();
            if (!newPointAttributeMap.containsKey(name)) {
                try {
                    pointInfoService.selectByAttributeId(oldPointAttributeMap.get(name).getId());
                    throw new ServiceException("The point attribute(" + name + ") used by point info and cannot be deleted");
                } catch (NotFoundException notFoundException1) {
                    log.debug("Point attribute is redundant, deleting: {}", oldPointAttributeMap.get(name));
                    pointAttributeService.delete(oldPointAttributeMap.get(name).getId());
                }
            }
        }
    }

}