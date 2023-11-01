package weather.sprorowski.io.domain.access

enum class Scope(val scope: String) {
    // Human Resources - self
    EMPLOYEE_EXPENSE_LIST("game/expenses.list"),
    EMPLOYEE_EXPENSE_SUBMIT("game/expenses.submit"),
    EMPLOYEE_EXPENSE_VIEW("game/expenses.view"),

    // Human Resources - manager
    HR_EMPLOYEE_EXPENSES_VIEW("game/employee-expenses.view"),
    HR_EMPLOYEE_EXPENSES_APPROVE("game/employee-expenses.approve"),
}
