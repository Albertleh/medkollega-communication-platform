package at.ac.tuwien.isg.backend.repository;

import at.ac.tuwien.isg.backend.entity.BaseEntity;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractBaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    protected JPAQuery<?> query() {
        return new JPAQuery<>(entityManager);
    }

    protected <T> JPAQuery<T> query(EntityPath<T> entity) {
        return query().select(entity).from(entity);
    }

    protected <T> Optional<T> getResult(JPAQuery<T> query) {
        return Optional.ofNullable(query.fetchOne());
    }

    protected <T> List<T> getResults(JPAQuery<T> query) {
        return query.fetch();
    }

    protected <T> JPAUpdateClause updateClause(EntityPath<T> path) {
        return new JPAUpdateClause(entityManager, path);
    }


    @Transactional
    public <T extends BaseEntity> void save(T entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void flush() {
        entityManager.flush();
    }

    public void delete(BaseEntity entity) {
        entityManager.remove(entity);
    }

}
