/*Domain class of table s_tag*/
package com.mycollab.common.domain;

import com.mycollab.core.arguments.ValuedBean;
import com.mycollab.db.metadata.Column;
import com.mycollab.db.metadata.Table;
import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

@SuppressWarnings("ucd")
@Table("s_tag")
@Alias("Tag")
public class Tag extends ValuedBean {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_tag.id
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    @Column("id")
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_tag.name
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    @Length(max=100, message="Field value is too long")
    @Column("name")
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_tag.type
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    @Length(max=45, message="Field value is too long")
    @Column("type")
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_tag.typeid
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    @Length(max=100, message="Field value is too long")
    @Column("typeid")
    private String typeid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_tag.sAccountId
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    @Column("sAccountId")
    private Integer saccountid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_tag.extraTypeId
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    @Column("extraTypeId")
    private Integer extratypeid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_tag.createdTime
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    @Column("createdTime")
    private LocalDateTime createdtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column s_tag.createdUser
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    @Length(max=45, message="Field value is too long")
    @Column("createdUser")
    private String createduser;

    private static final long serialVersionUID = 1;

    public final boolean equals(Object obj) {
        if (obj == null) { return false;}
        if (obj == this) { return true;}
        if (!obj.getClass().isAssignableFrom(getClass())) { return false;}
        Tag item = (Tag)obj;
        return new EqualsBuilder().append(id, item.id).build();
    }

    public final int hashCode() {
        return new HashCodeBuilder(1505, 1251).append(id).build();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_tag.id
     *
     * @return the value of s_tag.id
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_tag.id
     *
     * @param id the value for s_tag.id
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_tag.name
     *
     * @return the value of s_tag.name
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_tag.name
     *
     * @param name the value for s_tag.name
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_tag.type
     *
     * @return the value of s_tag.type
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_tag.type
     *
     * @param type the value for s_tag.type
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_tag.typeid
     *
     * @return the value of s_tag.typeid
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public String getTypeid() {
        return typeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_tag.typeid
     *
     * @param typeid the value for s_tag.typeid
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_tag.sAccountId
     *
     * @return the value of s_tag.sAccountId
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public Integer getSaccountid() {
        return saccountid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_tag.sAccountId
     *
     * @param saccountid the value for s_tag.sAccountId
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public void setSaccountid(Integer saccountid) {
        this.saccountid = saccountid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_tag.extraTypeId
     *
     * @return the value of s_tag.extraTypeId
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public Integer getExtratypeid() {
        return extratypeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_tag.extraTypeId
     *
     * @param extratypeid the value for s_tag.extraTypeId
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public void setExtratypeid(Integer extratypeid) {
        this.extratypeid = extratypeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_tag.createdTime
     *
     * @return the value of s_tag.createdTime
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public LocalDateTime getCreatedtime() {
        return createdtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_tag.createdTime
     *
     * @param createdtime the value for s_tag.createdTime
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public void setCreatedtime(LocalDateTime createdtime) {
        this.createdtime = createdtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column s_tag.createdUser
     *
     * @return the value of s_tag.createdUser
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public String getCreateduser() {
        return createduser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column s_tag.createdUser
     *
     * @param createduser the value for s_tag.createdUser
     *
     * @mbg.generated Fri Dec 21 03:28:34 CST 2018
     */
    public void setCreateduser(String createduser) {
        this.createduser = createduser;
    }

    public enum Field {
        id,
        name,
        type,
        typeid,
        saccountid,
        extratypeid,
        createdtime,
        createduser;

        public boolean equalTo(Object value) {
            return name().equals(value);
        }
    }
}