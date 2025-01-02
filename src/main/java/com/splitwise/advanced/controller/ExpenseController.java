package com.splitwise.advanced.controller;

import com.splitwise.advanced.dto.request.ExpenseReqDto;
import com.splitwise.advanced.entities.expense.Expense;
import com.splitwise.advanced.repository.ExpenseRepository;
import com.splitwise.advanced.service.expense.ExpenseService;
import org.springframework.web.bind.annotation.*;

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
    public Expense getExpense(@RequestParam int expenseId) {
        return expenseRepository.findById(expenseId).orElse(null);
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
