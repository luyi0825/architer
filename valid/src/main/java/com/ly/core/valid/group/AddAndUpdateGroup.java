/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.ly.core.valid.group;


import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *
 * @author luyi
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface AddAndUpdateGroup {

}
