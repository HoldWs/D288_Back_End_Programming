package com.d288PA.WGU.bootstrap;

import com.d288PA.WGU.dao.CustomerRepository;
import com.d288PA.WGU.dao.DivisionRepository;
import com.d288PA.WGU.entity.Customer;
import com.d288PA.WGU.entity.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private CustomerRepository customerRepository;
    private DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        Division div = new Division();
        div.setId(26L);
        div.setCountry_id(1L);

        Customer sample1 = new Customer("Cole", "McDougal", "1234 Place St.", "12345", "8675309");
        Customer sample2 = new Customer("Isaac", "Elenbaas", "5678 Location Pk.", "54321", "5555555");
        Customer sample3 = new Customer("Aaron", "Kirk", "13254 Destination Ave.", "40506", "9999999");
        Customer sample4 = new Customer("Freddy", "Fazbear", "Wherever FNAF took place IDK", "01987", "1234567");
        Customer sample5 = new Customer("Sample", "Text", "33 Not Real St.", "00000", "1002000");
        sample1.setDivision(div);
        sample2.setDivision(div);
        sample3.setDivision(div);
        sample4.setDivision(div);
        sample5.setDivision(div);

        customerRepository.save(sample1);
        customerRepository.save(sample2);
        customerRepository.save(sample3);
        customerRepository.save(sample4);
        customerRepository.save(sample5);

        customerRepository.findAll();
    }
}


