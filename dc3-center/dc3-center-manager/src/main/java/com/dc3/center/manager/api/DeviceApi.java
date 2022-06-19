/*
 * Copyright (c) 2022. Pnoker. All Rights Reserved.
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

package com.dc3.center.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.feign.DeviceClient;
import com.dc3.center.manager.service.DeviceService;
import com.dc3.center.manager.service.NotifyService;
import com.dc3.common.bean.R;
import com.dc3.common.constant.CommonConstant;
import com.dc3.common.constant.ServiceConstant;
import com.dc3.common.dto.DeviceDto;
import com.dc3.common.model.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备 Client 接口实现
 *
 * @author pnoker
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.Manager.DEVICE_URL_PREFIX)
public class DeviceApi implements DeviceClient {

    @Resource
    private DeviceService deviceService;
    @Resource
    private NotifyService notifyService;

    @Override
    public R<Device> add(Device device, String tenantId) {
        try {
            Device add = deviceService.add(device.setTenantId(tenantId));
            if (null != add) {
                notifyService.notifyDriverDevice(CommonConstant.Driver.Device.ADD, add);
                return R.ok(add);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Boolean> delete(String id) {
        try {
            Device device = deviceService.selectById(id);
            if (null != device && deviceService.delete(id)) {
                notifyService.notifyDriverDevice(CommonConstant.Driver.Device.DELETE, device);
                return R.ok();
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Device> update(Device device, String tenantId) {
        try {
            Device update = deviceService.update(device.setTenantId(tenantId));
            if (null != update) {
                notifyService.notifyDriverDevice(CommonConstant.Driver.Device.UPDATE, update);
                return R.ok(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Device> selectById(String id) {
        try {
            Device select = deviceService.selectById(id);
            if (null != select) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<List<Device>> selectByDriverId(String driverId) {
        try {
            List<Device> select = deviceService.selectByDriverId(driverId);
            if (null != select) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<List<Device>> selectByProfileId(String profileId) {
        try {
            List<Device> select = deviceService.selectByProfileId(profileId);
            if (null != select) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Page<Device>> list(DeviceDto deviceDto, String tenantId) {
        try {
            deviceDto.setTenantId(tenantId);
            Page<Device> page = deviceService.list(deviceDto);
            if (null != page) {
                return R.ok(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
