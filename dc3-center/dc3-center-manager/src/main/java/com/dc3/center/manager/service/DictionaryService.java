/*
 * Copyright 2016-2021 Pnoker. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dc3.center.manager.service;

import com.dc3.common.bean.Dictionary;

import java.util.List;

/**
 * Dictionary Interface
 *
 * @author pnoker
 */
public interface DictionaryService {

    /**
     * 获取租户驱动字典
     *
     * @return Dictionary Array
     */
    List<Dictionary> driverDictionary(Long tenantId);

    /**
     * 获取驱动配置属性字典
     *
     * @return Dictionary Array
     */
    List<Dictionary> driverAttributeDictionary(Long tenantId);

    /**
     * 获取位号配置属性字典
     *
     * @return Dictionary Array
     */
    List<Dictionary> pointAttributeDictionary(Long tenantId);

    /**
     * 获取租户模板字典
     *
     * @return Dictionary Array
     */
    List<Dictionary> profileDictionary(Long tenantId);

    /**
     * 获取租户驱动下设备字典
     *
     * @return Dictionary Array
     */
    List<Dictionary> deviceDictionary(Long tenantId);

    /**
     * 获取租户模板、设备位号字典
     * profile/device
     *
     * @param parent
     * @return Dictionary Array
     */
    List<Dictionary> pointDictionary(String parent, Long tenantId);

}
