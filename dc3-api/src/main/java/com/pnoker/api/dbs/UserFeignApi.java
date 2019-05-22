/*
 * Copyright 2018 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pnoker.api.dbs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.pnoker.api.dbs.hystrix.UserFeignApiHystrix;
import com.pnoker.common.model.rtmp.Rtmp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>Copyright(c) 2018. Pnoker All Rights Reserved.
 * <p>Author     : Pnoker
 * <p>Email      : pnokers@gmail.com
 * <p>Description:
 */
@FeignClient(name = "DC3-DBS", fallbackFactory = UserFeignApiHystrix.class)
public interface UserFeignApi extends BaseMapper<Rtmp> {

    @RequestMapping(value = "/api/user/getById/{userId}", method = RequestMethod.GET)
    String getById(@PathVariable("userId") Long userId);
}
