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

package io.github.pnoker.center.manager.service;

import io.github.pnoker.common.dto.DeviceDto;
import io.github.pnoker.common.dto.DriverDto;

import java.util.Map;

/**
 * Device Interface
 *
 * @author pnoker
 * @since 2022.1.0
 */
public interface StatusService {

    /**
     * 根据 驱动ServiceName 查询 Driver 服务状态
     *
     * @param serviceName Driver ServiceName
     * @return String
     */
    String driver(String serviceName);

    /**
     * 模糊分页查询 Driver 服务状态，同驱动模糊分页查询配套使用
     *
     * @param driverDto 驱动和分页参数
     * @return Map String:String
     */
    Map<String, String> driver(DriverDto driverDto);

    /**
     * 根据 设备Id 查询 Device 服务状态
     *
     * @param id 设备ID
     * @return String
     */
    String device(String id);

    /**
     * 模糊分页查询 Device 服务状态，同设备分页查询配套使用
     *
     * @param deviceDto 设备和分页参数
     * @return Map String:String
     */
    Map<String, String> device(DeviceDto deviceDto);

    /**
     * 根据 模板ID 查询 Device 服务状态
     *
     * @param profileId Profile ID
     * @return Map String:String
     */
    Map<String, String> deviceByProfileId(String profileId);
}