package com.yongxin.dao.impl;

import com.yongxin.dao.UserRelationDao;
import com.yongxin.entity.UserRelation;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-12-28.
 */
@Repository
public class UserRelationDaoImpl implements UserRelationDao{
    @Resource
    SessionFactory sessionFactory;
    public Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public UserRelation getUserRelationById(int id) {
        Session session = this.getSession();
        UserRelation userRelation = new UserRelation();
        try {
            userRelation = (UserRelation) session.createQuery("from UserRelation where id=?").setParameter(0, id).uniqueResult();
        } finally {
            session.close();
        }
        return userRelation;
    }

    /**
     * 通过上级id获取下一级用户列表
     * @param fatherId
     * @return
     */
    @Override
    public List<UserRelation> getUserRelationByFatherId(int fatherId) {
        Session session = this.getSession();
        List<UserRelation> userRelationList = new ArrayList<>();
        try{
            List<Object> param= new ArrayList<Object>();
            param.add(fatherId);
            org.hibernate.Query q = session.createQuery("from UserRelation where userFId=?");
            if (param != null && param.size() > 0) {
                for (int j = 0; j < param.size(); j++) {
                    q.setParameter(j, param.get(j));
                }
            }
            userRelationList= q.list();
        }finally {
            session.close();
        }
        return userRelationList;
    }

    /**
     * 通过下级id获取上一级用户
     * @param sonId
     * @return
     */
    @Override
    public UserRelation getUserRelationBySonId(int sonId) {
        Session session = this.getSession();
        UserRelation userRelation = new UserRelation();
        try {
            userRelation = (UserRelation) session.createQuery("from UserRelation where userSId=?").setParameter(0, sonId).uniqueResult();
        } finally {
            session.close();
        }
        return userRelation;
    }

