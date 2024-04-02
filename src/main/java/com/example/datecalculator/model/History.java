package com.example.datecalculator.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_date", nullable = false)
    private Timestamp firstDate;

    @Column(name = "second_date", nullable = false)
    private Timestamp secondDate;

    @Column(name = "diff_in_days", nullable = false)
    private Timestamp diffInDays;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setFirstDate(Timestamp firstDate) {
        this.firstDate = firstDate;
    }

    public void setSecondDate(Timestamp secondDate) {
        this.secondDate = secondDate;
    }

    public void setDiffInDays(Timestamp diffInDays) {
        this.diffInDays = diffInDays;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getFirstDate() {
        return firstDate;
    }

    public Timestamp getSecondDate() {
        return secondDate;
    }

    public Timestamp getDiffInDays() {
        return diffInDays;
    }
}
