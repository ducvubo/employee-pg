package com.pg.employee.repository;

import com.pg.employee.entities.WorkScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkScheduleEntity, UUID> {

    @Query("SELECT w FROM WorkScheduleEntity w WHERE w.ws_id = :wsId AND w.ws_res_id = :wsResId")
    Optional<WorkScheduleEntity> findByWsIdAndWsResId(
            @Param("wsId") UUID wsId, @Param("wsResId") String wsResId);

    @Query("SELECT w FROM WorkScheduleEntity w " +
            "LEFT JOIN FETCH w.listEmployeeId " +
            "WHERE w.ws_res_id = :ws_res_id " +
            "AND w.isDeleted = :isDeleted " +
            "AND w.ws_date BETWEEN :startDate AND :endDate")
    List<WorkScheduleEntity> findByWsResIdAndIsDeletedAndWsDateBetween(
            @Param("ws_res_id") String ws_res_id,
            @Param("isDeleted") int isDeleted,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);


    //viết query findByWsResIdAndWsDateAndWorkingShiftAndLabel
    @Query("SELECT w FROM WorkScheduleEntity w  LEFT JOIN FETCH w.listEmployeeId WHERE w.ws_res_id = :ws_res_id AND w.ws_date = :ws_date")
    List<WorkScheduleEntity> DateWorkExist(
            @Param("ws_res_id") String ws_res_id,
            @Param("ws_date") Date ws_date);
}
