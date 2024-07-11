package com.cydeo.service.impl;

import com.cydeo.client.WeatherClient;
import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.weather.WeatherResponse;
import com.cydeo.entity.Address;
import com.cydeo.exception.NotFoundException;
import com.cydeo.repository.AddressRepository;
import com.cydeo.service.AddressService;
import com.cydeo.util.MapperUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {
    @Value("${access_key}")
    private String accessKey;
    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;
    private final WeatherClient weatherClient;

    public AddressServiceImpl(AddressRepository addressRepository, MapperUtil mapperUtil, WeatherClient weatherClient) {
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
        this.weatherClient = weatherClient;
    }

    @Override
    public AddressDTO findByAddressNo(String addressNo) {

        Address foundAddress = addressRepository.findByAddressNo(addressNo)
                .orElseThrow(() -> new NotFoundException("No Address Found!"));

        AddressDTO addressDTO = mapperUtil.convert(foundAddress, new AddressDTO());
        //we need to find the temperature for the city information and return it
        addressDTO.setCurrentTemperature(retrieveTemperatureByCity(addressDTO.getCity()));

        return addressDTO;
    }

    private Integer retrieveTemperatureByCity(String city) {
        //we have city information as a parameter
        //we need to make request to get the weather info
        Map<String,Object> currentWeather = weatherClient.getCurrentWeather(accessKey, city);

        Map<String,Object> current = (Map<String, Object>) currentWeather.get("current");

        Integer temperature = (Integer) current.get("temperature");

        return temperature;
//        if(currentWeather==null||currentWeather.getCurrent().getTemperature()==null){
//            return null;
//        }
//        //find the temperature and return it
//        return currentWeather.getCurrent().getTemperature();

    }

    @Override
    public AddressDTO update(String addressNo, AddressDTO address) {

        Address foundAddress = addressRepository.findByAddressNo(addressNo)
                .orElseThrow(() -> new NotFoundException("No Address Found!"));

        Address addressToUpdate = mapperUtil.convert(address, new Address());

        addressToUpdate.setAddressNo(addressNo);
        addressToUpdate.setId(foundAddress.getId());

        Address updatedAddress = addressRepository.save(addressToUpdate);

        return mapperUtil.convert(updatedAddress, new AddressDTO());

    }
}
