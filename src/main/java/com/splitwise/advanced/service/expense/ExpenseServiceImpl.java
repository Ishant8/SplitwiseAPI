package com.splitwise.advanced.service.expense;

import com.splitwise.advanced.dto.request.ExpenseReqDto;
import com.splitwise.advanced.dto.response.ExpenseRespDto;
import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.expense.Expense;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.entities.userexpense.UserExpense;
import com.splitwise.advanced.entities.userexpense.UserExpenseId;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import com.splitwise.advanced.entities.userfriendcircle.UserFriendCircle;
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

        expenseReqDto.getUserShares().forEach(us -> {

            String[] temp = us.trim().split(",");

            User u = userRepository.findByFullName(temp[0]);

            if(u.getId() != user.getId())
            {
                UserFriend userFriend = updateUserFriend(user, u, temp[1], circle);

                if (user.getId() < u.getId()) {
                    user.getFriendsLinkedAsSmaller().replaceAll(uf -> {
                        if (uf.getBigger().getId() == u.getId()) {
                            return userFriend;
                        }
                        return uf;
                    });
                    u.getFriendsLinkedAsBigger().replaceAll(uf -> {
                        if (uf.getSmaller().getId() == user.getId()) {
                            return userFriend;
                        }
                        return uf;
                    });

                } else {
                    user.getFriendsLinkedAsBigger().replaceAll(uf -> {
                        if (uf.getSmaller().getId() == u.getId()) {
                            return userFriend;
                        }
                        return uf;
                    });
                    u.getFriendsLinkedAsSmaller().replaceAll(uf -> {
                        if (uf.getBigger().getId() == user.getId()) {
                            return userFriend;
                        }
                        return uf;
                    });
                }
            }

            UserExpenseId userExpenseId = new UserExpenseId(u.getId(), 0);
            UserExpense userExpense = new UserExpense(userExpenseId, u, expense, BigDecimal.valueOf(Double.parseDouble(temp[1])));

            expense.getUserExpenseList().add(userExpense);


        });

        return expenseRepository.save(expense);
    }

    private UserFriend updateUserFriend(User lender, User debtor, String sharedMoney, Circle circle) {

        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(sharedMoney));
        UserFriend userFriend;
        if(debtor.getId() < lender.getId()) {
            userFriend =  lender.getFriendsLinkedAsBigger().stream().filter(uf -> uf.getSmaller().getId() == debtor.getId()).findFirst().orElse(null);
            userFriend.setMoneyOwed(userFriend.getMoneyOwed().add(amount));
            return updateUserFriendCircle(userFriend,circle,true,amount);

        }
        userFriend = lender.getFriendsLinkedAsSmaller().stream().filter(uf -> uf.getBigger().getId() == debtor.getId()).findFirst().orElse(null);
        userFriend.setMoneyOwed(userFriend.getMoneyOwed().subtract(amount));
        return updateUserFriendCircle(userFriend,circle,false,amount);


    }

// in this function if the positive is true means that the lender Id is larger than the debtor Id and vice versa.
    private UserFriend updateUserFriendCircle(UserFriend userFriend, Circle circle, boolean positive, BigDecimal amount) {
        userFriend.getUserFriendCircle().forEach(ufc ->{
            if(ufc.getCircle().getId() == circle.getId()) {
                if (positive) {
                    ufc.setOwesInGroup(ufc.getOwesInGroup().add(amount));
                } else {
                    ufc.setOwesInGroup(ufc.getOwesInGroup().subtract(amount));
                }
            }
        });

        return userFriend;
    }
}
