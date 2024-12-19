package com.splitwise.advanced.repository;

import com.splitwise.advanced.entities.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

}
