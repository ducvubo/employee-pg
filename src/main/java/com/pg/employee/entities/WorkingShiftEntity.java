package com.pg.employee.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pg.employee.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "working_shift")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkingShiftEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false,columnDefinition = "RAW(16)")
    private UUID wks_id;

    @Column(name = "wks_res_id")
    private String wks_res_id;

    @Column(name = "wks_name")
    private String wks_name;

    @Column(name = "wks_description")
    private String wks_description;

    @Column(name = "wks_start_time")
    private String wks_start_time;

    @Column(name = "wks_end_time")
    private String wks_end_time;

//    @OneToMany(mappedBy = "workingShift",fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<WorkScheduleEntity> workSchedules;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "is_deleted")
    private int isDeleted;

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
