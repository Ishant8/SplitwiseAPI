package com.splitwise.advanced.mapper;

import com.splitwise.advanced.dto.request.ExpenseReqDto;
import com.splitwise.advanced.dto.response.ExpenseRespDto;
import com.splitwise.advanced.dto.response.UserExpenseRespDto;
import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.expense.Expense;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.entities.userexpense.UserExpense;
import com.splitwise.advanced.repository.CircleRepository;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExpensePopulator {

    ExpensePopulator INSTANCE = Mappers.getMapper(ExpensePopulator.class);

    @Mapping(target = "involvedUsers", source = "userExpenseList")
    ExpenseRespDto populateExpense(Expense expense);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "expense.id", ignore = true)
    void updateExpenseFromDto(ExpenseReqDto expenseReqDto, @MappingTarget Expense expense);


    default Map<Integer,String> mapCircle(Circle circle) {
        Map<Integer,String> map = new HashMap<>();
        map.put(circle.getId(), circle.getName());
        return map;
    }

    default List<UserExpenseRespDto> mapUserExpenseList(List<UserExpense> userExpenseList) {
        List<UserExpenseRespDto> userExpenseRespDtos = new ArrayList<>();
        userExpenseList.forEach(userExpense -> {
            UserExpenseRespDto uerDto = new UserExpenseRespDto(userExpense.getUser().getId(), userExpense.getUser().getFullName(), userExpense.getExpenseShare());
            userExpenseRespDtos.add(uerDto);
        });
        return userExpenseRespDtos;
    }



    default String mapCreator(User creator) {
        return creator.getFullName();

    }
}
