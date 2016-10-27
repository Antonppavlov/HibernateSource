package ru.learn.www.helpers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.learn.www.entity.Author;
import ru.learn.www.utils.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AuthorHelper {
    private final SessionFactory sessionFactory;

    public AuthorHelper() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Author> getAuthorList() {

        Session session = sessionFactory.openSession();


        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = criteriaQuery.from(Author.class);
        criteriaQuery.select(root);



        Query query = session.createQuery(criteriaQuery);
        List<Author> resultList = query.getResultList();


        session.close();

        return resultList;
    }
}
