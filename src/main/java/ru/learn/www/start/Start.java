package ru.learn.www.start;

import ru.learn.www.utils.HibernateUtil;

public class Start {

    public static void main(String[] args) {
        System.out.println(HibernateUtil.getSessionFactory());
    }
}
