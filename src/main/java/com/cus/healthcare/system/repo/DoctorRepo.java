package com.cus.healthcare.system.repo;

import com.cus.healthcare.system.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor,Long> {


}
