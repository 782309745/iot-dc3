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

package io.github.pnoker.center.auth.entity.query;

import io.github.pnoker.common.constant.enums.EnableFlagEnum;
import io.github.pnoker.common.constant.enums.GroupTypeFlagEnum;
import io.github.pnoker.common.entity.common.Pages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Group Query
 *
 * @author pnoker
 * @since 2022.1.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Schema(title = "GroupQuery", description = "分组-查询")
public class GroupQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "分页")
    private Pages page;

    /**
     * 分组名称
     */
    @Schema(description = "分组名称")
    private String groupName;

    /**
     * 父分组ID
     */
    @Schema(description = "分组父级ID")
    private String parentGroupId;

    /**
     * 分组排序位置
     */
    @Schema(description = "分组排序")
    private Integer position;

    /**
     * 分组标识
     */
    @Schema(description = "分组标识")
    private GroupTypeFlagEnum groupTypeFlag;

    /**
     * 使能标识
     */
    @Schema(description = "使能标识")
    private EnableFlagEnum enableFlag;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;
}