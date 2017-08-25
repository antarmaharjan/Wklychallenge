package me.ratna.wk4.models;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Education {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @NotNull
    private long id;
    @NotNull
    private String school;
    @NotNull
    private String degree;
    @NotNull
    @Range(min = 1950,max = 2099)
    private long gradyear;

    public long getGradyear() {
        return gradyear;
    }
    public void setGradyear(long gradyear) {
        this.gradyear = gradyear;
    }
    public long getId() {
        return id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {

            this.degree = degree;
    }

}
