package com.fmi.materials.controller;

import java.io.IOException;
import java.util.List;

import com.fmi.materials.dto.material.MaterialDtoWithData;
import com.fmi.materials.dto.materialrequest.MaterialRequestDto;
import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.response.ResponseDtoSuccess;
import com.fmi.materials.service.MaterialRequestService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admins/{adminId}")
public class AdminController {

    private final MaterialRequestService materialRequestService;

    @GetMapping("material-requests")
    public ResponseEntity<List<MaterialRequestDto>> getAllMaterialRequests(@PathVariable Long adminId) {
        return new ResponseEntity<List<MaterialRequestDto>>(
                this.materialRequestService.getAllMaterialRequests(adminId),
                HttpStatus.OK);
    }

    @GetMapping("material-requests/{requestId}")
    public ResponseEntity<MaterialRequestDto> getMaterialRequestById(@PathVariable Long adminId,
            @PathVariable Long requestId) {
        return new ResponseEntity<MaterialRequestDto>(
                this.materialRequestService.getMaterialRequestById(adminId, requestId),
                HttpStatus.OK);
    }

    @GetMapping("material-requests/{requestId}/material")
    public ResponseEntity<byte[]> getMaterialFromMaterialRequest(@PathVariable Long adminId,
            @PathVariable Long requestId) {
        MaterialDtoWithData material = this.materialRequestService.getMaterialFromMaterialRequest(adminId, requestId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(material.getFileFormat()));
        headers.add("Content-Disposition", "inline; filename=" + material.getFileName());

        return new ResponseEntity<byte[]>(
                material.getData(),
                headers,
                HttpStatus.OK);
    }

    @PostMapping("material-requests/{requestId}")
    public ResponseEntity<ResponseDto> processRequest(@RequestParam Boolean status, @PathVariable Long adminId,
            @PathVariable Long requestId) throws IOException {
        this.materialRequestService.processRequest(adminId, requestId, status);

        return new ResponseEntity<ResponseDto>(
                new ResponseDtoSuccess(HttpStatus.OK, "Request processed successfully"),
                HttpStatus.OK);
    }
}
