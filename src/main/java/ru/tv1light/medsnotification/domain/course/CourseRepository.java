// src/main/java/ru/tv1light/medsnotification/domain/course/CourseRepository.java
package ru.tv1light.medsnotification.domain.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByUserIdOrderByStartDateDesc(Long userId);
    List<Course> findAllByUserIdOrderByStartDateAsc(Long userId);
}