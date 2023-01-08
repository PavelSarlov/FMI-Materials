package com.fmi.materials.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fmi.materials.vo.WorkerJobStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "worker_jobs")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class WorkerJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Type(type = "jsonb")
    @Column(name = "data")
    private String data;

    @Column(name = "status")
    private WorkerJobStatus status;

    public WorkerJob() {
    }

    public WorkerJob(String type, JSONObject data, WorkerJobStatus status) {
        this(null, type, data, status);
    }

    public WorkerJob(Long id, String type, JSONObject data, WorkerJobStatus status) {
        this.id = id;
        this.type = type;
        this.setData(data);
        this.status = status;
    }

    public JSONObject getData() {
        return new JSONObject(this.data);
    }

    public void setData(JSONObject data) {
        this.data = data.toString();
    }
}
