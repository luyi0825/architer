package io.github.architers.syscenter.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.component.mybatisplus.MybatisPageUtils;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.syscenter.user.StatusEnum;
import io.github.architers.syscenter.user.dao.SysTenantDao;
import io.github.architers.syscenter.user.domain.entity.SysTenant;
import io.github.architers.syscenter.user.service.SysTenantService;
import io.github.architers.context.exception.BusErrorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 租户对应的service实现类
 *
 * @author luyi
 */
@Service
public class SysTenantServiceImpl implements SysTenantService {

    @Resource
    private SysTenantDao sysTenantDao;

    @Override
    public SysTenant findById(Integer tenantId) {
        return sysTenantDao.selectById(tenantId);
    }

    @Override
    public void isValid(Integer tenantId) {
        SysTenant sysTenant = sysTenantDao.selectById(tenantId);
        if (sysTenant == null) {
            throw new BusErrorException("租户不存在");
        }
        if (StatusEnum.DISABLED.getStatus().equals(sysTenant.getStatus())) {
            throw new BusErrorException("租户已经被禁用");
        }
    }

    @Override
    public PageResult<SysTenant> getTenantsByPage(PageRequest<SysTenant> pageRequest) {
        return MybatisPageUtils.pageQuery(pageRequest.getPageParam(), () -> {
            SysTenant sysTenant = pageRequest.getRequestParam();
            Wrapper<SysTenant> sysTenantWrapper = Wrappers.lambdaQuery(SysTenant.class)
                    .like(StringUtils.isNotBlank(sysTenant.getTenantCode()), SysTenant::getTenantCode, sysTenant.getTenantCode())
                    .like(StringUtils.isNotBlank(sysTenant.getTenantCaption()), SysTenant::getTenantCaption, sysTenant.getTenantCaption())
                    .eq(sysTenant.getStatus() != null, SysTenant::getStatus, sysTenant.getStatus());
            return sysTenantDao.selectList(sysTenantWrapper);
        });
    }

    @Override
    public void addTenant(SysTenant sysTenant) {
        //TODO 判断租户是否存在
        Wrapper<SysTenant> sysTenantWrapper = Wrappers.lambdaQuery(SysTenant.class).eq(SysTenant::getTenantCode,
                sysTenant.getTenantCode());
        long count = sysTenantDao.selectCount(sysTenantWrapper);
        if (count > 0) {
            throw new BusErrorException("租户编辑已经存在");
        }
        sysTenant.fillCreateAndUpdateField(new Date());
        sysTenantDao.insert(sysTenant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editTenant(SysTenant sysTenant) {
        //编码不能修改
        sysTenant.setTenantCode(null);
        sysTenant.fillCreateAndUpdateField(new Date());
        int count = sysTenantDao.updateById(sysTenant);
        if (count != 1) {
            throw new BusErrorException("更新租户失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        //TODO 判断租户是否能删除
        int count = sysTenantDao.deleteById(id);
        if (count != 1) {
            throw new BusErrorException("删除租户失败");
        }
    }


}
