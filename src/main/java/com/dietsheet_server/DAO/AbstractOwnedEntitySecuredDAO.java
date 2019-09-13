package com.dietsheet_server.DAO;

import com.dietsheet_server.model.OwnedEntity;
import com.dietsheet_server.model.user.User;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public abstract class AbstractOwnedEntitySecuredDAO<T extends OwnedEntity> {
    protected Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    protected void setClazz( Class<T> clazzToSet ){
        this.clazz = clazzToSet;
    }

    @PostAuthorize(
            "returnObject.owner == null ||" +
            "returnObject.owner.username == principal.username")
    public T get( long id ){
        T entity = entityManager.find(clazz, id);
        if(entity == null) {
            throw new ResourceNotFoundException();
        }
        initializeEntityChildren(entity);
        return entity;
    }

    public List<T> getAll(){
        return entityManager.createQuery("from " + clazz.getName(), clazz).getResultList();
    }

    public List<T> getAllByUser(User user, QueryParams params) {
        String hql =
                "from " +
                clazz.getName() +
                " c where c.owner = :owner or c.owner = null" +
                " and c.name like concat(:nameLike, '%')";

        return entityManager
                .createQuery(hql, clazz)
                .setParameter("owner", user)
                .setFirstResult(params.getFirstResult())
                .setMaxResults(params.getMaxResults())
                .setParameter("nameLike", params.getNameLike())
                .getResultList();
    }

    @PostFilter("filterObject.owner.username == principal.username")
    public List<T> getByIds(List<Long> ids) {
        String hql = "from " + clazz.getName() + " c where c.id IN(:ids)";

        return entityManager
                .createQuery(hql, clazz)
                .setParameter("ids", ids)
                .getResultList();
    }


    public void save(T entity) {
        entityManager.persist(entity);
    }

    @PreAuthorize("#entity.owner != null && #entity.owner.username == principal.username")
    public void update(T entity){
        entityManager.merge(entity);
    }

    @PreAuthorize("#entity.owner != null && #entity.owner.username == principal.username")
    public void delete(T entity){
        entityManager.remove(entityManager.merge(entity));
    }

    public void delete(long entityId) {
        T entity = get( entityId );
        if(entity.getOwner() != null) {
            delete( entity );
        }
    }

    public void deleteAll() {
        entityManager.createQuery("delete from " + clazz.getName()).executeUpdate();
    }

    public abstract void initializeEntityChildren(T entity);

}
