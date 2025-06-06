# Course Enrollment System(CES)- Project Submission

Hey there! Here's my submission for the Course Enrollment System project. I'm Saadat Nurbekova, and I've built a complete system to manage student enrollments in courses.

## What Does This System Do?

Imagine you're running a school or training center and need to keep track of which students are taking which courses. That's exactly what my system helps with! It lets you:

- Add new students and update their info
- Create courses and set how many students can enroll
- Enroll and unenroll students from courses
- See everything in clear, organized tables

## Key Features I Implemented

1. **Student Management** - Full CRUD operations (Create, Read, Update, Delete)
2. **Course Management** - Set course codes, names, and capacity limits
3. **Smart Enrollment** - Prevents over-enrollment and duplicate signups
4. **Clean Interface** - Easy-to-use tabs for different functions
5. **Real-time Updates** - Tables refresh automatically after changes
6. **Helpful Alerts** - Clear messages about success or errors
7. **Database Backed** - All data saves to PostgreSQL
8. **Safety Checks** - Prevents deleting courses with enrolled students

## How I Built It

I used JavaFX for the interface with a proper MVC (Model-View-Controller) setup. The database has three main tables:

1. **Students** (with ID, name, email)
2. **Courses** (with code, name, capacity)
3. **Enrollments** (linking students to courses)

The tricky parts were making sure enrollments properly update the course counts and handling all the error cases (like trying to enroll in a full course). I used database transactions to keep everything in sync.

## Testing It Out

Here's how you can try the system:
![image](https://github.com/user-attachments/assets/88461b59-183e-43c6-aa50-c31b25aa7b5b)
![image](https://github.com/user-attachments/assets/b29ad0e3-231c-41b7-918f-e4558905d11f)
![image](https://github.com/user-attachments/assets/13d189d2-8a05-43de-8974-dc7ffe15bb5f)

Database Structure:

![Screenshot 2025-04-24 214044](https://github.com/user-attachments/assets/344ab93d-6b18-4237-8615-039e4a340ced)

1. **Add Students**  
   - Fill in name and email → Click "Add Student"  
   - Try leaving fields blank to see the error

2. **Create Courses**  
   - Set a small capacity (like 2 students) to test limits  
   - Try updating a course's capacity after enrollments exist

3. **Enroll Students**  
   - First enroll should work  
   - Try enrolling same student twice → should block it  
   - Fill the course to capacity → next enrollment should fail  

4. **Check the Enrollment Tab**  
   - Shows all current enrollments clearly  
   - You can drop enrollments from here too

## Challenges I Faced

- Getting the enrollment counts to update properly took a few tries
- Making sure the UI stays responsive during database operations
- Handling all the edge cases (like deleting courses with enrollments)
- Designing an interface that's simple but shows all needed info

## Presentation: 
https://www.canva.com/design/DAGkzVebzuo/oL0ASYulOUuNdOQVrCpfcQ/edit?utm_content=DAGkzVebzuo&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton

## Final Thoughts

This project really helped me understand how to build a complete application with database integration. I'm particularly proud of the enrollment system that properly manages course capacities and prevents duplicates.

The system works well and meets all requirements, but if I had more time I'd add:
- Search/filter options for the tables
- More detailed student profiles
- Reports on enrollment statistics
