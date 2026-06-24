package com.datacentreops.config;

import com.datacentreops.customer.entity.*;
import com.datacentreops.customer.repository.ColoCustomerRepository;

import com.datacentreops.environmental.entity.*;
import com.datacentreops.environmental.repository.EnvironmentalReadingRepository;

import com.datacentreops.iam.entity.*;
import com.datacentreops.iam.repository.UserRepository;

import com.datacentreops.infrastructure.entity.*;
import com.datacentreops.infrastructure.repository.*;

import com.datacentreops.notification.entity.*;
import com.datacentreops.notification.repository.NotificationRepository;

import com.datacentreops.workorder.entity.*;
import com.datacentreops.workorder.repository.WorkOrderRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository users;
    private final ColoCustomerRepository customers;
    private final DataHallRepository halls;
    private final RackRepository racks;
    private final WorkOrderRepository workOrders;
    private final EnvironmentalReadingRepository readings;
    private final NotificationRepository notifications;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository users,
                      ColoCustomerRepository customers,
                      DataHallRepository halls,
                      RackRepository racks,
                      WorkOrderRepository workOrders,
                      EnvironmentalReadingRepository readings,
                      NotificationRepository notifications,
                      PasswordEncoder passwordEncoder) {
        this.users = users;
        this.customers = customers;
        this.halls = halls;
        this.racks = racks;
        this.workOrders = workOrders;
        this.readings = readings;
        this.notifications = notifications;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (users.count() > 0) {
            return;
        }

        //  USERS
        User admin = new User();
        admin.setName("DC Admin");
        admin.setRole(Role.ADMIN);
        admin.setEmail("admin@datacentreops.local");
        admin.setPassword(passwordEncoder.encode("Admin@123"));
        admin.setStatus(UserStatus.ACTIVE);
        users.save(admin);

        User engineer = new User();
        engineer.setName("Olivia Ops");
        engineer.setRole(Role.DCOPS_ENGINEER);
        engineer.setEmail("olivia.ops@datacentreops.local");
        engineer.setPassword(passwordEncoder.encode("Olivia@123"));
        engineer.setStatus(UserStatus.ACTIVE);
        engineer = users.save(engineer);

        //  CUSTOMER
        ColoCustomer acme = new ColoCustomer();
        acme.setCompanyName("Acme Cloud Ltd");
        acme.setIndustrySegment("CloudProvider");
        acme.setContactPerson("Jane Doe");
        acme.setKycStatus(KycStatus.PENDING);
        acme.setStatus(CustomerStatus.ACTIVE);
        acme = customers.save(acme);

        //  DATA HALL
        DataHall hall = new DataHall();
        hall.setDataCentreId(1L);
        hall.setHallName("Hall A");
        hall.setTotalRacks(50);
        hall.setTotalPowerKW(500.0);
        hall.setCoolingCapacityKW(600.0);
        hall.setTierLevel("Tier3");
        hall.setStatus(HallStatus.OPERATIONAL);
        hall = halls.save(hall);

        //  RACK
        Rack rack = new Rack();
        rack.setHallId(hall.getHallId());
        rack.setRackLabel("A-01");
        rack.setTotalU(42);
        rack.setUsedU(10);
        rack.setAvailableU(32);
        rack.setMaxPowerKW(10.0);
        rack.setAllocatedPowerKW(3.5);
        rack.setCustomerId(acme.getCustomerId());
        rack.setStatus(RackStatus.ALLOCATED);
        rack = racks.save(rack);

        //  WORK ORDER
        WorkOrder wo = new WorkOrder();
        wo.setCustomerId(acme.getCustomerId());
        wo.setRackId(rack.getRackId());
        wo.setRequestType(WorkOrderType.REMOTE_HANDS);
        wo.setDescription("Reseat network cable on top-of-rack switch");
        wo.setPriority(WorkOrderPriority.URGENT);
        wo.setRequestedById(acme.getCustomerId());
        wo.setStatus(WorkOrderStatus.OPEN);
        workOrders.save(wo);

        //  ENVIRONMENTAL
        EnvironmentalReading reading = new EnvironmentalReading();
        reading.setHallId(hall.getHallId());
        reading.setSensorId("TEMP-A-01");
        reading.setReadingType(ReadingType.TEMPERATURE);
        reading.setValue(23.4);
        reading.setUnit("C");
        reading.setStatus(ReadingStatus.NORMAL);
        readings.save(reading);

        //  NOTIFICATION
        Notification note = new Notification();
        note.setUserId(engineer.getUserId());
        note.setMessage("New urgent remote hands work order raised");
        note.setCategory(NotificationCategory.WORK_ORDER);
        note.setStatus(NotificationStatus.UNREAD);
        notifications.save(note);
    }
}