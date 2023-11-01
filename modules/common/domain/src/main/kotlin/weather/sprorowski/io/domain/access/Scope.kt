package weather.sprorowski.io.domain.access

enum class Scope(val scope: String) {
    // Human Resources - self
    EMPLOYEE_EXPENSE_LIST("hr/expenses.list"),
    EMPLOYEE_EXPENSE_SUBMIT("hr/expenses.submit"),
    EMPLOYEE_EXPENSE_VIEW("hr/expenses.view"),

    // Human Resources - manager
    HR_EMPLOYEE_EXPENSES_VIEW("hr/employee-expenses.view"),
    HR_EMPLOYEE_EXPENSES_APPROVE("hr/employee-expenses.approve"),
}
