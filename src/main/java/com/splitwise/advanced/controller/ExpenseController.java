package com.splitwise.advanced.controller;

import com.splitwise.advanced.dto.request.ExpenseReqDto;
import com.splitwise.advanced.dto.response.ExpenseRespDto;
import com.splitwise.advanced.entities.expense.Expense;
import com.splitwise.advanced.mapper.ExpensePopulator;
import com.splitwise.advanced.repository.ExpenseRepository;
import com.splitwise.advanced.service.expense.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseRepository expenseRepository, ExpenseService expenseService)
    {
        this.expenseRepository = expenseRepository;
        this.expenseService = expenseService;
    }

    @GetMapping("/get")
    public List<ExpenseRespDto> getExpense() {


        List<Expense> expenses =  expenseRepository.findAll();
        return expenses.stream().map(expense -> ExpensePopulator.INSTANCE.populateExpense(expense)).toList();
    }

    @PostMapping("/add")
    public Expense addExpense(@RequestBody ExpenseReqDto expenseReqDto) {
        return expenseService.createExpense(expenseReqDto);
    }

//    @PostMapping("/user/add")
//    public Expense addExpenseWithUser(@RequestBody Expense expense) {
//
//    }
}
