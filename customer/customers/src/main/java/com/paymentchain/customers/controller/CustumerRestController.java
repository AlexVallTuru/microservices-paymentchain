package com.paymentchain.customers.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymentchain.customers.entities.Customer;
import com.paymentchain.customers.repository.CustomerRepository;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/customer")
public class CustumerRestController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping()
    public List<Customer> list() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Customer input) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer newCustomer = optionalCustomer.get();
            newCustomer.setName(input.getName());
            newCustomer.setPhone(input.getPhone());
            Customer save = customerRepository.save(newCustomer);
            return new ResponseEntity<>(save, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Customer input) {
        Customer save = customerRepository.save(input);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
    
}