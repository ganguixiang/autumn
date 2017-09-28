package com.ggxspace.autumn.repository;

import com.ggxspace.autumn.entity.IdEntity;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.security.auth.login.CredentialException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ganguixiang on 2017/9/27.
 */
//@Repository
public class AbstractRepositoryImpl<T> implements AbstractRepositoryCustom<T> {

    @PersistenceContext
    private EntityManager em;

    /**
     * T.class
     */
    private Class<T> entityClass;

    /**
     * 构造器反射获取T.class
     */
    public AbstractRepositoryImpl() {
        Class cls = getClass();
        ParameterizedType parameterizedType = null;
        // 递归获取cls的参数直至不是Object
        while (cls != Object.class && parameterizedType == null) {
            try {
                parameterizedType = (ParameterizedType) cls.getGenericSuperclass();
            } catch (ClassCastException e) {
                cls = cls.getSuperclass();
            }
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        entityClass = (Class) actualTypeArguments[0];
    }


//    @Override
//    public List<T> findAll() {
//        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
//        Root<T> root = criteriaQuery.from(entityClass);
//        // 只查询delflag为false的数据
//        Predicate delflagPredicate = criteriaBuilder.equal(root.get("delflag").as(Boolean.class), false);
//        criteriaQuery.where(delflagPredicate);
//
//        return em.createQuery(criteriaQuery).getResultList();
//    }


    public T save(T entity) {
        return em.merge(entity);
    }
}
