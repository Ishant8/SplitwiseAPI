package com.splitwise.advanced.service.expense;

import com.splitwise.advanced.dto.request.ExpenseReqDto;
import com.splitwise.advanced.dto.response.ExpenseRespDto;
import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.expense.Expense;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.entities.userexpense.UserExpense;
import com.splitwise.advanced.entities.userexpense.UserExpenseId;
import com.splitwise.advanced.repository.CircleRepository;
import com.splitwise.advanced.repository.ExpenseRepository;
import com.splitwise.advanced.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    CircleRepository circleRepository;
    ExpenseRepository expenseRepository;
    UserRepository userRepository;

    public ExpenseServiceImpl(CircleRepository circleRepository, ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.circleRepository = circleRepository;
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Expense createExpense(ExpenseReqDto expenseReqDto) {

        Expense expense = new Expense(expenseReqDto.getName(), expenseReqDto.getAmount(), expenseReqDto.getNotes());

        Circle circle = circleRepository.findByName(expenseReqDto.getCircleName());
        expense.setCircle(circle);

        User user = userRepository.findByFullName(expenseReqDto.getCreatorName());
        expense.setCreator(user);

        Expense finalExpense = expenseRepository.saveAndFlush(expense);

        expenseReqDto.getUserShares().forEach(us -> {

            String[] temp = us.trim().split(",");

            User u = userRepository.findByFullName(temp[0]);

            UserExpenseId userExpenseId = new UserExpenseId(u.getId(), finalExpense.getId());
            UserExpense userExpense = new UserExpense(userExpenseId, u, finalExpense, BigDecimal.valueOf(Integer.parseInt(temp[1])));

            finalExpense.getUserExpenseList().add(userExpense);
        });

        return expenseRepository.save(finalExpense);
    }
}
