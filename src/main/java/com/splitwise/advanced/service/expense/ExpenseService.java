package com.splitwise.advanced.service.expense;

import com.splitwise.advanced.dto.request.ExpenseReqDto;
import com.splitwise.advanced.dto.response.ExpenseRespDto;
import com.splitwise.advanced.entities.expense.Expense;

public interface ExpenseService {

    Expense createExpense(ExpenseReqDto expenseReqDto);
    ExpenseRespDto updateExpense(ExpenseReqDto expenseReqDto);
    void deleteExpense(int id);

}