    @Override
    public Integer deleteUserRelationBySonId(int id) {
        Session session = this.getSession();
        int result = 0;
        try {
            List<Object> objects= new ArrayList<Object>();
            objects.add(id);
            org.hibernate.Query query = session.createQuery("delete from UserRelation where userSId=?")
                    .setParameter(0, objects);
            if (objects!=null&&objects.size()>0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Integer deleteUserRelationByFatherId(int id) {
        Session session = this.getSession();
        int result = 0;
        try {
            List<Object> objects= new ArrayList<Object>();
            objects.add(id);
            org.hibernate.Query query = session.createQuery("delete from UserRelation where userFId=?")
                    .setParameter(0, objects);
            if (objects!=null&&objects.size()>0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Integer deleteUserRelationById(int id) {
        Session session = this.getSession();
        int result = 0;
        try {
            List<Object> objects= new ArrayList<Object>();
            objects.add(id);
            org.hibernate.Query query = session.createQuery("delete from UserRelation where id=?")
                    .setParameter(0, objects);
            if (objects!=null&&objects.size()>0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Integer save(UserRelation userRelation) {
        Session session = this.getSession();
        int result = 0;
        try {
            //添加
            session.saveOrUpdate(userRelation);
            //查询是否添加成功
            List<Object> objects= new ArrayList<Object>();
            objects.add(userRelation.getUserF().getId());
            objects.add(userRelation.getUserS().getId());
            org.hibernate.Query query = session.createQuery("from UserRelation where userFId=? and userSId=?")
                    .setParameter(0, objects);
            if (objects!=null&&objects.size()>0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            List<UserRelation> userRelationList = new ArrayList<>();
            userRelationList = query.list();
            if (userRelationList != null && userRelationList.size() > 0) {
                result = 1;
            }
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<UserRelation> getUserRelationList() {
        Session session = this.getSession();
        List<UserRelation> userRelationList = new ArrayList<>();
        try {
            userRelationList = session.createCriteria(UserRelation.class).list();
        } finally {
            session.close();
        }
        return userRelationList;
    }

    /**
     * 获取页数
     * @return
     */
    @Override
    public int getPageNum() {
        Session session = this.getSession();
        int pages = 0;
        try {
            Criteria query = session.createCriteria(UserRelation.class);
            ScrollableResults scroll = query.scroll();
            // 滚动到最后一行
            scroll.last();
            // 一共多少条数据
            int n = scroll.getRowNumber() + 1;
            System.out.println(n);
            int num = 10;
            if (n % num != 0) {
                pages = n / num + 1;
            }else {
                pages = n / num;
            }
        } finally {
            session.close();
        }
        return pages;
    }

    /**
     * 通过页码获取分页查询
     * @param num
     * @return
     */
    @Override
    public List<UserRelation> getUserRelationList(int num) {
        Session session = this.getSession();
        List<UserRelation> userRelationList = new ArrayList<>();
        try{
            Criteria query = session.createCriteria(UserRelation.class);
            ScrollableResults scroll = query.scroll();
            // 滚动到最后一行
            scroll.last();
            // 一共多少条数据
            int n = scroll.getRowNumber() + 1;
            System.out.println(n);
            // 设定一页显示多少条
            int nu = 10;
            int begin = (num - 1) * nu;
            //int last = n - nu * num;
            // 设置分页位置
            query.setFirstResult(begin);
            //结束位置
            query.setMaxResults(nu);
            userRelationList = query.list();
            System.out.println(userRelationList);
        }finally {
            session.close();
        }
        return userRelationList;
    }

    @Override
    public Integer updateUserRelation(int id,int userFId,int userSid) {
        Session session = this.getSession();
        int result = 0;
        try {
            List<Object> objects= new ArrayList<Object>();
            objects.add(userFId);
            objects.add(userSid);
            objects.add(id);
            org.hibernate.Query query = session.createQuery("update UserRelation set userFId = ?,userSId=? where id=?")
                    .setParameter(0, objects);
            if (objects!=null&&objects.size()>0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        }finally {
            session.close();
        }
        return result;
    }


    /**
     * 分页查询
     * @param fatherId
     * @param num
     * @return
     */
    @Override
    public List<UserRelation> getUserRelationListByFatherId(int fatherId,int num) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        List<UserRelation> userRelationList = new ArrayList<>();
        try {
			/*
			 * 分页查询步骤
			 */
            List<Object> param= new ArrayList<Object>();
            param.add(fatherId);
            org.hibernate.Query q = session.createQuery("from UserRelation where userFId = ?");
            if (param != null && param.size() > 0) {
                for (int j = 0; j < param.size(); j++) {
                    q.setParameter(j, param.get(j));
                }
            }
            int nu = 10;
            int begin = (num - 1) * nu;
            userRelationList= q.setFirstResult(begin).setMaxResults(nu).list();
        }finally {
            session.close();
        }
        return userRelationList;
    }

    /**
     * 查找到有关系的所有下级用户
     * @param fatherId
     * @return
     */
    @Override
    public List<UserRelation> getUserRelationListByFatherId(int fatherId) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        List<UserRelation> userRelationList = new ArrayList<>();
        try {
			/*
			 * 分页查询步骤
			 */
            List<Object> param= new ArrayList<Object>();
            param.add(fatherId);
            org.hibernate.Query q = session.createQuery("from UserRelation where userFId = ?");
            if (param != null && param.size() > 0) {
                for (int j = 0; j < param.size(); j++) {
                    q.setParameter(j, param.get(j));
                }
            }
            userRelationList= q.list();
        }finally {
            session.close();
        }
        return userRelationList;
    }
    @Override
    public int getPagesByfatherId(int fatherId) {
        Session session = this.getSession();
        int pages = 0;
        try {
			/*
			 * 分页查询步骤
			 */
            List<Object> param= new ArrayList<Object>();
            param.add(fatherId);
            org.hibernate.Query q = session.createQuery("from UserRelation where userFId = ?");
            if (param != null && param.size() > 0) {
                for (int j = 0; j < param.size(); j++) {
                    q.setParameter(j, param.get(j));
                }
            }
            int num = 10;
            int n = q.list().size();
            if (n % num != 0) {
                pages = n / num + 1;
            }else {
                pages = n / num;
            }
        }finally {
            session.close();
        }
        return pages;
    }

    @Override
    public int updateFatherIdByid(int id, int fatherId) {
        Session session = this.getSession();
        int result = 0;
        try {
            List<Object> objects = new ArrayList<Object>();
            objects.add(fatherId);
            objects.add(id);
            org.hibernate.Query query = session.createQuery("update UserRelation set userFId=? where id=?")
                    .setParameter(0, objects);
            if (objects != null && objects.size() > 0) {
                for (int i = 0; i < objects.size(); i++) {
                    query.setParameter(i, objects.get(i));
                }
            }
            result = query.executeUpdate();
        }finally {
            session.close();
        }

        return result;
    }
}
