package com.cus.healthcare.system.api;


import com.cus.healthcare.system.dto.request.RequestDoctorDto;
import com.cus.healthcare.system.service.DoctorService;
import com.cus.healthcare.system.utill.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //this is a rest controller
@RequestMapping("/api/v1/doctors")//api path
public class DoctorController {


    //created 201
    //get 200
    //update 201

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping //post method
    public ResponseEntity<StandardResponse> createDoctor(@RequestBody RequestDoctorDto doctorDto) {
        doctorService.createDoctor(doctorDto);

        StandardResponse standardResponse = new StandardResponse(201, "Doctor Add Successfully", doctorDto);

        return new ResponseEntity<>(standardResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> getDoctor(@PathVariable long id){

        StandardResponse standardResponse = new StandardResponse(200, "Doctor details get Successfully", doctorService.getDoctor(id));

        return new ResponseEntity<>(standardResponse, HttpStatus.OK);
    }


    @PutMapping(params = "id")
    public ResponseEntity<StandardResponse> updateDoctor(

            @RequestParam long id,
            @RequestBody RequestDoctorDto doctorDto

    ){
        doctorService.updateDoctor(id,doctorDto);

        StandardResponse standardResponse = new StandardResponse(201, "Doctor details Update Successfully", doctorService.getDoctor(id));

        return new ResponseEntity<>(standardResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponse> deleteDoctor(@PathVariable long id){

        doctorService.deleteDoctor(id);

        StandardResponse standardResponse = new StandardResponse(204, "Doctor Delete Successfully",id);

        return new ResponseEntity<>(standardResponse, HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/list" ,params = {"searchText","page","size"})
    public ResponseEntity<StandardResponse> getDoctorList(

            @RequestParam String searchText,
            @RequestParam int page,
            @RequestParam int size

    ){

        StandardResponse standardResponse = new StandardResponse(200, "Get All Doctors Successfully",doctorService.getAllDoctors(searchText,page,size));

        return new ResponseEntity<>(standardResponse, HttpStatus.OK);
    }


}
