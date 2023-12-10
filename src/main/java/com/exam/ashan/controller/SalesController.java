package com.exam.ashan.controller;

import com.exam.ashan.entity.Salesman;
import com.exam.ashan.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;
import java.util.Random;

@Controller
@SessionAttributes({"id"})
public class SalesController {

    @Autowired
    private SalesmanRepository salesmanRepository;

    Random random = new Random();

    @GetMapping("/index")
    public String getSales(Model model) {
        List<Salesman> salesmanList = salesmanRepository.findAll();

        model.addAttribute("salesmanList", salesmanList);

        return "sales";
    }

    @PostMapping("/save")
    public String save(Salesman salesman, ModelMap map) {
        salesman.setId(random.nextLong(1L, Long.MAX_VALUE));

        salesmanRepository.save(salesman);

        return "redirect:/index";
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model, ModelMap map) {
        Salesman salesman = salesmanRepository.findById(id).orElseThrow();

        model.addAttribute("salesman", salesman);

        map.put("id", id);

        return "edit";
    }

    @PostMapping("/saveEdit")
    public String saveEidt(Salesman salesman, ModelMap map, SessionStatus status) {
        salesman.setId(Long.parseLong(map.get("id").toString()));

        status.setComplete();

        salesmanRepository.save(salesman);

        return "redirect:/index";
    }

    @GetMapping("/delete")
    public String delete(Long id, SessionStatus status) {
        salesmanRepository.deleteById(id);

        status.setComplete();

        return "redirect:/index";
    }
}
