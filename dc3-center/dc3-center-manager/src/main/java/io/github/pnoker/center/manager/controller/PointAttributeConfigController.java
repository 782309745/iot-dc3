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

package io.github.pnoker.center.manager.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.pnoker.center.manager.entity.query.PointAttributeConfigQuery;
import io.github.pnoker.center.manager.service.PointAttributeConfigService;
import io.github.pnoker.common.base.Controller;
import io.github.pnoker.common.constant.service.ManagerServiceConstant;
import io.github.pnoker.common.entity.R;
import io.github.pnoker.center.manager.entity.bo.PointAttributeConfigBO;
import io.github.pnoker.common.valid.Add;
import io.github.pnoker.common.valid.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 位号属性配置信息 Controller
 *
 * @author pnoker
 * @since 2022.1.0
 */
@Slf4j
@RestController
@RequestMapping(ManagerServiceConstant.POINT_ATTRIBUTE_CONFIG_URL_PREFIX)
public class PointAttributeConfigController implements Controller {

    @Resource
    private PointAttributeConfigService pointAttributeConfigService;

    /**
     * 新增 PointInfo
     *
     * @param pointAttributeConfigBO PointInfo
     * @return PointInfo
     */
    @PostMapping("/add")
    public R<String> add(@Validated(Add.class) @RequestBody PointAttributeConfigBO pointAttributeConfigBO) {
        try {
            pointAttributeConfigService.save(pointAttributeConfigBO);
            return R.ok();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 根据 ID 删除 PointInfo
     *
     * @param id 位号信息ID
     * @return 是否删除
     */
    @PostMapping("/delete/{id}")
    public R<String> delete(@NotNull @PathVariable(value = "id") String id) {
        try {
            pointAttributeConfigService.remove(Long.parseLong(id));
            return R.ok();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 更新 PointInfo
     *
     * @param pointAttributeConfigBO PointInfo
     * @return PointInfo
     */
    @PostMapping("/update")
    public R<String> update(@Validated(Update.class) @RequestBody PointAttributeConfigBO pointAttributeConfigBO) {
        try {
            pointAttributeConfigService.update(pointAttributeConfigBO);
            return R.ok();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 根据 ID 查询 PointInfo
     *
     * @param id 位号信息ID
     * @return PointInfo
     */
    @GetMapping("/id/{id}")
    public R<PointAttributeConfigBO> selectById(@NotNull @PathVariable(value = "id") String id) {
        try {
            PointAttributeConfigBO select = pointAttributeConfigService.selectById(Long.parseLong(id));
            if (ObjectUtil.isNotNull(select)) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    /**
     * 根据 属性ID、设备ID 和 位号ID 查询 PointInfo
     *
     * @param attributeId Attribute ID
     * @param deviceId    设备ID
     * @param pointId     位号ID
     * @return PointInfo
     */
    @GetMapping("/attribute_id/{attributeId}/device_id/{deviceId}/point_id/{pointId}")
    public R<PointAttributeConfigBO> selectByAttributeIdAndDeviceIdAndPointId(@NotNull @PathVariable(value = "attributeId") String attributeId,
                                                                              @NotNull @PathVariable(value = "deviceId") String deviceId,
                                                                              @NotNull @PathVariable(value = "pointId") String pointId) {
        try {
            PointAttributeConfigBO select = pointAttributeConfigService.selectByAttributeIdAndDeviceIdAndPointId(Long.parseLong(attributeId), Long.parseLong(deviceId), Long.parseLong(pointId));
            if (ObjectUtil.isNotNull(select)) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    /**
     * 根据 设备ID 和 位号ID 查询 PointInfo
     *
     * @param deviceId 设备ID
     * @param pointId  位号ID
     * @return PointInfo
     */
    @GetMapping("/device_id/{deviceId}/point_id/{pointId}")
    public R<List<PointAttributeConfigBO>> selectByDeviceIdAndPointId(@NotNull @PathVariable(value = "deviceId") Long deviceId,
                                                                      @NotNull @PathVariable(value = "pointId") Long pointId) {
        try {
            List<PointAttributeConfigBO> select = pointAttributeConfigService.selectByDeviceIdAndPointId(deviceId, pointId);
            if (ObjectUtil.isNotNull(select)) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    /**
     * 根据 设备ID 查询 PointInfo
     *
     * @param deviceId 设备ID
     * @return PointInfo
     */
    @GetMapping("/device_id/{deviceId}")
    public R<List<PointAttributeConfigBO>> selectByDeviceId(@NotNull @PathVariable(value = "deviceId") Long deviceId) {
        try {
            List<PointAttributeConfigBO> select = pointAttributeConfigService.selectByDeviceId(deviceId);
            if (ObjectUtil.isNotNull(select)) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    /**
     * 分页查询 PointInfo
     *
     * @param pointInfoPageQuery PointInfo Dto
     * @return Page Of PointInfo
     */
    @PostMapping("/list")
    public R<Page<PointAttributeConfigBO>> list(@RequestBody(required = false) PointAttributeConfigQuery pointInfoPageQuery) {
        try {
            if (ObjectUtil.isEmpty(pointInfoPageQuery)) {
                pointInfoPageQuery = new PointAttributeConfigQuery();
            }
            Page<PointAttributeConfigBO> page = pointAttributeConfigService.selectByPage(pointInfoPageQuery);
            if (ObjectUtil.isNotNull(page)) {
                return R.ok(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
