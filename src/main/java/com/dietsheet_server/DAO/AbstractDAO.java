package com.dietsheet_server.DAO;

import com.dietsheet_server.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public abstract class AbstractDAO< T > {
    protected Class< T > clazz;

    @PersistenceContext
    protected EntityManager entityManager;


    public final void setClazz( Class< T > clazzToSet ){
        this.clazz = clazzToSet;
    }

    public T get( long id ){
        return entityManager.find(clazz, id);
    }

    public List< T > getAll(){
        return entityManager.createQuery("from " + clazz.getName(), clazz).getResultList();
    }

    public List< T > getAllByUser(User user) {
        String hql = "from " + clazz.getName() + " c where c.owner = :owner";

        return entityManager
                .createQuery(hql, clazz)
                .setParameter("owner", user)
                .getResultList();
    }

    public List< T > getByIds(List<Long> ids) {
        String hql = "from " + clazz.getName() + " c where c.id IN(:ids)";

        return entityManager
                .createQuery(hql, clazz)
                .setParameter("ids", ids)
                .getResultList();
    }

    public void save( T entity ) {
        entityManager.persist(entity);
    }

    public void update( T entity ){
        entityManager.merge(entity);
    }

    public void delete( T entity ){
        entityManager.remove(entity);
    }

    public void delete( long entityId ) {
        T entity = get( entityId );
        delete( entity );
    }

    public void deleteAll() {
        entityManager.createQuery("delete from " + clazz.getName()).executeUpdate();
    }



}
