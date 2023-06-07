package com.example.learn.boot.unittest.service.impl;

import com.example.learn.boot.unittest.converter.EmployeeConverter;
import com.example.learn.boot.unittest.domain.PersistentEmployeeEntity;
import com.example.learn.boot.unittest.model.EmployeeDTO;
import com.example.learn.boot.unittest.repository.EmployeeRepository;
import com.example.learn.boot.unittest.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public  class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeConverter employeeConverter;
    private final EmployeeRepository employeeRepository;


    @Override
    public EmployeeDTO save(EmployeeDTO dto) {
        try {
            EmployeeDTO savedDto = employeeConverter.convertToDto(
                    employeeRepository.saveAndFlush(employeeConverter.convertToEntity(dto)));
            return savedDto;

        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public EmployeeDTO getEmployeeById(long id) {
        EmployeeDTO dto = employeeConverter.convertToDto(employeeRepository.findById(id).orElse(null));

        if (dto == null) {
            return null;
        }
        return dto;

    }



    @Override
    public EmployeeDTO deleteEmployeeById(long id) {
        employeeRepository.deleteById(id);

        return null;
    }



    @Override
    public List<EmployeeDTO> ListAll() {
        return employeeConverter.convertToDtoList(employeeRepository.findAll());

    }



    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO updatedEmployee) {
        PersistentEmployeeEntity existingEntity = employeeRepository.findById(id).orElse(null);
        employeeRepository.save(employeeConverter.copyConvertToEntity(updatedEmployee, existingEntity));
        return updatedEmployee;
    }









}

