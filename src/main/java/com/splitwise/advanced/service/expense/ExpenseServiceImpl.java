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
import com.splitwise.advanced.mapper.ExpensePopulator;
import com.splitwise.advanced.repository.CircleRepository;
import com.splitwise.advanced.repository.ExpenseRepository;
import com.splitwise.advanced.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpensePopulator expensePopulator;
    CircleRepository circleRepository;
    ExpenseRepository expenseRepository;
    UserRepository userRepository;

    public ExpenseServiceImpl(CircleRepository circleRepository, ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.circleRepository = circleRepository;
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.expensePopulator = ExpensePopulator.INSTANCE;
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

    @Override
    public ExpenseRespDto updateExpense(ExpenseReqDto expenseReqDto) {
        Expense expense = expenseRepository.findById(expenseReqDto.getId()).orElse(null);

        if(expense != null){

            User creator = expense.getCreator();
            Circle circle = expense.getCircle();
            expensePopulator.updateExpenseFromDto(expenseReqDto, expense);

            if(expenseReqDto.getCircleName() != null && !Objects.equals(expenseReqDto.getCircleName(), expense.getCircleName())){
                circle = circleRepository.findByName(expenseReqDto.getCircleName());
                expense.setCircle(circle);
            }

            if(!expenseReqDto.getUserShares().isEmpty()){
                List<String> addedUsers = new ArrayList<>();
                List<UserExpense> removedUsers = new ArrayList<>();
                List<String> updatedUsers = new ArrayList<>(expenseReqDto.getUserShares().stream().map(upu -> upu.split(",")[0]).toList());

                Circle finalCircle1 = circle;
                expense.getUserExpenseList().forEach(userExpense -> {

                    if(!updatedUsers.contains(userExpense.getUser().getFullName())){

//                        expense.getUserExpenseList().remove(userExpense);
                        removedUsers.add(userExpense);

                        BigDecimal previousAmount = userExpense.getExpenseShare().negate();

                        if(userExpense.getUser().getId() != creator.getId()) {
                            UserFriend userFriend = updateUserFriend(creator, userExpense.getUser(), String.valueOf(previousAmount), finalCircle1);

                            if (creator.getId() < userExpense.getUser().getId()) {
                                creator.getFriendsLinkedAsSmaller().replaceAll(uf -> {
                                    if (uf.getBigger().getId() == userExpense.getUser().getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });
                                userExpense.getUser().getFriendsLinkedAsBigger().replaceAll(uf -> {
                                    if (uf.getSmaller().getId() == creator.getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });

                            } else {
                                creator.getFriendsLinkedAsBigger().replaceAll(uf -> {
                                    if (uf.getSmaller().getId() == userExpense.getUser().getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });
                                userExpense.getUser().getFriendsLinkedAsSmaller().replaceAll(uf -> {
                                    if (uf.getBigger().getId() == creator.getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });
                            }
                        }


                    }else{

                        addedUsers.add(userExpense.getUser().getFullName());

                        int index = updatedUsers.indexOf(userExpense.getUser().getFullName());
                        Double newAmount = -userExpense.getExpenseShare().doubleValue() + Double.parseDouble(expenseReqDto.getUserShares().get(index).split(",")[1]);

                        userExpense.setExpenseShare(userExpense.getExpenseShare().add(BigDecimal.valueOf(newAmount)));

                        if(userExpense.getUser().getId() != creator.getId()) {
                            UserFriend userFriend = updateUserFriend(creator, userExpense.getUser(), String.valueOf(newAmount), finalCircle1);

                            if (creator.getId() < userExpense.getUser().getId()) {
                                creator.getFriendsLinkedAsSmaller().replaceAll(uf -> {
                                    if (uf.getBigger().getId() == userExpense.getUser().getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });
                                userExpense.getUser().getFriendsLinkedAsBigger().replaceAll(uf -> {
                                    if (uf.getSmaller().getId() == creator.getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });

                            } else {
                                creator.getFriendsLinkedAsBigger().replaceAll(uf -> {
                                    if (uf.getSmaller().getId() == userExpense.getUser().getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });
                                userExpense.getUser().getFriendsLinkedAsSmaller().replaceAll(uf -> {
                                    if (uf.getBigger().getId() == creator.getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });
                            }
                        }
                    }
                });

                expense.getUserExpenseList().removeAll(removedUsers);
                updatedUsers.removeAll(addedUsers);
                updatedUsers.remove(creator.getFullName());

                if(!updatedUsers.isEmpty()){
                    Circle finalCircle = circle;
                    updatedUsers.forEach(u -> {
                        User user = userRepository.findByFullName(u);
                        if(!user.getCircles().contains(finalCircle.getName())){
                            throw new EntityNotFoundException(u +" is not in the group");
                        }

//                        int index = updatedUsers.indexOf(u);
                        String extractAmount = expenseReqDto.getUserShares().stream()
                                                .filter(us->us.contains(u))
                                                .findFirst()
                                                .orElse(",0");

                        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(extractAmount.split(",")[1]));

//                        UserFriend userFriend = updateUserFriend(creator, user, String.valueOf(amount), finalCircle);


                        if(user.getId() != creator.getId())
                        {
                            UserFriend userFriend = updateUserFriend(creator, user, String.valueOf(amount), finalCircle);

                            if (creator.getId() < user.getId()) {
                                creator.getFriendsLinkedAsSmaller().replaceAll(uf -> {
                                    if (uf.getBigger().getId() == user.getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });
                                user.getFriendsLinkedAsBigger().replaceAll(uf -> {
                                    if (uf.getSmaller().getId() == creator.getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });

                            } else {
                                creator.getFriendsLinkedAsBigger().replaceAll(uf -> {
                                    if (uf.getSmaller().getId() == user.getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });
                                user.getFriendsLinkedAsSmaller().replaceAll(uf -> {
                                    if (uf.getBigger().getId() == creator.getId()) {
                                        return userFriend;
                                    }
                                    return uf;
                                });
                            }
                        }

                        UserExpenseId id = new UserExpenseId(user.getId(), expenseReqDto.getId());
                        UserExpense userExpense = new UserExpense(id,user,expense,amount);

                        expense.getUserExpenseList().add(userExpense);
                    });
                }
            }
            return expensePopulator.populateExpense(expenseRepository.save(expense));
        }
        return null;
    }

    @Override
    public void deleteExpense(int id) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense != null) {
            User creator = expense.getCreator();
            expense.getUserExpenseList().forEach(u -> {
                if(!Objects.equals(u.getUser().getFullName(), creator.getFullName())) {
                    User user = u.getUser();
                    updateUserFriend(creator,user,u.getExpenseShare().negate().toString(),expense.getCircle());
                }
            });
            expenseRepository.delete(expense);
        }
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
