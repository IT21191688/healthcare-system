package com.cus.healthcare.system.repo;

import com.cus.healthcare.system.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepo extends JpaRepository<Doctor,Long> {

    public List<Doctor>findAllByName(String name);

}
