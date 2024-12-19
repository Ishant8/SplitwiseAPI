package com.splitwise.advanced.controller;

import com.splitwise.advanced.entities.expense.Expense;
import com.splitwise.advanced.repository.ExpenseRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/get")
    public Expense getExpense(@RequestParam int expenseId) {
        return expenseRepository.findById(expenseId).orElse(null);
    }

    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseRepository.save(expense);
    }

//    @PostMapping("/user/add")
//    public Expense addExpenseWithUser(@RequestBody Expense expense) {
//
//    }
}
