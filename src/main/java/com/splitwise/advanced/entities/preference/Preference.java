package com.splitwise.advanced.entities.preference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splitwise.advanced.entities.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "preference")
public class Preference {

    @Id
    @Column(name = "preference_id")
    private int id;

    @Column(name = "allow_suggestion_as_friend")
    private boolean allowSuggestionAsFriend;

    @Column(name = "allow_email_when_added_as_friend")
    private boolean allowEmailWhenAddedAsFriend;

    @Column(name = "allow_email_when_added_to_group")
    private boolean allowEmailWhenAddedToGroup;

    @Column(name = "allow_email_when_expense_added")
    private boolean allowEmailWhenExpenseAdded;

    @Column(name = "allow_email_when_expense_altered")
    private boolean allowEmailWhenExpenseAltered;

    @Column(name = "allow_email_when_comments_on_expense")
    private boolean allowEmailWhenCommentsOnExpense;

    @Column(name = "allow_email_when_expense_due")
    private boolean allowEmailWhenExpenseDue;

    @Column(name = "allow_email_when_payment_received")
    private boolean allowEmailWhenPaymentReceived;

    @Column(name = "allow_email_of_monthly_activity_summary")
    private boolean allowEmailOfMonthlyActivitySummary;

    @Column(name = "allow_email_of_news_and_updates")
    private boolean allowEmailOfNewAndUpdates;

    @Column(name = "allow_authentication_with_biometrics")
    private boolean allowAuthenticationWithBiometrics;

    @Column(name = "timeout_before_authentication_expires")
    private int timeoutBeforeAuthenticationExpires;

    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "preference_id")
    private User user;

    public Preference() {
    }

    public Preference(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAllowSuggestionAsFriend() {
        return allowSuggestionAsFriend;
    }

    public void setAllowSuggestionAsFriend(boolean allowSuggestionAsFriend) {
        this.allowSuggestionAsFriend = allowSuggestionAsFriend;
    }

    public boolean isAllowEmailWhenAddedAsFriend() {
        return allowEmailWhenAddedAsFriend;
    }

    public void setAllowEmailWhenAddedAsFriend(boolean allowEmailWhenAddedAsFriend) {
        this.allowEmailWhenAddedAsFriend = allowEmailWhenAddedAsFriend;
    }

    public boolean isAllowEmailWhenAddedToGroup() {
        return allowEmailWhenAddedToGroup;
    }

    public void setAllowEmailWhenAddedToGroup(boolean allowEmailWhenAddedToGroup) {
        this.allowEmailWhenAddedToGroup = allowEmailWhenAddedToGroup;
    }

    public boolean isAllowEmailWhenExpenseAdded() {
        return allowEmailWhenExpenseAdded;
    }

    public void setAllowEmailWhenExpenseAdded(boolean allowEmailWhenExpenseAdded) {
        this.allowEmailWhenExpenseAdded = allowEmailWhenExpenseAdded;
    }

    public boolean isAllowEmailWhenExpenseAltered() {
        return allowEmailWhenExpenseAltered;
    }

    public void setAllowEmailWhenExpenseAltered(boolean allowEmailWhenExpenseAltered) {
        this.allowEmailWhenExpenseAltered = allowEmailWhenExpenseAltered;
    }

    public boolean isAllowEmailWhenCommentsOnExpense() {
        return allowEmailWhenCommentsOnExpense;
    }

    public void setAllowEmailWhenCommentsOnExpense(boolean allowEmailWhenCommentsOnExpense) {
        this.allowEmailWhenCommentsOnExpense = allowEmailWhenCommentsOnExpense;
    }

    public boolean isAllowEmailWhenExpenseDue() {
        return allowEmailWhenExpenseDue;
    }

    public void setAllowEmailWhenExpenseDue(boolean allowEmailWhenExpenseDue) {
        this.allowEmailWhenExpenseDue = allowEmailWhenExpenseDue;
    }

    public boolean isAllowEmailWhenPaymentReceived() {
        return allowEmailWhenPaymentReceived;
    }

    public void setAllowEmailWhenPaymentReceived(boolean allowEmailWhenPaymentReceived) {
        this.allowEmailWhenPaymentReceived = allowEmailWhenPaymentReceived;
    }

    public boolean isAllowEmailOfMonthlyActivitySummary() {
        return allowEmailOfMonthlyActivitySummary;
    }

    public void setAllowEmailOfMonthlyActivitySummary(boolean allowEmailOfMonthlyActivitySummary) {
        this.allowEmailOfMonthlyActivitySummary = allowEmailOfMonthlyActivitySummary;
    }

    public boolean isAllowEmailOfNewAndUpdates() {
        return allowEmailOfNewAndUpdates;
    }

    public void setAllowEmailOfNewAndUpdates(boolean allowEmailOfNewAndUpdates) {
        this.allowEmailOfNewAndUpdates = allowEmailOfNewAndUpdates;
    }

    public boolean isAllowAuthenticationWithBiometrics() {
        return allowAuthenticationWithBiometrics;
    }

    public void setAllowAuthenticationWithBiometrics(boolean allowAuthenticationWithBiometrics) {
        this.allowAuthenticationWithBiometrics = allowAuthenticationWithBiometrics;
    }

    public int getTimeoutBeforeAuthenticationExpires() {
        return timeoutBeforeAuthenticationExpires;
    }

    public void setTimeoutBeforeAuthenticationExpires(int timeoutBeforeAuthenticationExpires) {
        this.timeoutBeforeAuthenticationExpires = timeoutBeforeAuthenticationExpires;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
