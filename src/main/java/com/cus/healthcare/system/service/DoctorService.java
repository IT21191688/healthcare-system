package com.cus.healthcare.system.service;

import com.cus.healthcare.system.dto.request.RequestDoctorDto;
import com.cus.healthcare.system.dto.response.ResponseDoctorDto;

import java.util.List;

public interface DoctorService {

    public void createDoctor(RequestDoctorDto dto);

    public void deleteDoctor(long id);

    public void updateDoctor(long id,RequestDoctorDto dto);

    public ResponseDoctorDto getDoctor(long id);

    public List<ResponseDoctorDto> getAllDoctors(String searchText, int page, int size);

    public List<ResponseDoctorDto> findDoctorsByName(String name);

}
