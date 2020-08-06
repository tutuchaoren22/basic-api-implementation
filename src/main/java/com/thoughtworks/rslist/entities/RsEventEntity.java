package com.thoughtworks.rslist.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "rs_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RsEventEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "name")
    private String eventName;
    private String keyword;
    @Column(name = "user_id")
    private Integer userId;
}
