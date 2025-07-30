import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

data class EmployeeData(
    val id: String,
    val firstName: String,
    val lastName: String,
    val role: String
)

data class CheckInOutRecord(
    val employeeId: String,
    val checkIn: LocalDateTime,
    var checkOut: LocalDateTime? = null
)

class EmployeeManager {
    private val employeeList = mutableListOf<EmployeeData>()

    fun addEmployee() {
        println("=== Add New Employee ===")
        print("Enter First Name: ")
        val firstName = readLine()?.trim().orEmpty()
        print("Enter Last Name: ")
        val lastName = readLine()?.trim().orEmpty()
        print("Enter Role: ")
        val role = readLine()?.trim().orEmpty()

        val newId = generateEmployeeId()
        val newEmployee = EmployeeData(newId, firstName, lastName, role)
        employeeList.add(newEmployee)

        println(" Employee added successfully!")
        println(" Your Employee ID is: $newId (Please save this for Check-In/Check-Out)")
    }

    fun getEmployeeById(id: String): EmployeeData? {
        return employeeList.find { it.id == id }
    }

    fun displayAllEmployees() {
        println("\n=== Current Employees ===")
        if (employeeList.isEmpty()) {
            println("No employees added yet.")
        } else {
            employeeList.forEach {
                println("${it.id}: ${it.firstName} ${it.lastName} - ${it.role}")
            }
        }
    }

    fun isValidEmployee(id: String): Boolean {
        return employeeList.any { it.id == id }
    }

    private fun generateEmployeeId(): String {
        return "E" + UUID.randomUUID().toString().take(5).uppercase()
    }

    fun getAllEmployees(): List<EmployeeData> = employeeList
}

class AttendanceTracker(private val empManager: EmployeeManager) {
    private val records = mutableListOf<CheckInOutRecord>()

    fun checkInEmployee() {
        println("\n=== Employee Check-In ===")
        print("Enter your Employee ID: ")
        val id = readLine()?.trim().orEmpty()
        checkInEmployeeById(id)
    }

    fun checkInEmployeeById(id: String) {
        if (!empManager.isValidEmployee(id)) {
            println("Invalid ID. Please add employee first.")
            return
        }

        if (records.any { it.employeeId == id && it.checkOut == null }) {
            println("Already checked in. Please check out first.")
            return
        }

        val now = LocalDateTime.now()
        records.add(CheckInOutRecord(id, now))
        val emp = empManager.getEmployeeById(id)
        println("${emp?.firstName} ${emp?.lastName} checked in at $now")
    }

    fun checkOutEmployee() {
        println("\n=== Employee Check-Out ===")
        print("Enter your Employee ID: ")
        val id = readLine()?.trim().orEmpty()
        /*
        If no such record is found (i.e., the employee either never checked in or has already checked out), record will be null.
         */
        val record = records.findLast { it.employeeId == id && it.checkOut == null }

        if (record == null) {
            println("no active check-in found for this ID.")
            return
        }

        record.checkOut = LocalDateTime.now()
        val emp = empManager.getEmployeeById(id)
        println(" ${emp?.firstName} ${emp?.lastName} checked out at ${record.checkOut}")
    }

    fun showWorkingHours() {
        println("\n=== Working Hours Report ===")
        val completedRecords = records.filter { it.checkOut != null }

        if (completedRecords.isEmpty()) {
            println("No completed attendance records.")
            return
        }

        completedRecords.forEach { rec ->
            val emp = empManager.getEmployeeById(rec.employeeId)
            val duration = Duration.between(rec.checkIn, rec.checkOut)
            val hours = duration.toHours()
            val minutes = duration.toMinutes() % 60
            println(" ${emp?.firstName} ${emp?.lastName} worked for ${hours}h ${minutes}m")
        }
    }
}

fun main() {
    val employeeManager = EmployeeManager()
    val attendanceTracker = AttendanceTracker(employeeManager)

    println("=== Welcome to the Employee Attendance System ===")

    loop@ while (true) {
        println(
            """
            --------------------------
            1. Add Employee
            2. View All Employees
            3. Check-In
            4. Check-Out
            5. Show Working Hours
            6. Exit
            --------------------------
        """.trimIndent()
        )
        print("Choose an option (1-6): ")
        when (readLine()?.trim()) {
            "1" -> employeeManager.addEmployee()
            "2" -> employeeManager.displayAllEmployees()
            "3" -> attendanceTracker.checkInEmployee()
            "4" -> attendanceTracker.checkOutEmployee()
            "5" -> attendanceTracker.showWorkingHours()
            "6" -> {
                println(" Exiting...!")
                break@loop
            }
            else -> println(" Invalid choice. Please try again.")
        }
    }
}
