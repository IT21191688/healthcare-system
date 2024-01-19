package com.cus.healthcare.system.repo;

import com.cus.healthcare.system.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepo extends JpaRepository<Doctor,Long> {

    public List<Doctor>findAllByName(String name);

    @Query(value = "SELECT * FROM doctor WHERE name LIKE ?1 OR address LIKE ?1",nativeQuery = true)
    public List<Doctor>searchDoctors(String searchText, Pageable pageable);


    Optional<Doctor> findTopByOrderByIdDesc();

}
