package com.fmi.materials.controller;

import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.service.MaterialRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/admins/{adminId}")
public class AdminController {
    private MaterialRequestService materialRequestService;

    @Autowired
    public AdminController(MaterialRequestService materialRequestService) {
        this.materialRequestService = materialRequestService;
    }

    @GetMapping("material-requests")
    public ResponseEntity<List<MaterialRequestDto>> getAllMaterialRequests(@PathVariable Long adminId) {
        return new ResponseEntity<List<MaterialRequestDto>>(
                this.materialRequestService.getAllAdminMaterialRequests(adminId),
                HttpStatus.OK
        );
    }

    @GetMapping("material-requests/{requestId}")
    public ResponseEntity<MaterialRequestDto> getMaterialRequestById(@PathVariable Long adminId, @PathVariable Long requestId) {
        return new ResponseEntity<MaterialRequestDto>(
                this.materialRequestService.getMaterialRequestById(adminId, requestId),
                HttpStatus.OK
        );
    }

    @GetMapping("material-requests/{requestId}/material")
    public ResponseEntity<byte[]> getMaterialFromMaterialRequest(@PathVariable Long adminId, @PathVariable Long requestId) {
        MaterialDtoWithData material = this.materialRequestService.getMaterialFromMaterialRequest(adminId, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(material.getFileFormat()));
        headers.add("Content-Disposition", "inline; filename=" + material.getFileName());

        return new ResponseEntity<byte[]>(
                material.getData(),
                headers,
                HttpStatus.FOUND
        );
    }

    @PostMapping("material-requests/{requestId}")
    public ResponseEntity<ResponseDto> processRequest(@RequestParam Boolean status, @PathVariable Long adminId, @PathVariable Long requestId) throws IOException {
        this.materialRequestService.processRequest(adminId, requestId, status);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK, "Request processed successfully"),
                HttpStatus.OK);
    }
}
