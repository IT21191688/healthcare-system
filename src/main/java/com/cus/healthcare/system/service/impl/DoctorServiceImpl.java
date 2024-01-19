package com.cus.healthcare.system.service.impl;

import com.cus.healthcare.system.dto.request.RequestDoctorDto;
import com.cus.healthcare.system.dto.response.ResponseDoctorDto;
import com.cus.healthcare.system.entity.Doctor;
import com.cus.healthcare.system.repo.DoctorRepo;
import com.cus.healthcare.system.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements DoctorService {

    //crud ,exeption,mapping

    private final DoctorRepo doctorRepo;

    @Autowired
    public DoctorServiceImpl(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    @Override
    public void createDoctor(RequestDoctorDto dto) {

        UUID uuid=UUID.randomUUID();
        long dId=uuid.getMostSignificantBits();

        Doctor doctor=new Doctor(dId,dto.getName(),dto.getAddress(),dto.getContact(),dto.getSalary());
        doctorRepo.save(doctor);

    }

    @Override
    public void deleteDoctor(long id) {

        Optional<Doctor> selectedDoctor= doctorRepo.findById(id);

        if(selectedDoctor.isEmpty()){
            throw new RuntimeException("Doctor Not found");
        }

        doctorRepo.deleteById(selectedDoctor.get().getId());

    }

    @Override
    public void updateDoctor(long id, RequestDoctorDto dto) {
        Optional<Doctor> selectedDoctor= doctorRepo.findById(id);

        if(selectedDoctor.isEmpty()){
            throw new RuntimeException("Doctor Not found");
        }

        Doctor doc=selectedDoctor.get();

        doc.setName(dto.getName());
        doc.setContact(dto.getContact());
        doc.setSalary(dto.getSalary());
        doc.setAddress(dto.getAddress());

        doctorRepo.save(doc);



    }

    @Override
    public ResponseDoctorDto getDoctor(long id) {

        Optional<Doctor> selectedDoctor= doctorRepo.findById(id);

        if(selectedDoctor.isEmpty()){
            throw new RuntimeException("Doctor Not found");
        }

        Doctor doc=selectedDoctor.get();
        return new ResponseDoctorDto(
                doc.getId(),doc.getName(),doc.getContact(),doc.getAddress(),doc.getSalary()
        );

    }

    @Override
    public List<ResponseDoctorDto> getAllDoctors(String searchText, int page, int size) {
        searchText="%"+searchText+"%";
        List<Doctor> doctors=doctorRepo.searchDoctors(searchText, PageRequest.of(page,size));

        List<ResponseDoctorDto> dtos=new ArrayList<>();

        doctors.forEach(
                doc->{
                   dtos.add(
                           new ResponseDoctorDto(
                                   doc.getId(),doc.getName(),doc.getContact(),doc.getAddress(),doc.getSalary()
                           )
                   );
                }
        );

        return dtos;
    }

    @Override
    public List<ResponseDoctorDto> findDoctorsByName(String name) {
        List<Doctor> allByName=doctorRepo.findAllByName(name);

        return null;

    }
}
