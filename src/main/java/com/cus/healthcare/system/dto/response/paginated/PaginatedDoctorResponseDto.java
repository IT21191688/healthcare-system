package com.cus.healthcare.system.dto.response.paginated;

import com.cus.healthcare.system.dto.response.ResponseDoctorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginatedDoctorResponseDto {

    private long count;
    private List<ResponseDoctorDto> data;

}
