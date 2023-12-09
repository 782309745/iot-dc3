/*
 * Copyright 2016-present the IoT DC3 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.pnoker.center.manager.service;

import io.github.pnoker.center.manager.entity.query.ProfileBindBOPageQuery;
import io.github.pnoker.common.base.Service;
import io.github.pnoker.center.manager.entity.bo.ProfileBindBO;

import java.util.Set;

/**
 * ProfileBind Interface
 *
 * @author pnoker
 * @since 2022.1.0
 */
public interface ProfileBindService extends Service<ProfileBindBO, ProfileBindBOPageQuery> {

    /**
     * 根据 设备ID 删除关联的模版映射
     *
     * @param deviceId 设备ID
     * @return 是否删除
     */
    Boolean deleteByDeviceId(Long deviceId);

    /**
     * 根据 设备ID 和 模版ID 删除关联的模版映射
     *
     * @param deviceId  设备ID
     * @param profileId Profile ID
     * @return 是否删除
     */
    Boolean deleteByDeviceIdAndProfileId(Long deviceId, Long profileId);

    /**
     * 根据 设备ID 和 模版ID 查询关联的模版映射
     *
     * @param deviceId  设备ID
     * @param profileId Profile ID
     * @return ProfileBind
     */
    ProfileBindBO selectByDeviceIdAndProfileId(Long deviceId, Long profileId);

    /**
     * 根据 模版ID 查询关联的 设备ID 集合
     *
     * @param profileId Profile ID
     * @return 设备ID Set
     */
    Set<Long> selectDeviceIdsByProfileId(Long profileId);

    /**
     * 根据 设备ID 查询关联的 模版ID 集合
     *
     * @param deviceId 设备ID
     * @return Profile ID Set
     */
    Set<Long> selectProfileIdsByDeviceId(Long deviceId);

}
