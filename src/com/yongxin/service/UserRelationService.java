package com.yongxin.service;

import com.yongxin.entity.UserRelation;

import java.util.List;

/**
 * Created by Administrator on 2017-12-28.
 */
public interface UserRelationService {

    public List<UserRelation> getUserRelationList();

    public boolean addUserRelation(UserRelation userRelation);

    public List<UserRelation> getUserRelationByFatherId(int fatherId);
    public UserRelation getUserRelationBySonId(int sonId);

    public boolean deleteUserRelationById(int id);
    public boolean deleteUserRelationBySonId(int id);
    public boolean deleteUserRelationByFatherId(int id);

    public List<UserRelation> getUserRelationListByFatherId(int fatherId, int num);
    public List<UserRelation> getUserRelationListByFatherId(int fatherId);
    public List<UserRelation> getOperatorUserRelationListByFatherId(int fatherId);
    public int getPagesByFatherId(int fatherId);

    public List<UserRelation> getclientUserRelationListByFatherId(int fatherId);

    public boolean updateFatherIdByid(int id, int fatherId);
}
