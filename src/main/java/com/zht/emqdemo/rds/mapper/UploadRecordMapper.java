package com.zht.emqdemo.rds.mapper;

import com.zht.emqdemo.rds.model.UploadRecord;
import com.zht.emqdemo.rds.model.UploadRecordCriteria;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface UploadRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload_record
     *
     * @mbg.generated
     */
    @SelectProvider(type=UploadRecordSqlProvider.class, method="countByExample")
    long countByExample(UploadRecordCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload_record
     *
     * @mbg.generated
     */
    @DeleteProvider(type=UploadRecordSqlProvider.class, method="deleteByExample")
    int deleteByExample(UploadRecordCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload_record
     *
     * @mbg.generated
     */
    @Delete({
        "delete from upload_record",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload_record
     *
     * @mbg.generated
     */
    @Insert({
        "insert into upload_record (id, power_one, power_two, ",
        "power_three, power_four, ",
        "temperature_one, temperature_two, ",
        "temperature_three, temperature_four, ",
        "value_one, value_two, ",
        "upload_date, cabinet_id)",
        "values (#{id,jdbcType=VARCHAR}, #{powerOne,jdbcType=INTEGER}, #{powerTwo,jdbcType=INTEGER}, ",
        "#{powerThree,jdbcType=INTEGER}, #{powerFour,jdbcType=INTEGER}, ",
        "#{temperatureOne,jdbcType=DECIMAL}, #{temperatureTwo,jdbcType=DECIMAL}, ",
        "#{temperatureThree,jdbcType=DECIMAL}, #{temperatureFour,jdbcType=DECIMAL}, ",
        "#{valueOne,jdbcType=INTEGER}, #{valueTwo,jdbcType=INTEGER}, ",
        "#{uploadDate,jdbcType=TIMESTAMP}, #{cabinetId,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(UploadRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload_record
     *
     * @mbg.generated
     */
    @InsertProvider(type=UploadRecordSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(UploadRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload_record
     *
     * @mbg.generated
     */
    @SelectProvider(type=UploadRecordSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="power_one", property="powerOne", jdbcType=JdbcType.INTEGER),
        @Result(column="power_two", property="powerTwo", jdbcType=JdbcType.INTEGER),
        @Result(column="power_three", property="powerThree", jdbcType=JdbcType.INTEGER),
        @Result(column="power_four", property="powerFour", jdbcType=JdbcType.INTEGER),
        @Result(column="temperature_one", property="temperatureOne", jdbcType=JdbcType.DECIMAL),
        @Result(column="temperature_two", property="temperatureTwo", jdbcType=JdbcType.DECIMAL),
        @Result(column="temperature_three", property="temperatureThree", jdbcType=JdbcType.DECIMAL),
        @Result(column="temperature_four", property="temperatureFour", jdbcType=JdbcType.DECIMAL),
        @Result(column="value_one", property="valueOne", jdbcType=JdbcType.INTEGER),
        @Result(column="value_two", property="valueTwo", jdbcType=JdbcType.INTEGER),
        @Result(column="upload_date", property="uploadDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="cabinet_id", property="cabinetId", jdbcType=JdbcType.VARCHAR)
    })
    List<UploadRecord> selectByExample(UploadRecordCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload_record
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, power_one, power_two, power_three, power_four, temperature_one, temperature_two, ",
        "temperature_three, temperature_four, value_one, value_two, upload_date, cabinet_id",
        "from upload_record",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="power_one", property="powerOne", jdbcType=JdbcType.INTEGER),
        @Result(column="power_two", property="powerTwo", jdbcType=JdbcType.INTEGER),
        @Result(column="power_three", property="powerThree", jdbcType=JdbcType.INTEGER),
        @Result(column="power_four", property="powerFour", jdbcType=JdbcType.INTEGER),
        @Result(column="temperature_one", property="temperatureOne", jdbcType=JdbcType.DECIMAL),
        @Result(column="temperature_two", property="temperatureTwo", jdbcType=JdbcType.DECIMAL),
        @Result(column="temperature_three", property="temperatureThree", jdbcType=JdbcType.DECIMAL),
        @Result(column="temperature_four", property="temperatureFour", jdbcType=JdbcType.DECIMAL),
        @Result(column="value_one", property="valueOne", jdbcType=JdbcType.INTEGER),
        @Result(column="value_two", property="valueTwo", jdbcType=JdbcType.INTEGER),
        @Result(column="upload_date", property="uploadDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="cabinet_id", property="cabinetId", jdbcType=JdbcType.VARCHAR)
    })
    UploadRecord selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload_record
     *
     * @mbg.generated
     */
    @UpdateProvider(type=UploadRecordSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UploadRecord record, @Param("example") UploadRecordCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload_record
     *
     * @mbg.generated
     */
    @UpdateProvider(type=UploadRecordSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") UploadRecord record, @Param("example") UploadRecordCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload_record
     *
     * @mbg.generated
     */
    @UpdateProvider(type=UploadRecordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UploadRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload_record
     *
     * @mbg.generated
     */
    @Update({
        "update upload_record",
        "set power_one = #{powerOne,jdbcType=INTEGER},",
          "power_two = #{powerTwo,jdbcType=INTEGER},",
          "power_three = #{powerThree,jdbcType=INTEGER},",
          "power_four = #{powerFour,jdbcType=INTEGER},",
          "temperature_one = #{temperatureOne,jdbcType=DECIMAL},",
          "temperature_two = #{temperatureTwo,jdbcType=DECIMAL},",
          "temperature_three = #{temperatureThree,jdbcType=DECIMAL},",
          "temperature_four = #{temperatureFour,jdbcType=DECIMAL},",
          "value_one = #{valueOne,jdbcType=INTEGER},",
          "value_two = #{valueTwo,jdbcType=INTEGER},",
          "upload_date = #{uploadDate,jdbcType=TIMESTAMP},",
          "cabinet_id = #{cabinetId,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(UploadRecord record);
}