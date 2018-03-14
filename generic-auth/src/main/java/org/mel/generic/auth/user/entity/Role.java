package org.mel.generic.auth.user.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.mel.generic.auth.common.View;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String desc;

    public Role() {
    }

    public Role(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    @JsonView(View.L1.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(View.L1.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonView(View.L1.class)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
