package com.ggxspace.autumn.repository.system.impl;

import com.ggxspace.autumn.entity.system.Menu;
import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.enums.MenuTypeEnum;
import com.ggxspace.autumn.repository.AbstractRepositoryImpl;
import com.ggxspace.autumn.repository.system.MenuRepositoryCustom;
import com.ggxspace.autumn.repository.system.UserRepositoryCustom;
import com.ggxspace.autumn.util.ObjectUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 用户仓库实现
 * Created by ganguixiang on 2017/9/27.
 */
@Repository
public class MenuRepositoryImpl extends AbstractRepositoryImpl<Menu> implements MenuRepositoryCustom {

//    public List<Menu> findByRoleIdsAndType(List<String> roleIds, MenuTypeEnum type) {
//        ObjectUtils.requireNonNull(roleIds);
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
//        Root<Menu> root = cq.from(Menu.class);
//
//        em.createQuery("select m from Menu m left join m.roles r where r.id in ")
//
//        CriteriaBuilder.In<Object> in = cb.in(root.get("roles.id"));
//
//        in.value(roleIds);
//        cq.where(in);
//
//        if (ObjectUtils.nonNull(type)) {
//            Predicate typePredicate = cb.equal(root.get("type").as(MenuTypeEnum.class), type);
//            cq.where(typePredicate);
//        }
//
//        return em.createQuery(cq).getResultList();
//    }

}
