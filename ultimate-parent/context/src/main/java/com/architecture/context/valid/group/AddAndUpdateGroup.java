
package com.architecture.context.common.valid.group;


import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *
 * @author luyi
 * @TODO 还没有校验是否生效
 */
@GroupSequence(value = {AddGroup.class, UpdateGroup.class})
public interface AddAndUpdateGroup {

}
