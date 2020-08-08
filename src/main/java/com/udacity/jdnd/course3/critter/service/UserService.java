package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.notFound.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PetRepository petRepository;
    /**
    * ADD CUSTOMER
     */
    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        List<Pet> pets = new ArrayList<>();
        if (petIds != null && !petIds.isEmpty()) {
            pets = petIds.stream().map((petId) -> petRepository.getOne(petId)).collect(Collectors.toList());
        }
        customer.setPets(pets);
        return customerRepository.save(customer);
    }
    public Customer findById(long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new ObjectNotFoundException("Customer not found in Id : " + customerId));

    }
    /**
     * GET ALL CUSTOMERS
     */
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    /**
     * GET ALL CUSTOMERS
     */
    public Customer getCustomerByPet(long petId){
        return petRepository.getOne(petId).getCustomer();
    }
    /**
     * ADD EMPLOYEE
     */

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    /**
     * GET EMPLOYEE
     */

    public Employee getEmployeeById(long employeeId){
        return employeeRepository.findById(employeeId).orElseThrow(() -> new ObjectNotFoundException("Employee not found By Id : " + employeeId));
    }
    /**
     * UPDATE AVAILABILITY DAY
     */
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ObjectNotFoundException("Employee not found By Id : " + employeeId));
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }
    /**
     * GET EMPLOYEE FOR SERVICE
     */
    public List<Employee> getEmployeesForService(Set<EmployeeSkill> skills,DayOfWeek dayOfWeek) {
        return employeeRepository.findAllBySkillsInAndDaysAvailableContains(skills, dayOfWeek);
    }
}
