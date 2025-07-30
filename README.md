# Employee Attendance System – Kotlin CLI

A simple Kotlin-based command-line app to manage employee check-ins, check-outs, and track total working hours.

---

## Menu Options

1. Add Employee
2. View All Empoyees
3. Check-In
4. check-Out
5. Show Working Hours
6. Exit


---

## Project Structure

- `EmployeeData` – Data class for employee information  
- `CheckInOutRecord` – Stores check-in and check-out timestamps  
- `EmployeeManager` – Handles employee creation, lookup, and display  
- `AttendanceTracker` – Manages attendance and working hour reporting  
- `main()` – Runs the interactive menu loop


---

 ## Updated Rules:
🔒 User must type today’s date to check in/out.

No past or future dates allowed.

You want to require typing today's date (not just allow it).

🔒 Only one check-in and one check-out allowed per employee per day.

---
## Sample Output

<pre>
1. Add Employee
Enter First Name: John
Enter Last Name: Doe
Enter Role: Developer
✅ Employee added successfully!
🔑 Your Employee ID is: E5C845

2. Check-In
Enter your Employee ID: E5C845
✅ John Doe checked in at 2025-07-29T10:15:00

3. Check-Out
✅ John Doe checked out at 2025-07-29T18:15:00

4. Show Working Hours
🕓 John Doe worked for 8h 0m

</pre>


