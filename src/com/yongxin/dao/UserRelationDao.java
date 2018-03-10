package com.yongxin.dao;

import com.yongxin.entity.UserRelation;

import java.util.List;

/**
 * Created by wnf on 2017-12-28.
 */
public interface UserRelationDao {
    public UserRelation getUserRelationById(int id);

    public List<UserRelation> getUserRelationByFatherId(int fatherId);

    public UserRelation getUserRelationBySonId(int sonId);

    public Integer deleteUserRelationById(int id);

    public Integer deleteUserRelationBySonId(int id);

    public Integer deleteUserRelationByFatherId(int id);

    public Integer save(UserRelation userRelation);

    public List<UserRelation> getUserRelationList();

    public Integer updateUserRelation(int id,int userFId,int userSid);

    public int getPageNum();

    public List<UserRelation> getUserRelationList(int num);

    public List<UserRelation> getUserRelationListByFatherId(int fatherId, int num);
    public List<UserRelation> getUserRelationListByFatherId(int fatherId);
    public int getPagesByfatherId(int fatherId);

    public int updateFatherIdByid(int id, int fatherId);
}
