package com.cus.healthcare.system.api;


import org.springframework.web.bind.annotation.*;

@RestController //this is a rest controller
@RequestMapping("/api/v1/doctors")//api path
public class DoctorController {

    @PostMapping //post methord
    public String createDoctor(){

        return "create Doctor";
    }

    @GetMapping
    public String getDoctor(){

        return "Get Doctor";
    }


    @PutMapping
    public String updateDoctor(){

        return "Update Doctor";
    }

    @DeleteMapping
    public String deleteDoctor(){

        return "Delete Doctor";
    }

    @GetMapping(path = "/list")
    public String getDoctorList(){

        return "List Doctor";
    }


}
