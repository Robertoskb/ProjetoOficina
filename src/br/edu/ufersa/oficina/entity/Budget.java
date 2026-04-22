package br.edu.ufersa.oficina.entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Budget extends Treatment{
    public Budget(int id, ArrayList<Parts> parts, ArrayList<Service> services, Car car, double price){
        super(id, parts, services, car, price);
    }

    public Budget(int id, ArrayList<Parts> parts, ArrayList<Service> services, Car car, double price, LocalDate date_start, LocalDate date_finish){
        super(id, parts, services, car, price, date_start, date_finish);
    }
}
