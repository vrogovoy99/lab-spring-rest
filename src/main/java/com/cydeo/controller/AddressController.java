package com.cydeo.controller;

import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address/")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /*
     Endpoint: /api/v1/address/{addressNo}
     HTTP Status Code: 200

     JSON Response Body:
     "success": true
     "message": "Address <addressNo> is successfully retrieved."
     "code":200
     "data":<address data>
    */
    @GetMapping("{addressNo}")
    public ResponseEntity<ResponseWrapper> getAddressByNo(@PathVariable("addressNo") String addressNo){

        AddressDTO addressDTO = addressService.findByAddressNo(addressNo);
        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .success(true)
                .message("Address " + addressNo + " is retrieved")
                .code(HttpStatus.OK.value())
                .data(addressDTO)
                .build();

        return ResponseEntity.ok(responseWrapper);
    }



    /*
      Endpoint: /api/v1/address/{addressNo}

      JSON Response Body:
      <updated address data>
     */
    @PutMapping("{addressNo}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable("addressNo") String addressNo,
                                                         @RequestBody AddressDTO addressDTO){
        addressDTO=addressService.update(addressNo, addressDTO);
        return ResponseEntity.ok(addressDTO);
    }


}
