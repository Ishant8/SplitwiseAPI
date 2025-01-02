package com.splitwise.advanced.entities.expense;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.entities.userexpense.UserExpense;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "expense_date")
    private Date expenseDate = new Date(System.currentTimeMillis());

    @Column(name = "notes", length = 1000)
    private String notes;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "circle_id")
    private Circle circle;

    @JsonIgnore
    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserExpense> userExpenseList = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "creator_id")
    private User creator;

    public Expense() {
    }

    public Expense(String name, BigDecimal amount, String notes) {
        this.name = name;
        this.amount = amount;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCircleName(){
        return circle.getName();
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public List<UserExpense> getUserExpenseList() {
        return userExpenseList;
    }

    public List<String> getUserExpenses(){
        return userExpenseList.stream().map(userExpense -> userExpense.getUser().getFullName()+" "+userExpense.getExpenseShare()).collect(Collectors.toList());
    }

    public void setUserExpenseList(List<UserExpense> userExpenseList) {
        this.userExpenseList = userExpenseList;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", expenseDate=" + expenseDate +
                ", notes='" + notes + '\'' +
                ", circle=" + getCircleName() +
                ", userExpenseList=" + getUserExpenses()+
                '}';
    }
}
