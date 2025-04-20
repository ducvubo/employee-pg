package com.pg.employee.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "leave_application")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveApplicationEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false,columnDefinition = "RAW(16)")
    private UUID leaveAppId;

    @Column(name="leaveAppResId")
    private String leaveAppResId;


    @Column(name = "employeeId", nullable = false)
    private String employeeId; // ID nhân viên gửi đơn

    @Column(name = "leaveType", nullable = false)
    private String leaveType; // Loại nghỉ phép (Nghỉ ốm, nghỉ phép năm, nghỉ cưới...)

    @Temporal(TemporalType.DATE)
    @Column(name = "startDate", nullable = false)
    private String startDate; // Ngày bắt đầu nghỉ

    @Temporal(TemporalType.DATE)
    @Column(name = "endDate", nullable = false)
    private String endDate; // Ngày kết thúc nghỉ

    @Column(name = "reason")
    private String reason; // Lý do nghỉ phép

    @Column(name = "status", nullable = false)
    private String status = "DRAFT"; // Trạng thái: DRAFT, PENDING, APPROVED, REJECTED, CANCELED

    @Column(name = "approvedBy")
    private String approvedBy; // Người duyệt đơn (quản lý)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approvedAt")
    private Date approvedAt; // Thời gian duyệt đơn

    @Column(name = "approvalComment")
    private String approvalComment;

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
