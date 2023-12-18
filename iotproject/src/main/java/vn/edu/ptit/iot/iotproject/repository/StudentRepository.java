package vn.edu.ptit.iot.iotproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.ptit.iot.iotproject.entity.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

	
}
