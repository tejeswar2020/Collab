package com.mycollab.module.ecm.dao;

import com.mycollab.db.persistence.ICrudGenericDAO;

import java.util.List;

import com.mycollab.module.ecm.domain.DriveInfo;
import com.mycollab.module.ecm.domain.DriveInfoExample;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings({ "ucd", "rawtypes" })
public interface DriveInfoMapper extends ICrudGenericDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    long countByExample(DriveInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    int deleteByExample(DriveInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    int insert(DriveInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    int insertSelective(DriveInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    List<DriveInfo> selectByExample(DriveInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    DriveInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    int updateByExampleSelective(@Param("record") DriveInfo record, @Param("example") DriveInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    int updateByExample(@Param("record") DriveInfo record, @Param("example") DriveInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    int updateByPrimaryKeySelective(DriveInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    int updateByPrimaryKey(DriveInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    Integer insertAndReturnKey(DriveInfo value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    void removeKeysWithSession(List primaryKeys);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_ecm_driveinfo
     *
     * @mbg.generated Fri Nov 30 08:06:40 CST 2018
     */
    void massUpdateWithSession(@Param("record") DriveInfo record, @Param("primaryKeys") List primaryKeys);
}