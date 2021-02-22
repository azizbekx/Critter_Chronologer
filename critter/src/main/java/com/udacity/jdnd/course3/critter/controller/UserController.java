package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.entities.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.entities.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private CustomerService customerService;
    private EmployeeService employeeService;
    private PetService petService;

    public UserController(CustomerService customerService, EmployeeService employeeService, PetService petService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return convertCustomerToCustomerDTO(
                customerService.saveCustomer(
                        convertCustomerDTOtoCustomer(customerDTO)));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

        return customerService.getAllCustomers()
                .stream()
                //convert each customer to customerDTO
                .map(this::convertCustomerToCustomerDTO)
                //convert all stream to list
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPet(petId);

        return convertCustomerToCustomerDTO(customerService.getCustomerById(pet.getOwner().getId()));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        return convertEmployeeToEmployeeDTO(
                employeeService.saveEmployee(
                        convertEmployeeDTOToEmployee(employeeDTO)
                )
        );
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(
                employeeService.getEmployeeById(employeeId)
        );
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        System.out.println(daysAvailable);
        employeeService.setAvailability(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        return employeeService.findEmployeesForService(
                    employeeDTO.getSkills(),employeeDTO.getDate().getDayOfWeek()
                )
                .stream()
                .map(this::convertEmployeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    /*
    Customer, CustomerDTO convert function
     */
    private Customer convertCustomerDTOtoCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);

//        if (customerDTO.getPetIds()!=null) {
//            List<Pet> petList = new LinkedList<>();
//
//            for (Long petId : customerDTO.getPetIds()) {
//                petList.add(petService.getPet(petId));
//            }
//            customer.setPets(petList);
//        }
        return customer;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();

        BeanUtils.copyProperties(customer,customerDTO);

//            List<Long> petIds =petService.getPetsByOwner(customer.getId())
//                    .stream()
//                    .map(this::getPetId)
//                    .collect(Collectors.toList());
//            customerDTO.setPetIds(petIds);
        List<Long> petIds = new ArrayList<>();
        try {
            customer.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
            customerDTO.setPetIds(petIds);
        }catch (Exception e){
            System.out.println("customer "+customer.getId() + " does not have any pet");
        }

        return customerDTO;
    }
    private Long getPetId(Pet pet){
        return pet.getId();
    }
    /*
        Employee, EmployeeDTO convert function
    */


    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);

        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee,employeeDTO);

        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }



}
