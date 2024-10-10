package com.sparta.reviewservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass //이 클래스를 상속받을 수 있게 한다.
@EntityListeners(AuditingEntityListener.class) //엔티티 생성 ,수정시 이벤트 시간 포착
public class Timestamped {

    @CreatedDate
    @Column(updatable = false)
    //날짜 시간 관련  다룬다.
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt; //날짜 시간을 다루는 java객체

    /* tempal
    TemporalType.DATE: 날짜만 저장 (예: 2024-10-10)
    TemporalType.TIME: 시간만 저장 (예: 10:30:55)
    TemporalType.TIMESTAMP: 날짜와 시간을 함께 저장 (예: 2024-10-10 10:30:55)

    */

}
