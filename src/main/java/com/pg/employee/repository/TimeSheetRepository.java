package com.pg.employee.repository;

import com.pg.employee.entities.TimeSheetEntity;
import com.pg.employee.entities.WorkScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheetEntity, UUID> {
    @Query("SELECT t FROM TimeSheetEntity t WHERE t.tsEmployeeId = :employeeId AND t.tsWsId = :schedule")
    Optional<TimeSheetEntity> findExisting(@Param("employeeId") String employeeId,
                                           @Param("schedule") WorkScheduleEntity schedule);

    @Query("SELECT t FROM TimeSheetEntity t WHERE t.tsWsId = :tsWsId AND t.tsResId = :tsResId")
    List<TimeSheetEntity> findByTsWsIdAndTsResId(@Param("tsWsId") WorkScheduleEntity tsWsId, @Param("tsResId") String tsResId);

}
