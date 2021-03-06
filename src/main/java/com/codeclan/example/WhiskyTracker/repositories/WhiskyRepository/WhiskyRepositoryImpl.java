package com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;


import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.models.Whisky;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class WhiskyRepositoryImpl implements WhiskyRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public List<Whisky> allWhiskiesInYear(int year) {
        List<Whisky> result = null;
        Session session = entityManager.unwrap(Session.class);

        try{
            Criteria cr = session.createCriteria(Whisky.class);
            cr.add(Restrictions.eq("year", year));
            result = cr.list();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }

    // Returning same whisky twice when Highland
//    @Transactional
//    public List<Whisky> allWhiskyInRegion(String region) {
//        List<Whisky> result = null;
//        Session session = entityManager.unwrap(Session.class);
//
//        try{
//            Criteria cr = session.createCriteria(Distillery.class);
//            cr.createAlias("whiskies", "whisky");
//            cr.add(Restrictions.eq("region", region));
//            result = cr.list();
//        } catch (HibernateException ex) {
//            ex.printStackTrace();
//        } finally {
//            session.close();
//        }
//
//        return result;
//    }

    @Transactional
    public List<Whisky> allWhiskyInRegion(String region) {
        List<Whisky> result = null;
        Session session = entityManager.unwrap(Session.class);

        try{
            Criteria cr = session.createCriteria(Whisky.class);
            cr.createAlias("distillery", "distillery");
            cr.add(Restrictions.eq("distillery.region", region).ignoreCase());
            result = cr.list();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }
}
