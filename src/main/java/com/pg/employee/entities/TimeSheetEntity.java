package com.pg.employee.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "TimeSheet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSheetEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false,columnDefinition = "RAW(16)")
    private UUID tsId;

    @Column(name = "tsResId")
    private String tsResId;

    @Column(name = "tsCheckIn")
    private Date tsCheckIn;

    @Column(name = "tsCheckOut")
    private Date tsCheckOut;

    @Column(name = "tsEmployeeId")
    private String tsEmployeeId;

    @ManyToOne
    @JoinColumn(name = "tsWsId") // tên cột foreign key trong TimeSheet table
    private WorkScheduleEntity tsWsId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

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
