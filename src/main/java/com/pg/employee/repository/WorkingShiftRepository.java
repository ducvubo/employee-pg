package com.pg.employee.repository;

import com.pg.employee.entities.LabelEntity;
import com.pg.employee.entities.WorkingShiftEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkingShiftRepository extends JpaRepository<WorkingShiftEntity, UUID> {
    @Query("SELECT wks FROM WorkingShiftEntity wks WHERE (wks.wks_name LIKE %:wksName%) AND (wks.wks_res_id = :wksResId) AND (wks.isDeleted = :isDeleted)")
    Page<WorkingShiftEntity> findByFilters(@Param("wksName") String wksName, @Param("wksResId") String wksResId, @Param("isDeleted") int isDeleted, Pageable pageable);

    @Query("SELECT l FROM WorkingShiftEntity l WHERE (l.wks_res_id = :lbResId) AND (l.isDeleted = 0)")
    List<WorkingShiftEntity> findAllByRestaurantId(@Param("lbResId") String lbResId);
}
