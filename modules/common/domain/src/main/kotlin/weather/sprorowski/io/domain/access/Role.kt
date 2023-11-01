package weather.sprorowski.io.domain.access

enum class Role(val scopes: List<Scope>) {
    EMPLOYEE(
        listOf(
            Scope.EMPLOYEE_EXPENSE_LIST,
            Scope.EMPLOYEE_EXPENSE_SUBMIT,
            Scope.EMPLOYEE_EXPENSE_VIEW,
        ),
    ),
    PAYROLL_MANAGER(
        listOf(
            Scope.HR_EMPLOYEE_EXPENSES_VIEW,
            Scope.HR_EMPLOYEE_EXPENSES_APPROVE,
        ),
    ),
}

fun List<Role>.scopes(): List<Scope> = fold(listOf()) { acc, role -> acc + role.scopes }
