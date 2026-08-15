package hr.tis.academy.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "WORKING_DAY", schema = "PRODUCT_MANAGER")
public class WorkingDay implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String dayOfWeek;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    public WorkingDay() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}