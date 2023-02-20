package com.mmbeautyschool.mmapi.repository;

import com.mmbeautyschool.mmapi.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.id = :id")
    Course getCourseById(@Param("id") long id);

    @Query("SELECT c FROM Course c WHERE c.uniqCode = :code")
    Course getCourseByUniqCode(@Param("code") String code);

    @Query("SELECT c FROM Course c WHERE c.status = 'COURSE_ENABLE'")
    List<Course> getActiveCourse();

}
