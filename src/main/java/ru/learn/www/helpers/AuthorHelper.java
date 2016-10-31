package ru.learn.www.helpers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.learn.www.entity.Author;
import ru.learn.www.entity.Author_;
import ru.learn.www.utils.HibernateUtil;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
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

    public Author addAuthor(Author author) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(author);
        session.getTransaction().commit();
        session.close();

        return author;
    }


    public Author updateAuthor(long id, String newName) {
        Session session = sessionFactory.openSession();

        Author author = session.get(Author.class, id);
        author.setName(newName);
        session.beginTransaction();
        session.save(author);
        session.getTransaction().commit();
        session.close();

        return author;
    }


    public void addAuthors(String name, String lastName) {
        Session session = sessionFactory.openSession();

        Author author;
        session.beginTransaction();
        for (int i = 0; i < 200; i++) {
            author = new Author(name + i);
            author.setSurName(lastName);
            session.save(author);
            if (i % 20 == 0) {
                session.flush();
            }
        }
        session.getTransaction().commit();
        session.close();
    }


    public List<Author> getColumnsAuthors(SingularAttribute... singularAttributes) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);

        Root<Author> root = criteriaQuery.from(Author.class);
        if (singularAttributes.length != 0) {
            Selection[] selection = new Selection[singularAttributes.length];
            for (int i = 0; i < singularAttributes.length; i++) {
                selection[i] = root.get(singularAttributes[i]);
            }
            criteriaQuery.select(criteriaBuilder.construct(Author.class, selection));
        }

        Query query = session.createQuery(criteriaQuery);

        List<Author> resultList = query.getResultList();

        session.close();

        return resultList;
    }


    public List<Author> getAuthorsWhere() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        ParameterExpression<String> nameParameter = criteriaBuilder.parameter(String.class, "name");
        Root<Author> root = criteriaQuery.from(Author.class);

        criteriaQuery.select(root).where(criteriaBuilder.like(root.get(Author_.name), nameParameter));

        Query query = session.createQuery(criteriaQuery);
        query.setParameter("name", "%1%");

        List<Author> resultList = query.getResultList();

        session.close();

        return resultList;
    }


    public void updateAuthours() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();


        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<Author> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Author.class);
        Root<Author> root = criteriaUpdate.from(Author.class);
        ParameterExpression<String> surname = criteriaBuilder.parameter(String.class, "surname");


        Expression<Integer> lengthSurName = criteriaBuilder.length(root.get(Author_.surName));
        criteriaUpdate.set(root.get(Author_.name), surname)
                .where(criteriaBuilder.greaterThan(lengthSurName, 4));


        Query query = session.createQuery(criteriaUpdate);
        query.setParameter("surname", "1");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }
}
