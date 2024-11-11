package seg3x02.employeeGql.resolvers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeeRepository
import java.util.*
import org.springframework.stereotype.Controller

@Controller
class EmployeesResolver {
    constructor(
        private val employeeRepository: EmployeeRepository)

    @QueryMapping
    fun employees(): List<Employee> {
        return employeeRepository.findAll()
    }

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? {
        return employeeRepository.findById(id).orElse(null)
    }

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val employee = Employee(
            id = UUID.randomUUID().toString(),
            firstName = createEmployeeInput.firstName,
            lastName = createEmployeeInput.lastName,
            position = createEmployeeInput.position,
            salary = createEmployeeInput.salary
        )
        return employeeRepository.save(employee)
    }

    @MutationMapping
    fun deleteEmployee(@Argument id: String): Boolean {
        return if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
