package com.pg.employee.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "work_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkScheduleEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false,columnDefinition = "RAW(16)")
    private UUID ws_id;

    @Column(name = "ws_res_id")
    private String ws_res_id;

    @ManyToOne
    @JoinColumn(name = "lb_id")
    private LabelEntity label;

    @ManyToOne
    @JoinColumn(name = "wks_id")
    private WorkingShiftEntity workingShift;

    @Column(name = "ws_date")
    private Date ws_date;

    @Column(name = "ws_status", length = 1, columnDefinition = "CHAR(1) DEFAULT 'F'") // Trạng thái ca làm việc: T là đã phát ca, F là chưa phát ca
    private String ws_status;

    @Lob
    @Column(name = "ws_note", columnDefinition = "CLOB", nullable = true)
    private String ws_note;

    @ElementCollection
    @CollectionTable(name = "work_schedule_employees", joinColumns = @JoinColumn(name = "ws_id"))
    @Column(name = "employee_id")
    private List<String> listEmployeeId;

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
