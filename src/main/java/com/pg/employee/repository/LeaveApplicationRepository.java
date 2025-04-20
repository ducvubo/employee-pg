package com.pg.employee.repository;

import com.pg.employee.entities.LeaveApplicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplicationEntity, UUID> {

    LeaveApplicationEntity findByLeaveAppIdAndEmployeeId(UUID leaveAppId, String employeeId);

    LeaveApplicationEntity findByLeaveAppIdAndLeaveAppResId(UUID leaveAppId, String leaveAppResId);

    @Query("""
    SELECT l FROM LeaveApplicationEntity l
    WHERE l.leaveAppResId = :restaurantId
    AND (:leaveType IS NULL OR l.leaveType = :leaveType)
    AND (
        (:status IS NOT NULL AND l.status = :status)
        OR (:status IS NULL AND l.status NOT IN ('DRAFT', 'CANCEL'))
    )
""")
    Page<LeaveApplicationEntity> findAllByFilter(
            @Param("restaurantId") String restaurantId,
            @Param("leaveType") String leaveType,
            @Param("status") String status,
            Pageable pageable);

    @Query("""
    SELECT l FROM LeaveApplicationEntity l
    WHERE l.employeeId = :employeeId
    AND (:leaveType IS NULL OR l.leaveType = :leaveType)
    AND (:status IS NULL OR l.status = :status)
""")
    Page<LeaveApplicationEntity> getAllLeaveApplicationByEmployee(
            @Param("employeeId") String employeeId,
            @Param("leaveType") String leaveType,
            @Param("status") String status,
            Pageable pageable);



}
